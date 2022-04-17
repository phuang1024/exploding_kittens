import java.io.*;
import java.net.Socket;


/**
 * Represents client that sent a request and is waiting for
 * a response.
 */
public class Client {
    public HTTPRequest request;
    private Writer out;

    public Client(HTTPRequest request, Writer out) {
        this.request = request;
        this.out = out;
    }

    /**
     * Send HTTP response to client.
     */
    public void send(HTTPResponse resp) throws IOException {
        out.write(resp.toString());
        out.flush();
    }
}
