import java.io.*;
import java.net.Socket;


/**
 * Represents client that sent a request and is waiting for a response.
 * Holds the request that the client sent and the output stream.
 */
public class Client {
    /**
     * The client's request
     */
    public HTTPRequest request;

    private Writer out;

    /**
     * Initialize with respective values.
     * @param request  Client's request.
     * @param out  Client's output stream.
     */
    public Client(HTTPRequest request, Writer out) {
        this.request = request;
        this.out = out;
    }

    /**
     * Send HTTP response to client.
     * @param resp  Response to send.
     * @throws IOException
     */
    public void send(HTTPResponse resp) throws IOException {
        out.write(resp.toString());
        out.write("\n");
        out.flush();
    }
}
