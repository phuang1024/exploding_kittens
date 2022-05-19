/**
 * Error while parsing HTTP.
 */
public class HTTPParseException extends Exception {
    /**
     * Initialize with a message.
     * @param msg  Message.
     */
    public HTTPParseException(String msg) {
        super(msg);
    }
}
