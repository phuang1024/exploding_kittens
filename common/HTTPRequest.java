import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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
        headers = new HashMap<String, String>();
        body = "";

        Scanner in = new Scanner(data);
        method = in.next();
        path = in.next();
        in.nextLine();  // Protocol version

        while (true) {
            String line = in.nextLine();
            if (line.trim().length() == 0)
                break;
            parseHeader(line);
        }

        while (true) {
            if (!in.hasNextLine())
                break;
            String line = in.nextLine();
            body += line;
            body += NEWLINE;
        }
    }

    /**
     * Parse raw header and store internally.
     * e.g. "Host: asdf"
     */
    public void parseHeader(String data) throws HTTPParseException
    {
        int i = 0;
        while (true) {
            if (i >= data.length())
                throw new HTTPParseException("Error parsing header.");
            if (data.charAt(i) == ':')
                break;
            i++;
        }

        String name = data.substring(0, i).trim();
        String value = data.substring(i+1).trim();
        headers.put(name, value);
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
