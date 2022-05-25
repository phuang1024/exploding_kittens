import java.util.*;


/**
 * Used client side to package some information about the game.
 */
public class GameInfo {
    /**
     * Number of cards in the deck.
     */
    public int deckCardCount;

    /**
     * Number of cards of each player starting from you.
     */
    public int[] playerCardCount;

    /**
     * Active player index.
     */
    public int activePlayerNumber;

    /**
     * Top card of discard pile.
     */
    public int topCard;

    /**
     * Index of you.
     */
    public int playerIndex;

    /**
     * Initializes the array.
     */
    public GameInfo() {
        playerCardCount = new int[4];
    }
}
