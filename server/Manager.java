import java.io.*;
import java.util.*;


/**
 * Manages everything.
 * Has a list of games in progress.
 * Responds to the requests from clients.
 */
public class Manager {
    Server server;

    /**
     * Initialize.
     * Will pop from the server's request queue.
     */
    public Manager(Server server) {
        this.server = server;
    }

    public void start() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException exc) {
            }

            if (server.requests.isEmpty())
                continue;

            Client client = server.requests.remove();
            HTTPRequest req = client.request;
            HTTPResponse resp = new HTTPResponse(200, "OK",
                req.headers, req.body);

            try {
                client.send(resp);
            } catch (IOException exc) {
                Logger.warn(exc.toString());
            }
        }
    }
}
