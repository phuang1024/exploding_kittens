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
     * @param method  Method e.g. GET
     * @param path  HTTP path
     * @param headers  Headers.
     * @param body  Body.
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
     * @param in  Input data.
     * @throws HTTPParseException
     */
    public HTTPRequest(Scanner in) throws HTTPParseException
    {
        method = in.next();
        path = in.next();
        in.nextLine();  // Protocol version

        super.parse(in);
    }

    /**
     * Request as raw HTTP data.
     */
    public String toString() {
        String str = method + " " + path + " HTTP/1.1\n";
        str += super.toString();
        return str;
    }
}
