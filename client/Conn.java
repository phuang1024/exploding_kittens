import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Represents one request-response connection.
 */
public class Conn {
    public static final String IP = "127.0.0.1";
    //public static final String IP = "54.176.105.157";  // AWS
    public static final int PORT = 8016;

    private Socket conn;
    private Scanner in;
    private Writer out;
    public HTTPRequest request;

    /**
     * Initialize connection.
     * Internally creates connection object.
     * To send the request, use this.send()
     * To receive the response, use this.recv()
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
     */
    public HTTPResponse recv() throws IOException, HTTPParseException {
        HTTPResponse resp = new HTTPResponse(in);
        return resp;
    }

    public static List<Integer> getHand(String id, String game_id) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("id", id);
        headers.put("game-id", game_id);
        HTTPRequest req = new HTTPRequest("GET", "/hand", headers, "");
        conn = new Conn(req);
        conn.send();

        HTTPResponse resp = conn.recv();
        String hand = resp.headers.get("hand");
        String[] parts = hand.split(" ");

        List<Integer> cards = new ArrayList<Integer>();
        for (String p: parts)
            cards.add(Integer.parseInt(p));

        return cards;
    }
}
