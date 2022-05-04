/**
 * Random utilities.
 * i.e. pseudo random generation.
 */
public class Random {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Random string containing alphabetic characters.
     */
    public static String randstr(int len) {
        StringBuffer buff = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = ALPHABET.charAt((int)(Math.random() * ALPHABET.length()));
            buff.append(ch);
        }
        return buff.toString();
    }

    public static void main(String[] sohan) {
        System.out.println(randstr(10));
    }
}
