import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Handles networking and accepting clients.
 */
public class Server extends Thread {
    /**
     * Interval in ms of log stats.
     */
    public static final long LOG_INTERVAL = 10000;

    private ServerSocket server;
    private int port;

    /**
     * Queue of requests from clients.
     * Class Manager pops from this.
     */
    public Queue<Client> requests;

    /**
     * Initialize the server.
     * @param port  Port to bind to.
     */
    public Server(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port);
        requests = new LinkedList<Client>();
    }

    /**
     * Start server forever.
     * Run this in a different thread.
     * Adds request to this.requests when a client sends one.
     */
    public void run() {
        Logger.info("Started socket on port " + server.getLocalPort()
            + ", ip " + server.getInetAddress());

        long last_log = System.currentTimeMillis();
        int req_count = 0;

        while (true) {
            Socket conn;
            String addr = "";
            HTTPRequest req;
            Client client;

            long t = System.currentTimeMillis();
            if (t - last_log > LOG_INTERVAL) {
                double interval = (double)(t-last_log) / 1000.0;
                double rps = (double)req_count / interval;
                Logger.debug("Stats: " + req_count + " requests, " + rps + " requests per second.");

                last_log = t;
                req_count = 0;
            }

            try {
                conn = server.accept();
                addr = conn.getInetAddress().toString();
                Scanner in = new Scanner(conn.getInputStream());
                Writer out = new OutputStreamWriter(conn.getOutputStream());

                req = new HTTPRequest(in);
                client = new Client(req, out);
                requests.add(client);

                //Logger.debug(addr + " sent request, path=" + req.path);
                req_count++;
            }
            catch (IOException exc) {
                Logger.warn(addr + " " + exc);
                continue;

            }
            catch (HTTPParseException exc) {
                Logger.warn(addr + " " + exc);
                continue;
            }
        }
    }
}
