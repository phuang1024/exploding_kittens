/**
 * Logging functions.
 * Prints to stdout colored text.
 */
public class Logger {
    public static final String RESET = "\033[0m";
    public static final String BLUE = "\033[34m";
    public static final String YELLOW = "\033[33m";
    public static final String RED = "\033[31m";

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
}
