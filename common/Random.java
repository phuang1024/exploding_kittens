/**
 * Random utilities.
 * i.e. pseudo random generation.
 */
public class Random {
    /**
     * All alphabet letters.
     */
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Random string containing alphabetic characters.
     * @param len  Length of string.
     * @return Randomly generated string with length len.
     */
    public static String randstr(int len) {
        StringBuffer buff = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char ch = ALPHABET.charAt((int)(Math.random() * ALPHABET.length()));
            buff.append(ch);
        }
        return buff.toString();
    }

    /**
     * Test
     */
    public static void main(String[] sohan) {
        System.out.println(randstr(10));
    }
}
