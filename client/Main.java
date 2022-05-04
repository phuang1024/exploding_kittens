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

            HTTPRequest req = new HTTPRequest("GET", "/new-id", null, "");
            Conn conn = new Conn(req);
            conn.send();
            HTTPResponse resp = conn.recv();

            Logger.info("Your ID: " + resp.headers.get("id"));
        }
        catch (IOException exc) {
            Logger.error(exc.toString());
        }
        catch (HTTPParseException exc) {
            Logger.error(exc.toString());
        }
    }
}
//XD
