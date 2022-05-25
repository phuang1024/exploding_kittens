/**
 * This class has static attributes and methods that represent types of cards.
 */
public class Card {
    /**
     * Card IDs with only one bit set.
     */
    public static final int
        EXPLODING_KITTEN = 1 << 0,
        DEFUSE = 1 << 1,
        ATTACK = 1 << 2,
        SKIP = 1 << 3,
        SEE_THE_FUTURE = 1 << 4, // unused
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
     * @param card checks if card is within types
     * @param types cards checked against
     */
    public static boolean is(int card, int types) {
        return (card & types) > 0;
    }

    /**
     * Test card.
     */
    public static void main(String[] args) {
        int card = DEFUSE;
        System.out.println("Card is DEFUSE (" + card + ")");

        System.out.println("Card is ATTACK? " + is(card, ATTACK));
        System.out.println("Card is DEFUSE? " + is(card, DEFUSE));
        System.out.println("Card is A | D? " + is(card, ATTACK | DEFUSE));
        System.out.println("Card is SKIP | A? " + is(card, SKIP | ATTACK));
    }
}
