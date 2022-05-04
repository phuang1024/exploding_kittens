import java.util.ArrayList;

/**
 * stores information for a player within the game
 */
public class Player {
    private String ID;
    private String playerName;
    private ArrayList<Integer> hand;

    /**
     * constructs a player with attributes
     * @param ID of player
     * @param name of player
     */
    public Player(String ID, String name)
    {
        this.ID = ID;
        playerName = name;
        hand = new ArrayList<Integer>();
    }

    /**
     * 
     * @return player id
     */
    public String getID()
    {
        return ID;
    }

    /**
     * 
     * @return player name
     */
    public String getName()
    {
        return playerName;
    }

    /**
     * 
     * @param id being set as player's id
     */
    public void setID(String id)
    {
        ID = id;
    }

    /**
     * 
     * @param name being set as player name
     */
    public void setName(String name)
    {
        playerName = name;
    }
}
