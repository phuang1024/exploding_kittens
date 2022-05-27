import java.util.ArrayList;

/**
 * stores information for a player within the game
 */
public class Player {
    private String id; //for backend reference
    private ArrayList<Integer> hand;
    private boolean inGame;

    /**
     * Number of times player defused an exploding kitten.
     * NOT number of defuses in hand.
     * Used to show "You defused" screen.
     */
    private int defuseCount;

    //Constructors
    /**
     * constructs a player with attributes
     * @param id of player
     */
    public Player(String id) {
        this.id = id;
        hand = new ArrayList<Integer>();
        inGame = true;
        defuseCount = 0;
    }

    /**
     * removes itself from the game
     */
    public void removeFromGame() {
        inGame = false;
    }
    /**
     * updates the players ID
     * @param id being set as player's id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * adds cards to this player's hands specified by an arraylist of cards to be added
     * @param cardList cards to be added
     */
    public void addCards(ArrayList<Integer> cardList) {
        for (Integer i : cardList) {
            addCard(i);
        }
    }

    /**
     * adds a card to this player's hands specified by an integer
     * @param i card to be added
     */
    public void addCard(Integer i) {
        hand.add(i);
    }

    /**
     * removes a specified card from this player's hand
     * @param i card to be removed
     * @return true if player's hand contained this card
     *  false if player's hand did not contain this card
     */
    public boolean removeCard(Integer i) {
        return hand.remove(i);
    }

    /**
     * clears the players hand
     * @return arraylist containing players cards before clearing
     */
    public ArrayList<Integer> clearHand() {
        ArrayList<Integer> temp = hand;
        hand = new ArrayList<Integer>();
        return temp;
    }

    /**
     * Increment this.defuseCount
     */
    public void incDefuseCount() {
        defuseCount++;
    }

    //Getters
    /**
     * returns player's id
     * @return player id
     */
    public String getId() {
        return id;
    }

    /**
     * returns if this player is in the game or not
     * @return true if player is in game
     *  false if player is not in game
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * returns this players hand
     * @return arrayList of this player's hand
     */
    public ArrayList<Integer> getHand() {
        return hand;
    }

    /**
     * Return this.defuseCount
     * @return  Defuse counter.
     */
    public int getDefuseCount() {
        return defuseCount;
    }

    //Methods

    /**
     * checks if this player has a defuse or not
     * @return true if this player has a defuse
     *  false if this player does not have a defuse
     */
    public boolean hasDefuse() {
        for (int i : hand) {
            if (i == Card.DEFUSE) {
                return true;
            }
        }
        return false;
    }

    //Testing
    /**
     * returns a string representation of this player
     * @return String containing player information
     */
    public String toString() {
        return "id: " + id + "  hand: " + hand.toString();
    }

    /**
     * Used to test player.java
     * @param args cli args.
     */
    public static void main(String[] args) {
        Player p1 = new Player("1234");
        p1.addCard(Card.DEFUSE);
        p1.addCard(Card.BEARD_CAT);
        p1.addCard(Card.CATTERMELON);
        p1.addCard(Card.FAVOR);

        System.out.println(p1);
    }
}
