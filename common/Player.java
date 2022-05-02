import java.util.ArrayList;

public class Player {
    private String ID;
    private String playerName;
    private ArrayList<Integer> hand;

    public Player(String ID, String name)
    {
        this.ID = ID;
        playerName = name;
        hand = new ArrayList<Integer>();
    }

    public String getID()
    {
        return ID;
    }
    public String getName()
    {
        return playerName;
    }
}
