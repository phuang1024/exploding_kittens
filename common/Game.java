import java.util.*;


/**
 * Processes game logic
 */
public class Game {

    private ArrayList<Player> pList;    //holds references to each player in the game
    private Deck deck;                  //deck
    private Stack<Integer> discardPile; //Cards already played
    private String id;                  //Game id
    private Player whosePlaying;        //Stores id of who is playing

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
        whosePlaying = p0;
    }

    /**
     * @return the last card played222
     */
    public int lastPlayed() {
        return (int)discardPile.peek();
    }

    //Game Logic methods

    /**
     * 
     * @param cardId card being played
     * @param playerId id of player playing card
     * @param receiverId id of card being played towards (if applicable)
     */
    public void playCard(int cardId, int playerId, int receiverId)
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

    public int drawCard()
    {
        int card = deck.drawCard();
        return card;
    }

    public String getTurnId()
    {
        return whosePlaying.getId();
    }

    public void reOrderPlayers(Player p0, Player p1, Player p2, Player p3)
    {
        pList.clear();
        pList.add(p0);
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
    }

    // helpers
    private void shuffleCards()
    {
        deck.shuffle();
    }

    //Accessors

    /**
     * 
     * @param playerId of player to be returned
     * @return player with given id
     */
    public Player getPlayer(String playerId)
    {
        for (int i = 0; i < pList.size(); i++)
        {
            if (pList.get(i).getId().equals(playerId))
            {
                return pList.get(i);
            }
        }
        return null;
    }

    /**
     * 
     * @return game id
     */
    public String getId()
    {
        return id;
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
     * @return the deck
     */
    public Deck getDeck()
    {
        return deck;
    }

    /**
     * 
     * @return an arraylist of players in the game
     */
    public ArrayList<Player> getPlayers()
    {
        return pList;
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

    public ArrayList<Integer> getHand(String playerId)
    {
        try
        {
            return getPlayer(playerId).getHand();
        }
        catch (NullPointerException ex)
        {
            return null;
        }
    }

    //Testing

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
    public static void main(String[] args)
    {
        Player p0 = new Player("000");
        Player p1 = new Player("111");
        Player p2 = new Player("222");
        Player p3 = new Player("333");

        Game g1 = new Game(p0, p1, p2, p3, "1234");
        
        /*
        for (Player p : g1.getPlayers())
        {
            p.addCard(Card.DEFUSE);
            p.addCard(Card.BEARD_CAT);
            p.addCard(Card.CATTERMELON);
            p.addCard(Card.FAVOR);
        }

        System.out.println(g1);

        ArrayList<Player> pList = g1.getPlayers();
        g1.reOrderPlayers(pList.get(3), pList.get(2), pList.get(1), pList.get(0));
        System.out.println("\n" + "\n" + "\n" + "**Reversing player order " + "\n" + g1);

        g1.shuffleCards();
        System.out.println("\n" + "\n" + "\n" + "**Shuffling cards " + "\n" + g1);
        */
    }
}
