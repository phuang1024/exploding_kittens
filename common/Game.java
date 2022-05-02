import java.util.ArrayList;

/**
 * Processes game logic
 */
public class Game {

    private ArrayList<Player> pList;
    private Deck deck;
    private Stack discardPile;
    private String id;

    /**
     * constructs a Game object with the following parameters:
     * @param p1 player 1 
     * @param p2 player 2
     * @param p3 player 3
     * @param p4 player 4
     * @param id game id
     */
    public Game(Player p1, Player p2, Player p3, Player p4, String id)
    {
        pList = new ArrayList<Player>(4);
        deck = new Deck();
        discardPile = new Stack();
        this.id = id;
    }
    public Integer lastPlayed() {
        return discardPile.peek();
    }

    public Player getPlayer(int playerId)
    {
        return pList.get(playerId);
    }
    public ArrayList<Player> getPlayers()
    {
        return pList;
    }
    public Deck getDeck()
    {
        return deck;
    }
    public Stack getDiscardPile()
    {
        return discardPile;
    }
    public String getId()
    {
        return id;
    }
}
