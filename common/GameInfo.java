import java.util.*;

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
