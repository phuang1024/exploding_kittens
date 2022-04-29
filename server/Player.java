public class Player {
    private String ID;
    private String playerName;

    public Player(String ID, String name)
    {
        this.ID = ID;
        playerName = name;
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
