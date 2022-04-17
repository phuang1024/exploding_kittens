import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * HTTP conversation object.
 * HTTPRequest and HTTPResponse extend from this.
 */
public class HTTPConv {
    /**
     * HTTP uses carrige return + new line.
     */
    public static final String NEWLINE = "\r\n";

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
     * Initialize all empty.
     */
    public HTTPConv() {
        headers = new HashMap<String, String>();
        body = "";
    }

    /**
     * Initialize with given values.
     */
    public HTTPConv(Map<String, String> headers, String body)
    {
        this.headers = headers;
        this.body = body;
    }

    /**
     * Parse from raw data and store internally.
     * Please give data starting from the headers.
     */
    public void parse(Scanner in) throws HTTPParseException
    {
        headers = new HashMap<String, String>();
        body = "";

        // Parse headers
        while (true) {
            String line = in.nextLine();
            if (line.trim().length() == 0)
                break;
            parseHeader(line);
        }

        // Parse body
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
     * String representation of HTTP starting from headers.
     */
    public String toString()
    {
        String str = "";
        for (String key: headers.keySet()) {
            str += key + ": " + headers.get(key);
            str += NEWLINE;
        }
        str += NEWLINE;

        str += body;

        return str;
    }
}
