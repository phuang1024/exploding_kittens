import java.io.IOException;
import java.util.*;


/**
 * Server main entry point.
 * Run this class to start the server.
 */
public class Main {
    /**
     * Port the server binds to.
     */
    public static final int PORT = 8016;

    /**
     * Main entry point.
     * @param args  CLI args.
     */
    public static void main(String[] args) {
        Logger.info("Starting server.");

        try {
            Server server = new Server(PORT);
            server.start();  // Runs in a different thread.

            Manager man = new Manager(server);
            man.start();  // Blocks this thread.
        }
        catch (IOException exc) {
            Logger.error(exc.toString());
        }
    }
}
