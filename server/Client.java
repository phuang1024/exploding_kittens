import java.io.*;
import java.net.Socket;


/**
 * This class handles a client server side.
 */
public class Client {
    private Socket conn;
    private Reader in;
    private Writer out;

    public Client(Socket conn) throws IOException {
        this.conn = conn;
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
    }
}
