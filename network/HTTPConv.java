import java.io.Writer;
import java.util.*;


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
     * @param headers  Map of header key to value.
     * @param body  Body data.
     */
    public HTTPConv(Map<String, String> headers, String body)
    {
        this.headers = headers;
        this.body = body;
    }

    /**
     * Add a header.
     * @param name  Header name or key.
     * @param value  Header value.
     */
    public void putHeader(String name, String value) {
        headers.put(name.toLowerCase(), value);
    }

    /**
     * Parse from raw data and store internally.
     * Please give data starting from the headers.
     * @param in  Input scanner.
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
        if (!headers.containsKey("body-length"))
            throw new HTTPParseException("No header Body-Length.");

        // Parse body
        int len = Integer.parseInt(headers.get("body-length"));
        int tries = 0;
        while (body.length() < len && tries < 100000) {
            tries++;
            try {
                body += in.next();  // TODO sometimes doesnt work
            } catch (NoSuchElementException exc) {
                continue;
            }
        }
    }

    /**
     * Parse raw header and store internally.
     * @param data  Raw header data. e.g. "Host: asdf"
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
        putHeader(name, value);
    }

    /**
     * String representation of HTTP starting from headers.
     */
    public String toString()
    {
        String str = "";

        if (headers != null) {
            for (String key: headers.keySet())
                str += key + ": " + headers.get(key) + NEWLINE;
        }
        if (headers == null || !headers.containsKey("body-length")) {
            str += "Body-Length: " + body.length() + NEWLINE;
        }
        str += NEWLINE;

        str += body;

        return str;
    }
}
