import java.io.*;
import java.util.*;


/**
 * Manages everything.
 * Has a list of games in progress.
 * Responds to the requests from clients.
 */
public class Manager {
    public static final int ID_LEN = 10;
    public static final long ID_TIME = 10000;  // Max time for stale IDs

    private Server server;
    private Map<String, Long> toJoin;  // IDs de clientes sin juego y tiempo de juntar

    /**
     * Initialize.
     * Will pop from the server's request queue.
     */
    public Manager(Server server) {
        this.server = server;
        toJoin = new HashMap<String, Long>();
    }

    public void start() {
        while (true) {
            // Sleep 1 millis to reduce CPU load.
            try {
                Thread.sleep(1);
            } catch (InterruptedException exc) {
            }

            // Cleanup of stuff
            for (String key: toJoin.keySet()) {
                long diff = System.currentTimeMillis() - toJoin.get(key);
                if (diff > ID_TIME) {
                    toJoin.remove(key);
                    Logger.warn("Removed stale ID " + key);
                }
            }

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
                if (joinGame(req)) {
                    // TODO send some response
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

        // TODO join stuff
        toJoin.put(id, System.currentTimeMillis());

        return true;
    }
}
