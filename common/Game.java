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
    private int attackCounter;          //current attackCounter;
    private boolean attackPlayed;       //if current player is the one who played the last attack card

    /**
     * constructs a Game object with the following parameters:
     * @param p1 player 1 
     * @param p2 player 2
     * @param p3 player 3
     * @param p4 player 4
     * @param id game id
     */
    public Game(Player p0, Player p1, Player p2, Player p3, String id) {
        pList = new ArrayList<Player>(4);
        pList.add(p0);
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        deck = new Deck();
        deck.shuffle();

        initializeHand(p0);
        initializeHand(p1);
        initializeHand(p2);
        initializeHand(p3);

        deck.addCards(Card.EXPLODING_KITTEN, 3);
        deck.addCards(Card.DEFUSE, 3/*6*/);

        deck.shuffle();
        discardPile = new Stack<Integer>();
        this.id = id;
        whosePlaying = p0;
        attackCounter = 0;
        attackPlayed = false;
    }

    /**
     * @return the last card played
     */
    public int lastPlayed() {
        return (int)discardPile.peek();
    }

    //Game Logic methods

    /**
     * 
     * @param cards being played
     * @return 0 skip card was executed
     * @return 1 deck was shuffled
     * @return 2 favor/cat card was played
     * @return failed to call any card
     */
    public int playCard(int[] cards) {
        int cardId = cards[0];
        return playCard(cardId);
    }

    /**
     * plays card from hand of current player
     * @param cardId card being played
     * @return 0 skipped
     * @return 1 shuffled
     * @return 2 favor/cat card
     * @return 3 favor/cat card played  but all other player hands were empty so no cards stolen
     * @return 4 attack card played
     * @return failed to call any card
     */
    public int playCard(int cardId) {
        System.out.println("Processing card " + cardId);

        discardPile.push(cardId);
        whosePlaying.removeCard(cardId);

        switch (cardId) {
            case Card.ATTACK:
                System.out.println("Playing Attack Card");
                attackCounter += 2;
                attackPlayed = true;
                endTurn();
                return 3;
            case Card.SKIP:
                System.out.println("Playing Skip Card");
                endTurn();
                return 0;
            // case Card.SEE_THE_FUTURE:
            //     nextPlayer();
            //     return 1;
            case Card.SHUFFLE:
                System.out.println("Playing Shuffle Card");
                shuffleCards();
                return 1;
            case Card.BEARD_CAT:
            case Card.CATTERMELON:
            case Card.HAIRY_POTATO_CAT:
            case Card.RAINBOW_RALPHING_CAT:
            case Card.TACOCAT:
                whosePlaying.removeCard(cardId);
                discardPile.push(cardId);
            case Card.FAVOR:
                System.out.println("Playing Cat or Favor Card");
                Player next = nextPlayer();
                ArrayList<Integer> hand = next.getHand();

                while (hand.size() == 0 && !next.equals(whosePlaying)) {
                    next = nextPlayer(next.getId());
                    hand = next.getHand();
                }
                
                if (next.equals(whosePlaying)) {
                    return 3;
                }

                int rand = (int)(hand.size()*Math.random());
                whosePlaying.addCard(hand.get(rand));
                next.removeCard(hand.get(rand));
                return 2;
        }
        return 4;
    }

    /**
     * draws a card into the current players hand
     * @return -2 defused exploding kitten successfully
     * @return -1 player drew exploding kitten and blew up
     * @return 0 if player blew up and game ended
     * @return other = card id of card drawn
     */
    public int drawCard() {
        int card = deck.drawCard();
        if (card == Card.EXPLODING_KITTEN)
        {
            if (whosePlaying.hasDefuse()) {
                whosePlaying.removeCard(Card.DEFUSE);
                whosePlaying = nextPlayer();
                deck.insertBomb();
                return -2;
            } else {
                whosePlaying.removeFromGame();
                if (detectWin() != null)
                {
                    return 0;
                }
                return -1;
            }
        }
        whosePlaying.addCard(card);
        return card;
    }

    /**
     * ends the current players turn
     * @return next player
     */
    public Player endTurn() {
        if (attackPlayed = false) {
            while (attackCounter > 0) {
                attackCounter--;
                return whosePlaying;
            }
        }
        attackPlayed = false;
        whosePlaying = nextPlayer();
        return whosePlaying;
    }

    /**
     * Id of player currently playing
     * @return id of player currently playing
     */
    public String getTurnId() {
        return whosePlaying.getId();
    }

    /**
     * reorders the players in the game
     * @param p0 new player 0
     * @param p1 new player 1
     * @param p2 new player 2
     * @param p3 new player 3
     */
    public void reOrderPlayers(Player p0, Player p1, Player p2, Player p3) {
        pList.clear();
        pList.add(p0);
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
    }

    /**
     * Checks the game for a win by one of the players
     * @return the Id of the winning player if there is a win detected
     * @return null if there is no win detected
     */
    public String detectWin() {
        if (alivePlayerCount() == 1) {
            for (Player p : pList) {
                if (p.isInGame()) {
                    return p.getId();
                }
            }
        }
        return null;
    }

    /**
     * 
     * @return number of players currently in the game
     */
    public int alivePlayerCount() {
        int pCount = 0;
        for (Player p : pList) {
            if (p.isInGame()) {
                pCount++;
            }
        }
        return pCount;
    }

    // helpers
    private void shuffleCards() {
        deck.shuffle();
    }

    /**
     * Returns the next player in turn order, skipping over eliminated players
     * @return next player
     */
    public Player nextPlayer() {
        return nextPlayer(whosePlaying.getId());
    }

    /**
     * Returns the player after specified player in turn order, skipping over eliminated players
     * @param prevPlayerID ID of specified player. Player after this player's ID will be returned
     * @return ID of player after specified player in turn order
     */
    public Player nextPlayer(String prevPlayerID) {
        int num = getPlayerNum(prevPlayerID);
        if (num == 3) {
            num = 0;
        }
        else {
            num++;
        }
        while (!pList.get(num).isInGame()) {
            if (num == 3) {
                num = 0;
            } else {
                num++;
            }
        }
        return pList.get(num);
    }

    
    private void initializeHand(Player p) {
        for (int i = 0; i < 6; i++) {
            p.addCard(deck.drawCard());
        }
        p.addCard(Card.DEFUSE);
    }


    //Accessors

    /**
     * returns a specific player specified by its ID
     * @param playerId of player to be returned
     * @return player with given id
     */
    public Player getPlayer(String playerId) {
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getId().equals(playerId)) {
                return pList.get(i);
            }
        }
        return null;
    }

    /**
     * return this game's id
     * @return game id
     */
    public String getId() {
        return id;
    }

    /**
     * returns the discard pile
     * @return the discard pile
     */
    public Stack<Integer> getDiscardPile() {
        return discardPile;
    }

    /**
     * returns the deck
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * returns an arraylist of players
     * @return an arraylist of players in the game
     */
    public ArrayList<Player> getPlayers() {
        return pList;
    }

    /**
     * returns the player number of specified player
     * @param Id ID of player
     * @return number of specified player
     * @return -1 if playter not found
     */
    public int getPlayerNum(String Id) {
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getId().equals(Id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns hand of specified player
     * @param playerId ID of player
     * @return hand of player with ID playerID
     * @return null if player not found
     */
    public ArrayList<Integer> getHand(String playerId) {
        try {
            return getPlayer(playerId).getHand();
        }
        catch (NullPointerException ex) {
            return null;
        }
    }

    public Player getWhosePlaying() {
        return whosePlaying;
    }

    //Testing

    /**
     * Returns a text block showing all the games information
     * @return String representation of the game
     */
    public String toString() {
        String gameInfo = "Game ID: " + id + "\n" + "\n";

        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).equals(whosePlaying)) {
                gameInfo+= "**Current Player**";
            }
            gameInfo += "p" + i + ": " + pList.get(i).toString() + "\n";
        }

        gameInfo += "\n" + "Deck (bottom to top): " + deck;
        gameInfo += "\n" + "Discard Pile (bottom to top):" + discardPile;

        return gameInfo;
    }
    /**
     * Used to test Game.java
     * @param args
     */
    public static void main(String[] args) {
        Player p0 = new Player("000");
        Player p1 = new Player("111");
        Player p2 = new Player("222");
        Player p3 = new Player("333");

        Game g1 = new Game(p0, p1, p2, p3, "1234");

        p0.clearHand();
        p0.addCard(Card.CATTERMELON);
        p0.addCard(Card.CATTERMELON);

        System.out.println(g1);
        g1.playCard(Card.CATTERMELON);
        System.out.println(g1);

        p0.addCard(Card.TACOCAT);
        p0.addCard(Card.TACOCAT);
        System.out.println(g1);
        g1.playCard(Card.TACOCAT);
        System.out.println(g1);
    }
}
