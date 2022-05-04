import java.io.*;
import java.util.*;


/**
 * Manages everything.
 * Has a list of games in progress.
 * Responds to the requests from clients.
 */
public class Manager {
    public static final int ID_LEN = 10;

    private Server server;

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
}
