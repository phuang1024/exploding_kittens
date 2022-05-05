import java.util.*;


/**
 * Processes game logic
 */
public class Game {

    private ArrayList<Player> pList;
    private Deck deck;
    private Stack<Integer> discardPile;
    private String id;

    /**
     * constructs a Game object with the following parameters:
     * @param p1 player 1 
     * @param p2 player 2
     * @param p3 player 3
     * @param p4 player 4
     * @param id game id
     */
    public Game(Player p0, Player p1, Player p2, Player p3, String id)
    {
        pList = new ArrayList<Player>(4);
        pList.add(p0);
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        deck = new Deck();
        discardPile = new Stack<Integer>();
        this.id = id;
    }

    /**
     * @return the last card played
     */
    public int lastPlayed() {
        return (int)discardPile.peek();
    }

    public int getPlayerNum(String Id)
    {
        for (int i = 0; i < pList.size(); i++)
        {
            if (pList.get(i).getId().equals(Id))
            {
                return i;
            }
        }
        return -1;
    }

    public String getPlayerName(String Id)
    {
        return pList.get(getPlayerNum(Id)).getName();
    }

    public ArrayList<Integer> getHand( int playerId)
    {
        return getPlayer(playerId).getHand();
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
    public Stack<Integer> getDiscardPile()
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

    public String toString()
    {
        String gameInfo = "Game ID: " + id + "\n" + "\n";

        for (int i = 0; i < pList.size(); i++)
        {
            gameInfo += "p" + i + ": " + pList.get(i).toString() + "\n";
        }

        gameInfo += "\n" + "Deck (bottom to top): " + deck;
        gameInfo += "\n" + "Discard Pile (bottom to top):" + discardPile;

        return gameInfo;
    }
    //Game Logic methods

    /**
     * 
     * @param cardId card being played
     * @param playerId id of player playing card
     * @param receiverId id of card being played towards (if applicable)
     */
    private void playCard(int cardId, int playerId, int receiverId)
    {
        discardPile.push(cardId);

        switch (cardId) {
            case Card.EXPLODING_KITTEN:
                break;
            case Card.DEFUSE:
                break;
            case Card.ATTACK:
                break;
            case Card.SKIP:
                break;
            case Card.SEE_THE_FUTURE:
                break;
            case Card.SHUFFLE:
                break;
            case Card.FAVOR:
                break;
            case Card.BEARD_CAT:
                break;
            case Card.CATTERMELON:
                break;
            case Card.HAIRY_POTATO_CAT:
                break;
            case Card.RAINBOW_RALPHING_CAT:
                break;
            case Card.TACOCAT:
                break;
        }
    }

    //Private helpers

    /**
     * 
     * @param playerId of player to be returned
     * @return player with given id
     */
    private Player getPlayer(int playerId)
    {
        return pList.get(playerId);
    }

    //Main

    public static void main(String[] args)
    {
        Player p0 = new Player("000", "Andrew");
        Player p1 = new Player("111", "Bob");
        Player p2 = new Player("222", "Carl");
        Player p3 = new Player("333", "Drew");

        Game g1 = new Game(p0, p1, p2, p3, "1234");
        System.out.println(g1);
    }
}
