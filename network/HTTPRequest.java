import java.util.Map;
import java.util.Scanner;


/**
 * HTTP request.
 */
public class HTTPRequest extends HTTPConv {
    /**
     * Request method.
     * e.g. GET
     */
    public String method;

    /**
     * Request path.
     * e.g. /explode/cat
     */
    public String path;

    /**
     * Initialize HTTPRequest.
     */
    public HTTPRequest(String method, String path, Map<String, String> headers,
            String body)
    {
        super(headers, body);
        this.method = method;
        this.path = path;
    }

    /**
     * Initialize from raw data.
     */
    public HTTPRequest(Scanner in) throws HTTPParseException
    {
        method = in.next();
        path = in.next();
        in.nextLine();  // Protocol version

        super.parse(in);
    }

    public String toString() {
        String str = method + " " + path + " HTTP/1.1\n";
        str += super.toString();
        return str;
    }
}
