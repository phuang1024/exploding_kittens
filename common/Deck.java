import java.util.LinkedList;

/**
 * Manages the game deck
 */
public class Deck {
    private LinkedList<Integer> pile;

    //Constructors

    /**
     * constructs a 4 player game deck with custom card counts
     */
    public Deck() {
        this(0 /*3*/, 0, 5, 5, 0/*3*/, 3, 4, 5);
    }

    /**
     * constructs a deck with a custom amount of cards
     * @param numExplodingKitten    default : 3
     * @param numDefuse             default : 6
     * @param numAttack             default : 4
     * @param numSkip               default : 4
     * @param numSeeTheFuture       default : 3
     * @param numShuffle            default : 2
     * @param numFavor              default : 2
     * @param numCat                default : 3 for each type of Cat
     */
    public Deck(
        int numExplodingKitten, 
        int numDefuse, 
        int numAttack, 
        int numSkip, 
        int numSeeTheFuture, 
        int numShuffle, 
        int numFavor, 
        int numCat) {
        pile = new LinkedList<Integer>();
        addCards(numExplodingKitten,
            numDefuse,
            numAttack,
            numSkip,
            numSeeTheFuture,
            numShuffle,
            numFavor,
            numCat);
    }

    //Methods
    /**
     * returns the top card of the deck and removes it from the deck
     * @return top card of the deck
     */
    public Integer drawCard() {
        return pile.removeFirst();
    }

    /**
     * inserts an exploding kitten card at a random index within the deck
     */
    public void insertBomb() {
        int index = (int)(pile.size()*Math.random());
        insertBombAt(index);
    }

    /**
     * inserts an exploding kitten into the deck at a specific index
     * @param index at which bomb is added
     */
    public void insertBombAt(int index) {
        pile.add(index, Card.EXPLODING_KITTEN);
    }

    /**
     * view card at specified index
     * @param index of the card to be returned
     * @return card at given index
     */
    public Integer viewCard(int index) {
        return pile.get(index);
    }

    /**
     * returns amount of cards in the deck
     * @return amount of cards in the deck
     */
    public int cardCount() {
        return pile.size();
    }

    /**
     * shuffles the deck
     */
    public void shuffle() {
        LinkedList<Integer> copy = new LinkedList<Integer>(pile);
        pile.clear();

        while (copy.size() > 0) {
            int index = (int)(Math.random()*copy.size());
            pile.add(copy.get(index));
            copy.remove(index);
        }
    }

    /**
     * returns a visual string representation of deck
     * @return String containing deck information
     */
    public String toString() {
        return pile.toString();
    }
    
    //Constructor Helpers

    /**
     * adds specified quantity of a specific card to the deck
     * @param cardID        card to be added
     * @param cardCount     amount of cards (type cardID) to be added
     */
    public void addCards(Integer cardID, int cardCount) {
        for (int i = 0 ; i < cardCount; i++)
            pile.add(cardID);
    }
    
    /**
     * automatically adds custom amount for a large variety of cards
     * @param numExplodingKitten
     * @param numDefuse
     * @param numAttack
     * @param numSkip
     * @param numSeeTheFuture
     * @param numShuffle
     * @param numFavor
     * @param numCat
     */
    private void addCards(
        int numExplodingKitten, 
        int numDefuse, 
        int numAttack, 
        int numSkip, 
        int numSeeTheFuture, 
        int numShuffle, 
        int numFavor, 
        int numCat) {
        addCards(Card.EXPLODING_KITTEN, numExplodingKitten);
        addCards(Card.DEFUSE, numDefuse);
        addCards(Card.ATTACK, numAttack);
        addCards(Card.SKIP, numSkip);
        addCards(Card.SEE_THE_FUTURE, numSeeTheFuture);
        addCards(Card.SHUFFLE, numShuffle);
        addCards(Card.FAVOR, numFavor);
        addCards(Card.BEARD_CAT, numCat);
        addCards(Card.CATTERMELON, numCat);
        addCards(Card.HAIRY_POTATO_CAT, numCat);
        addCards(Card.RAINBOW_RALPHING_CAT, numCat);
        addCards(Card.TACOCAT, numCat);
    }
    
    //Testers

    /**
     * Used to test deck.java
     * @param args  CLI args.
     */
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.toString());

        deck.shuffle();
        System.out.println("\n" + "shuffled:" + deck.toString());
        
        deck.insertBombAt(3);
        System.out.println("\n" + deck.toString());

        System.out.println("Card at index 3: " + deck.viewCard(3));
        System.out.println("Card at index 4: " + deck.viewCard(4));
    }
}
