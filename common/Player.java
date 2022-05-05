import java.util.ArrayList;

/**
 * stores information for a player within the game
 */
public class Player {
    private String id;
    private String playerName;
    private ArrayList<Integer> hand;

    /**
     * constructs a player with attributes
     * @param ID of player
     * @param name of player
     */
    public Player(String id, String name)
    {
        this.id = id;
        playerName = name;
        hand = new ArrayList<Integer>();
    }

    /**
     * 
     * @return player id
     */
    public String getId()
    {
        return id;
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

    public String toString()
    {
        return "Name: " + playerName + "  id: " + id + "  hand: " + hand.toString();
    }

    /**
     * 
     * @param id being set as player's id
     */
    public void setID(String id)
    {
        this.id = id;
    }

    /**
     * 
     * @param name being set as player name
     */
    public void setName(String name)
    {
        playerName = name;
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
