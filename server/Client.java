import java.io.*;
import java.net.Socket;


/**
 * Represents client that sent a request and is waiting for
 * a response.
 */
public class Client {
    public HTTPRequest req;
    public Writer out;

    public Client(HTTPRequest req, Writer out) {
        this.req = req;
        this.out = out;
    }

    public void sendResponse(HTTPResponse resp) {
        out.write(resp.toString());
    }
}
