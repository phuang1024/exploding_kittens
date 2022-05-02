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

    /**
     * @return the last card played
     */
    public int lastPlayed() {
        return discardPile.peek();
    }

    /**
     * 
     * @param playerId of player to be returned
     * @return player with given id
     */
    public Player getPlayer(int playerId)
    {
        return pList.get(playerId);
    }

    /**
     * 
     * @return an arraylist of players in the game
     */
    public ArrayList<Player> getPlayers()
    {
        return pList;
    }

    /**
     * 
     * @return the deck
     */
    public Deck getDeck()
    {
        return deck;
    }

    /**
     * 
     * @return the discard pile
     */
    public Stack getDiscardPile()
    {
        return discardPile;
    }

    /**
     * 
     * @return game id
     */
    public String getId()
    {
        return id;
    }

    //Game Logic methods

    private playCard(int cardId)
    {
        discardPile.push(cardId);
        //TODO: finish method
    }
}
