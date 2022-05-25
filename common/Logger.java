/**
 * Logging functions.
 * Prints to stdout colored text.
 */
public class Logger {
    /**
     * ANSI code for reset.
     */
    public static final String RESET = "\033[0m";
    
    /**
     * ANSI code for cyan
     */
    public static final String BLUE = "\033[36m";
    
    /**
     * ANSI code for yellow
     */
    public static final String YELLOW = "\033[33m";
    
    /**
     * ANSI code for red
     */
    public static final String RED = "\033[31m";
    
    /**
     * ANSI code for gray
     */
    public static final String GRAY = "\033[2m\033[37m";

    /**
     * Log message with ANSI code prefix color.
     * Resets color after.
     */
    public static void log(String msg, String color) {
        System.out.print(color);
        System.out.print(msg);
        System.out.println(RESET);
    }

    /**
     * Log info (blue).
     */
    public static void info(String msg) {
        log(msg, BLUE);
    }

    /**
     * Log warning (yellow).
     */
    public static void warn(String msg) {
        log(msg, YELLOW);
    }

    /**
     * Log error (red).
     */
    public static void error(String msg) {
        log(msg, RED);
    }

    /**
     * Log debug (gray).
     */
    public static void debug(String msg) {
        log(msg, GRAY);
    }
}
