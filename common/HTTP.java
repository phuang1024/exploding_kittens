import java.io.*;


/**
 * HTTP protocol.
 */
public class HTTP {
    public static final String NEWLINE = "\r\n";

    /**
     * Write HTTP request data to stream.
     *
     * @param stream  Out stream to write to.
     * @param method  Request method e.g. GET
     * @param path  Request path.
     * @param headers  Request headers. Map name to value.
     * @param body  Request body.
     */
    public static void sendRequest(
        Writer stream,
        String method,
        String path,
        Map<String, String> headers,
        String body)
    {
        stream.write(method.toUpperCase() + " ");
        stream.write(path + " ");
        stream.write("HTTP/1.1" + NEWLINE);

        for (String key: headers.keySet()) {
            stream.write(key + ": " + headers.get(key));
            stream.write(NEWLINE);
        }
        stream.write(NEWLINE);

        stream.write(body);
    }

}
