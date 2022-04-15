import java.net.*;
import java.io.*;


/**
 * Handles networking and accepting clients.
 */
public class Server {
    private ServerSocket server;
    private int port;

    public Server(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port);

        Logger.info("Started socket on port " + server.getLocalPort()
            + ", ip " + server.getInetAddress());
    }
}
