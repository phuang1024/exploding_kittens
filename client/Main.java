import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Client main.
 */
public class Main {
    /**
     * Ping server and return lag in ms.
     */
    public static long testLag() throws IOException, HTTPParseException {
        final int PINGS = 3;

        long totalElapse = 0;
        for (int i = 0; i < PINGS; i++) {
            long start = System.currentTimeMillis();
            Conn conn = new Conn(new HTTPRequest("GET", "/", null, ""));
            conn.send();
            conn.recv();
            totalElapse +=  System.currentTimeMillis() - start;
        }

        return totalElapse / PINGS;
    }

    public static void main(String[] args) {
        try {
            Logger.info("Server IP=" + Conn.IP + ", port=" + Conn.PORT);
            Logger.info("Lag: " + testLag() + "ms");

            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message: ");
                String line = in.nextLine();

                HTTPRequest req = new HTTPRequest("GET", "/test",
                    new HashMap<String, String>(), line);
                Conn conn = new Conn(req);
                conn.send();
                HTTPResponse resp = conn.recv();
                System.out.println("Server responded (" + resp.status + "): " + resp.body.trim());
            }
        }
        catch (IOException exc) {
            Logger.error(exc.toString());
        }
        catch (HTTPParseException exc) {
            Logger.error(exc.toString());
        }
    }
}
