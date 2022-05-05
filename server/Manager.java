import java.io.*;
import java.util.*;


/**
 * Manages everything.
 * Has a list of games in progress.
 * Responds to the requests from clients.
 */
public class Manager {
    public static final int ID_LEN = 10;
    public static final long ID_MAX_TIME = 3000;  // Max time for stale IDs

    private Server server;
    private Map<String, Long> toJoin;  // IDs de clientes sin juego y tiempo de juntar
    private Map<String, String> joined;  // Joined but they don't know yet. Map of ID to game ID.
    private Map<String, Game> games;  // Mapping of game ID to game.

    /**
     * Initialize.
     * Will pop from the server's request queue.
     */
    public Manager(Server server) {
        this.server = server;
        toJoin = new HashMap<String, Long>();
        joined = new HashMap<String, String>();
        games = new HashMap<String, Game>();
    }

    public void start() {
        while (true) {
            // Sleep 1 millis to reduce CPU load.
            try {
                Thread.sleep(1);
            } catch (InterruptedException exc) {
            }

            process();

            // ...
            if (server.requests.isEmpty())
                continue;

            // Handle request
            Client client = server.requests.remove();
            HTTPRequest req = client.request;
            String path = req.path.trim();

            int status = 200;
            Map<String, String> headers = new HashMap<String, String>();

            if (path.equals("/")) {
                // Testing path.
            }
            else if (path.equals("/new-id")) {
                // Get new ID.
                String id = Random.randstr(ID_LEN);
                headers.put("id", id);

                toJoin.put(id, System.currentTimeMillis());
            }
            else if (path.equals("/join-game")) {
                String id = req.headers.get("id");

                if (joinGame(req)) {
                    if (joined.containsKey(id)) {
                        headers.put("join-success", "yes");
                        joined.remove(id);
                    } else {
                        headers.put("join-success", "no");
                    }
                } else {
                    status = 400;
                }
            }
            else {
                status = 404;
            }

            try {
                HTTPResponse resp = new HTTPResponse(status, "A", headers, "");
                client.send(resp);
            } catch (IOException exc) {
                Logger.warn(exc.toString());
            }
        }
    }

    /**
     * Handle internal stuff, e.g. four players to a game.
     */
    private void process() {
        // Remove stale IDs.
        for (String key: toJoin.keySet()) {
            long diff = System.currentTimeMillis() - toJoin.get(key);
            if (diff > ID_MAX_TIME) {
                toJoin.remove(key);
                Logger.warn("Removed stale ID " + key);
            }
        }

        // If four players in toJoin, make new game.
        while (toJoin.size() >= 4) {
            String gameId = Random.randstr(ID_LEN);
            String[] ids = new String[4];

            Iterator<String> iter = toJoin.keySet().iterator();
            for (int i = 0; i < 4; i++)
                ids[i] = iter.next();
            for (int i = 0; i < 4; i++)
                toJoin.remove(ids[i]);

            for (int i = 0; i < 4; i++)
                joined.put(ids[i], gameId);

            Player p1 = new Player(ids[0], "p1"),
                   p2 = new Player(ids[1], "p2"),
                   p3 = new Player(ids[2], "p3"),
                   p4 = new Player(ids[3], "p4");
            Game game = new Game(p1, p2, p3, p4, gameId);
            games.put(gameId, game);

            Logger.info("New game, id="+gameId);
        }
    }

    /**
     * Handler for /join-game.
     * Returns true if the client's request is valid.
     * Whether the client joins a game does not affect the return value.
     */
    private boolean joinGame(HTTPRequest req) {
        if (!req.headers.containsKey("id"))
            return false;

        String id = req.headers.get("id");
        if (!toJoin.containsKey(id))
            return false;

        toJoin.put(id, System.currentTimeMillis());
        return true;
    }
}
