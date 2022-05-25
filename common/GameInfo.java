import java.util.*;

/**
 * Stores information of the game to transfer between client and server
 */
public class GameInfo
{
    int deckCardCount;
    int[] playerCardCount;
    int activePlayerNumber;
    int topCard;
    int playerIndex;

    public GameInfo() {
       playerCardCount = new int[4];
    }
}
