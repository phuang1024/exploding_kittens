import java.util.ArrayList;

public class GameLogic {

    ArrayList<Player> pList;
    Deck deck;
    Card topCard;
    public GameLogic(Player p1, Player p2, Player p3, Player p4)
    {
        pList = new ArrayList<Player>(4);
        deck = new Deck();
        topCard = null;
    }
}
