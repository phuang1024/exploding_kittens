import java.io.*;
import java.util.*;


/**
 * Manages everything.
 * Has a list of games in progress.
 * Handles to the requests from clients (the server receives them
 * and adds to a queue).
 *
 * Available paths:
 *
 * /
 *   Testing path, does nothing.
 *
 * /new-id
 *   Request a new client ID.
 *   Out headers:
 *     id: New client ID.
 *
 * /join-game
 *   Request to join a game.
 *   In headers:
 *     id: Your client ID.
 *   Out headers:
 *     join-success: "yes" if successfully joined.
 *     game-id: Your game ID if you successfully joined.
 *
 * /status
 *   Get current game status.
 *   In headers:
 *     id: Your client ID.
 *     game-id: Game ID.
 *   Out headers:
 *     your-turn: "yes" if your turn.
 *     card-counts: Space separated integer count of cards of each player.
 *       Starts from you and goes clockwise.
 *     deck-cards: Number of cards in the deck.
 *     active-player-number: Number (0, 1, 2, 3) of active player.
 *     top-cards: Space separated 3 top cards of pile.
 *     index: Your player index.
 *     attack-count: Current attack counter.
 *     alive: Space separated "0" or "1" for alive for each player.
 *     defuse-count: The player.defuseCount
 *
 * /hand
 *   Get your current hand.
 *   In headers:
 *     id: Your client ID.
 *     game-id: Game ID.
 *   Out headers:
 *     hand: Space separated string of card int IDs.
 *
 * /play
 *   Play cards.
 *   In headers:
 *     id: Your client ID.
 *     game-id: Game ID.
 *     cards: String of card IDs, space separated.
 *   Out headers:
 *     success: "yes" if play successful.
 */
public class Manager {
    /**
     * Length of randomly generated IDs.
     */
    public static final int ID_LEN = 10;

    /**
     * Max time for IDs before they delete.
     * Clients can send "/join" request to refresh IDs.
     */
    public static final long ID_MAX_TIME = 3000;

    private Server server;
    private Map<String, Long> toJoin;  // IDs de clientes sin juego y tiempo de juntar
    private Map<String, String> joined;  // Joined but they don't know yet. Map of ID to game ID.
    private Map<String, Game> games;  // Mapping of game ID to game.

    /**
     * Initialize.
     * Will pop from the server's request queue.
     * @param server  Server to read from.
     */
    public Manager(Server server) {
        this.server = server;
        toJoin = new HashMap<String, Long>();
        joined = new HashMap<String, String>();
        games = new HashMap<String, Game>();
    }

    /**
     * Start receiving requests from server.
     * Blocks this thread.
     */
    public void start() {
        while (true) {
            // Sleep 1 millis to reduce CPU load.
            try {
                Thread.sleep(1);
            } catch (InterruptedException exc) {
            }

            try {
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
                    if (joined.containsKey(id)) {
                        headers.put("join-success", "yes");
                        headers.put("game-id", joined.get(id));
                        joined.remove(id);
                    } else {
                        headers.put("join-success", "no");
                        toJoin.put(id, System.currentTimeMillis());
                    }
                }
                else if (path.equals("/status")) {
                    String id = req.headers.get("id"), game_id = req.headers.get("game-id");
                    Game game = games.get(game_id);
    
                    boolean turn = id.equals(game.getTurnId());
                    headers.put("your-turn", turn ? "yes" : "no");
    
                    List<Player> players = game.getPlayers();
                    String card_counts = "";
                    for (int i = 0; i < 4; i++) {
                        int count = players.get(i).getHand().size();
                        card_counts += count + " ";
                    }
                    headers.put("card-counts", card_counts.trim());
    
                    headers.put("deck-cards", ""+game.getDeck().cardCount());
    
                    int playing = game.getPlayerNum(game.getWhosePlaying().getId());
                    headers.put("active-player-number", ""+playing);
    
                    List<Integer> pile = game.getDiscardPile();
                    int i = pile.size() - 1;
                    String top_cards = "";
                    for (int a = 0; a < 3; a++) {
                        int card = (i >= 0) ? pile.get(i) : -1;
                        top_cards += card + " ";
                        i--;
                    }
                    headers.put("top-cards", top_cards.trim());
    
                    headers.put("index", "" + game.getPlayerNum(id));

                    headers.put("attack-count", "" + game.getAttackCounter());

                    headers.put("defuse-count", "" + game.getPlayer(id).getDefuseCount());

                    String alive_str = "";
                    for (i = 0; i < 4; i++)
                        alive_str += players.get(i).isInGame() ? "1 " : "0 ";
                    headers.put("alive", alive_str.trim());
                }
                else if (path.equals("/hand")) {
                    String id = req.headers.get("id"), game_id = req.headers.get("game-id");
                    List<Integer> hand = games.get(game_id).getHand(id);
                    String ret = "";
                    for (Integer card: hand)
                        ret += card + " ";
                    headers.put("hand", ret.trim());
                }
                else if (path.equals("/play")) {
                    String id = req.headers.get("id"), game_id = req.headers.get("game-id");
                    String card_str = req.headers.get("cards").trim();
                    Game game = games.get(game_id);
    
                    headers.put("success", "yes");
                    if (!id.equals(game.getWhosePlaying().getId())) {
                        // Not your turn
                        headers.put("success", "no");
                    }
                    else if (card_str.length() == 0) {
                        // Client didn't play any cards, i.e. end turn
                        game.drawCard();
                    }
                    else {
                        List<Integer> cards = new ArrayList<Integer>();
                        for (String part: card_str.split(" "))
                            cards.add(Integer.parseInt(part));
                        int[] cardArray = new int[cards.size()];
                        for (int i = 0; i < cards.size(); i++)
                            cardArray[i] = cards.get(i);

                        // Simple play validation
                        boolean good = true;
                        int card1 = cardArray[0];
                        if (Card.is(card1, Card.BEARD_CAT | Card.CATTERMELON |
                                    Card.HAIRY_POTATO_CAT | Card.RAINBOW_RALPHING_CAT |
                                    Card.TACOCAT)) {
                            if (cardArray.length != 2 || cardArray[1] != card1)
                                good = false;
                        } else {
                            if (Card.is(card1, Card.DEFUSE | Card.EXPLODING_KITTEN))
                                good = false;
                            if (cardArray.length != 1)
                                good = false;
                        }

                        if (good) {
                            game.playCard(cardArray);
                        } else {
                            headers.put("success", "no");
                            Logger.warn("Invalid move: " + cardArray);
                        }
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
            catch (Exception e) {
                Logger.error("Exception:");
                e.printStackTrace();
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

            Player p1 = new Player(ids[0]),
                   p2 = new Player(ids[1]),
                   p3 = new Player(ids[2]),
                   p4 = new Player(ids[3]);
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
