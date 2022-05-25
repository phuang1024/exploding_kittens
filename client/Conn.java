import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Represents one request-response connection.
 * Initialize this to create a connection. There are also public static methods
 * for convenience that do specific connections.
 */
public class Conn {
    /**
     * Localhost
     */
    //public static final String IP = "127.0.0.1";

    /**
     * Patrick's AWS server.
     */
    public static final String IP = "54.176.105.157";  // AWS

    /**
     * Port to connect.
     */
    public static final int PORT = 8016;

    private Socket conn;
    private Scanner in;
    private Writer out;

    /**
     * Request that was sent.
     */
    public HTTPRequest request;

    /**
     * Initialize connection.
     * Internally creates connection object.
     * To send the request, use this.send()
     * To receive the response, use this.recv()
     * @param request  The request to send.
     */
    public Conn(HTTPRequest request) throws IOException {
        this.request = request;

        conn = new Socket(IP, PORT);
        in = new Scanner(conn.getInputStream());
        out = new OutputStreamWriter(conn.getOutputStream());
    }

    /**
     * Send the request.
     */
    public void send() throws IOException {
        out.write(request.toString());
        out.write("\n");
        out.flush();
    }

    /**
     * Receive the response.
     * @return  The response.
     */
    public HTTPResponse recv() throws IOException, HTTPParseException {
        HTTPResponse resp = new HTTPResponse(in);
        return resp;
    }


    /**
     * Get your current hand from the server.
     * @param id  Your client ID.
     * @param game_id  Game ID.
     * @return  Your hand.
     */
    public static List<Integer> getHand(String id, String game_id)
            throws IOException, HTTPParseException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("id", id);
        headers.put("game-id", game_id);
        HTTPRequest req = new HTTPRequest("GET", "/hand", headers, "");
        Conn conn = new Conn(req);
        conn.send();

        HTTPResponse resp = conn.recv();
        String hand = resp.headers.get("hand");
        String[] parts = hand.split(" ");

        List<Integer> cards = new ArrayList<Integer>();
        for (String p: parts)
            if (p.length() > 0)
                cards.add(Integer.parseInt(p));

        return cards;
    }

    /**
     * Requests new id from the server.
     * @return  Your client ID.
     */
    public static String getId() throws IOException, HTTPParseException {
        HTTPRequest req = new HTTPRequest("GET", "/new-id", null, "");
        Conn conn = new Conn(req);
        conn.send();
        HTTPResponse resp = conn.recv();
        String id = resp.headers.get("id");
        return id;
    }

    /**
     * Joins a game. Will hold the thread until a success response is received.
     * @param id  Your client ID.
     * @return  Game ID.
     */
    public static String joinGame(String id) throws IOException, HTTPParseException {
        String gameId;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exc) {
            }

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("id", id);
            HTTPRequest req = new HTTPRequest("GET", "/join-game", headers, "");
            Conn conn = new Conn(req);
            conn.send();

            HTTPResponse resp = conn.recv();
            if (resp.status == 200 && 
                resp.headers.get("join-success").equals("yes"))
            {
                gameId = resp.headers.get("game-id");
                break;
            }
        }

        return gameId;
    }

    /**
     * Play some cards.
     * @param id  Your client ID.
     * @param game_id  Game ID.
     * @param cards  Cards to play.
     * @return  Whether successful.
     */
    public static boolean playCards(String id, String game_id, List<Integer> cards)
            throws IOException, HTTPParseException {
        String cardStr = "";
        for (Integer card: cards)
            cardStr += card + " ";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("id", id);
        headers.put("game-id", game_id);
        headers.put("cards", cardStr.trim());
        HTTPRequest req = new HTTPRequest("GET", "/play", headers, "");
        Conn conn = new Conn(req);
        conn.send();

        HTTPResponse resp = conn.recv();
        boolean success = resp.headers.get("success").equals("yes");
        return success;
    }

    /**
     * Get the current status.
     * @param id  Your client ID.
     * @param game_id  Game ID.
     * @return  Status in a GameInfo class.
     */
    public static GameInfo getStatus(String id, String game_id) 
            throws IOException, HTTPParseException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("id", id);
        headers.put("game-id", game_id);
        Conn conn = new Conn(new HTTPRequest("GET", "/status", headers, ""));
        conn.send();

        HTTPResponse resp = conn.recv();
        GameInfo info = new GameInfo();

        info.deckCardCount = Integer.parseInt(resp.headers.get("deck-cards"));

        String[] parts = resp.headers.get("card-counts").split(" ");
        for (int i = 0; i < 4; i++)
            info.playerCardCount[i] = Integer.parseInt(parts[i]);

        info.activePlayerNumber = Integer.parseInt(resp.headers.get("active-player-number"));
        info.topCard = Integer.parseInt(resp.headers.get("top-card"));
        info.playerIndex = Integer.parseInt(resp.headers.get("index"));

        return info;
    }
}
