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
     * Top 4 cards of discard pile.
     */
    public int[] topCards;

    /**
     * Index of you.
     */
    public int playerIndex;

    /**
     * Number of attacks piled on next player.
     */
    public int attackCounter;

    /**
     * Initializes the arrays.
     */
    public GameInfo() {
        playerCardCount = new int[4];
        topCards = new int[3];
    }
}
