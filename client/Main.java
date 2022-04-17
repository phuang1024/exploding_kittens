import java.net.*;
import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        Logger.info("Server IP=" + Conn.IP + ", port=" + Conn.PORT);

        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter message: ");
                String line = in.nextLine();

                HTTPRequest req = new HTTPRequest("GET", "/test",
                    new HashMap<String, String>(), line);
                Conn conn = new Conn(req);
                conn.send();
                HTTPResponse resp = conn.recv();
                System.out.println("Server responded: " + resp.body.trim());
            }
            catch (IOException exc) {
                Logger.error(exc.toString());
            }
            catch (HTTPParseException exc) {
                Logger.error(exc.toString());
            }
        }
    }
}
