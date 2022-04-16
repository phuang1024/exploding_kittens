import java.io.IOException;
import java.util.*;


/**
 * Run this class to start the server.
 */
public class Main {
    public static final int PORT = 8016;

    public static void test_stuff() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Host", "explode");
        headers.put("Sohan", "Piano");
        HTTPRequest req = new HTTPRequest("GET", "/explode/kitten", headers,
            "hi sohan\nhi krish");

        try {
            HTTPRequest req2 = new HTTPRequest(req.toString());
            System.out.println(req);
            System.out.println(req2);
        } catch (HTTPParseException exc) {
        }
    }

    public static void main(String[] args) {
        test_stuff();

        /*
        Logger.info("Starting server.");

        try {
            Server server = new Server(PORT);
        }
        catch (IOException exc) {
            Logger.error(exc.toString());
        }*/
    }
}
