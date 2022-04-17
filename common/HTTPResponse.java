import java.util.Map;
import java.util.Scanner;


/**
 * HTTP response.
 */
public class HTTPResponse extends HTTPConv {
    /**
     * Status code.
     * e.g. 200
     */
    public int status;

    /**
     * Status reason.
     * e.g. OK
     */
    public String reason;

    /**
     * Initialize HTTPResponse.
     */
    public HTTPResponse(int status, String reason, Map<String, String> headers,
            String body)
    {
        super(headers, body);
        this.status = status;
        this.reason = reason;
    }

    /**
     * Initialize from raw data.
     */
    public HTTPResponse(String data) throws HTTPParseException
    {
        Scanner in = new Scanner(data);
        in.next();  // Protocol version
        status = Integer.parseInt(in.next());
        reason = in.next();
        in.nextLine();  // Read \n

        super.parse(in);
    }

    public String toString() {
        String str = "HTTP/1.1 " + status + " " + reason + "\n";
        str += super.toString();
        return str;
    }
}
