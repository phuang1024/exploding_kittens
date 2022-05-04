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
    public String getID()
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
}
