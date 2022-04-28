public class Card {
    public static final Integer
        EXPLODING_KITTEN = 1 << 0,
        DEFUSE = 1 << 1,
        ATTACK = 1 << 2,
        SKIP = 1 << 3,
        SEE_THE_FUTURE = 1 << 4,
        SHUFFLE = 1 << 5,
        FAVOR = 1 << 6,
        BEARD_CAT = 1 << 7,
        CATTERMELON = 1 << 8,
        HAIRY_POTATO_CAT = 1 << 9,
        RAINBOW_RALPHING_CAT = 1 << 10,
        TACOCAT = 1 << 11;

    /**
     * Check if a given card is one of the given types.
     * Use bitwise or to use multiple types.
     * e.g. Card.is(myCard, Card.DEFUSE | Card.ATTACK);
     */
    public static boolean is(int card, int types) {
        return (card & types) > 0;
    }
}
