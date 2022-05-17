import java.util.ArrayList;

/**
 * stores information for a player within the game
 */
public class Player {
    private String id; //for backend reference
    private String playerName; //only for GUI
    private Game Game;
    private ArrayList<Integer> hand;
    private boolean inGame;

    //Constructors
    /**
     * constructs a player with attributes
     * @param ID of player
     * @param name of player
     */
    public Player(String id, String name)
    {
        this.id = id;
        playerName = name;
        inGame = true;
        hand = new ArrayList<Integer>();
        Game = null;
    }
    
    //Setters
    /**
     * 
     * @param name being set as player name
     */
    public void setName(String name)
    {
        playerName = name;
    }
    public void setGame(Game game)
    {
        Game = game;
    }
    public void removeFromGame()
    {
        inGame = false;
    }
    /**
     * 
     * @param id being set as player's id
     */
    public void setId(String id)
    {
        this.id = id;
    }
    public void addCards(ArrayList<Integer> cardList)
    {
        for (Integer i : cardList)
        {
            addCard(i);
        }
    }
    public void addCard(Integer i)
    {
        hand.add(i);
    }
    public boolean removeCard(Integer i)
    {
        return hand.remove(i);
    }

    //Getters
    /**
     * 
     * @return player id
     */
    public String getId()
    {
        return id;
    }
    public boolean inGame()
    {
        return inGame;
    }
    /**
     * 
     * @return player name
     */
    public String getName()
    {
        return playerName;
    }
    public ArrayList<Integer> getHand()
    {
        return hand;
    }

    //Methods
    // public boolean playCard(Integer i)
    // {
    //     //TODO: finish
    //     return true; //TODO: fix
    // }
    // /**
    //  * 
    //  * @return -1 if blew up
    //  * @return 0 if defused exploding kitten
    //  * @return card
    //  */
    // public int drawCard()
    // {
    //     int card = Game.drawCard();
    //     if (card == Card.EXPLODING_KITTEN)
    //     {
    //         if (hand.contains(Card.DEFUSE))
    //         {
    //             this.playCard(Card.DEFUSE);
    //             return 0;
    //         }
    //         else
    //         {
    //             Game.removeFromGame(id); 
    //             return -1;   
    //         }
    //     }
    //     hand.add(card);
    //     return card;
    // }
    public boolean hasDefuse()
    {
        for (int i : hand)
        {
            if (i == Card.DEFUSE)
            {
                return true;
            }
        }
        return false;
    }

    //Testing
    public String toString()
    {
        return "Name: " + playerName + "  id: " + id + "  hand: " + hand.toString();
    }
    public static void main(String[] args)
    {
        Player p1 = new Player("1234", "John");
        p1.addCard(Card.DEFUSE);
        p1.addCard(Card.BEARD_CAT);
        p1.addCard(Card.CATTERMELON);
        p1.addCard(Card.FAVOR);
        
        System.out.println(p1);
    }
}
