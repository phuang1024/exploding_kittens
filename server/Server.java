import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Handles networking and accepting clients.
 */
public class Server extends Thread {
    private ServerSocket server;
    private int port;

    public Queue<Client> requests;

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

        while (true) {
            Socket conn;
            String addr = "";
            HTTPRequest req;
            Client client;

            try {
                conn = server.accept();
                addr = conn.getInetAddress().toString();
                Scanner in = new Scanner(conn.getInputStream());
                Writer out = new OutputStreamWriter(conn.getOutputStream());

                req = new HTTPRequest(in);
                client = new Client(req, out);
                requests.add(client);

                Logger.debug(addr + " sent request, path=" + req.path);
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
