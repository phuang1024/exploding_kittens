import java.io.IOException;
import java.util.*;


/**
 * Run this class to start the server.
 */
public class Main {
    public static final int PORT = 8016;

    public static void main(String[] args) {
        Logger.info("Starting server.");

        try {
            Server server = new Server(PORT);
            server.start();
            System.out.println("AOIJFASIOFJ");
        }
        catch (IOException exc) {
            Logger.error(exc.toString());
        }
    }
}
