import java.io.Writer;
import java.util.Map;


/**
 * HTTP request.
 */
public class HTTPRequest {
    /**
     * HTTP uses carrige return + new line.
     */
    public static final String NEWLINE = "\r\n";

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
     * Request headers.
     * Map name to value.
     */
    public Map<String, String> headers;

    /**
     * Request body.
     */
    public String body;

    /**
     * Initialize HTTPRequest.
     */
    public HTTPRequest(String method, String path, Map<String, String> headers,
            String body)
    {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Parse request from raw data.
     */
    public HTTPRequest(String data) throws HTTPParseException
    {
    }

    /**
     * String representation of HTTP request.
     */
    public String toString()
    {
        String str = "";
        str += method.toUpperCase() + " ";
        str += path + " ";
        str += "HTTP/1.1" + NEWLINE;

        for (String key: headers.keySet()) {
            str += key + ": " + headers.get(key);
            str += NEWLINE;
        }
        str += NEWLINE;

        str += body;

        return str;
    }
}
