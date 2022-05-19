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
     * @param status  Status code.
     * @param String  Reason of status.
     * @param headers  Headers.
     * @param body  Body.
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
     * @param in  Input data.
     */
    public HTTPResponse(Scanner in) throws HTTPParseException
    {
        in.next();  // Protocol version
        status = Integer.parseInt(in.next());
        reason = in.next();
        in.nextLine();  // Read \n

        super.parse(in);
    }

    /**
     * Response as raw HTTP data.
     */
    public String toString() {
        String str = "HTTP/1.1 " + status + " " + reason + "\n";
        str += super.toString();
        return str;
    }
}
