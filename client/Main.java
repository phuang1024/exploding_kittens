import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Client main.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Logger.info("Server IP=" + Conn.IP + ", port=" + Conn.PORT);
            Logger.info("Lag: " + testLag() + "ms");

            String id = getId();
            Logger.info("Your ID: " + id);

            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exc) {
                }

                Map<String, String> headers = new HashMap<String, String>();
                headers.put("id", id);
                HTTPRequest req = new HTTPRequest("GET", "/join-game", headers, "");
                Conn conn = new Conn(req);
                conn.send();
                HTTPResponse resp = conn.recv();

                Logger.info("" + resp.status);
            }
        }
        catch (IOException exc) {
            Logger.error(exc.toString());
        }
        catch (HTTPParseException exc) {
            Logger.error(exc.toString());
        }
    }

    /**
     * Ping server and return lag in ms.
     */
    private static long testLag() throws IOException, HTTPParseException {
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

    /**
     * Requests new id from the server.
     */
    private static String getId() throws IOException, HTTPParseException {
        HTTPRequest req = new HTTPRequest("GET", "/new-id", null, "");
        Conn conn = new Conn(req);
        conn.send();
        HTTPResponse resp = conn.recv();
        String id = resp.headers.get("id");
        return id;
    }
}
