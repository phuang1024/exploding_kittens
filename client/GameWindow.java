import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.security.cert.PolicyQualifierInfo;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class serves as the GUI of the game. It has all the necessities that 
 * one would need while playing exploding kittens.
 */
public class GameWindow
{
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private String playerID;
    private String gameID;

    private JFrame frame;

    //The componenets of the frame
    private MyJPanel components;
    private MyJPanel playerCards;

    private ArrayList<CardButton> hand = new ArrayList<CardButton>();
    private ArrayList<CardButton> selectedCards = new ArrayList<CardButton>();

    private int currentDiscCard;
    private boolean gameEnded;

    JLabel [] playerCardCounts;
    JLabel activePlayerTracker;
    JLabel deckCounter;
    int playerNum;
    


    /**
     * Constructor for GameWindow.
     * Initializes a new window with which sends commands to the server using 
     * gameID and playerID
     * @param playerID the ID of this player
     * @param gameID the ID of the game the player is in
     */
    public GameWindow(String playerID, String gameID)
    {
        this.playerID = playerID;
        this.gameID = gameID;
        gameEnded = false;
        //Creates a new JFrame for the UI to be on
        frame = new JFrame("Exploding Kittens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);

        try
        {
            GameInfo info = Conn.getStatus(playerID, gameID);
            playerNum = info.playerIndex;
        }
        catch (Exception f)
        {
            f.printStackTrace();
        }
        addComponents();
        addBackground();

        
        addCard(1<<5);
        addCard(1<<7);

        frame.setVisible(true);
        frame.setEnabled(true);
        startUpdating();
    }

    private void startUpdating()
    {
        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask(){
        public void run()
        {
            try
            {
                GameInfo info = Conn.getStatus(playerID, gameID);
                List<Integer> hand = Conn.getHand(playerID, gameID);
                updateScreen(info, hand);
            }
            catch (Exception f)
            {
                f.printStackTrace();
            }
            if (gameEnded)
            {
                t.cancel();
            }
        }
        } ,0 , 250);
    } 

    private void addPlayCardButton()
    {
        JButton btn = new JButton("Play Selected Cards");
        btn.addActionListener(new PlayCardsListener());
        btn.setSize(150, 30);
        btn.setLocation(660, 480);
        btn.setVisible(true);
        components.add(btn);
    }

    private void addEndTurnButton()
    {
        JButton btn = new JButton("End Turn");
        btn.addActionListener(new EndTurnListener());
        btn.setSize(150, 30);
        btn.setLocation(455, 480);
        btn.setVisible(true);
        components.add(btn);
    }

    /**
     * Updates the screen with information given by the user
     * 
     * @param info the information to use to update the screen, given in GameInfo, a wrapper class
     * @param hand The cards in the player's hand
     */
    public void updateScreen(GameInfo info, List<Integer> hand) 
    {
        updateScreen(info.playerCardCount, info.topCard, hand, info.activePlayerNumber, info.deckCardCount);
    }

    private void createActivePlayerTracker() 
    {
        JLabel lb = new JLabel("Player " + playerNum + "'s (your) turn!");
        lb.setSize(160,50);
        lb.setLocation(new Point(1130, 0));
        lb.setForeground(Color.BLACK);
        lb.setFont(new Font("Dialog", Font.PLAIN, 13));
        lb.setVisible(true);
        components.add(lb);
        activePlayerTracker = lb;
        return;
    }

    private void updateActivePlayer(int currentPlayer)
    {
        if (currentPlayer == playerNum)
        {
            activePlayerTracker.setText("Player " + currentPlayer + "'s (your) turn!");
            return;
        }
        activePlayerTracker.setText("Player " + currentPlayer + "'s turn");
    }

    private void updateScreen(int [] playerCardCounts, int centerCard, List<Integer> playerHand, int currentPlayer, int deckCardCount)
    {
        //Updates the opponenets' card counts
        for (int i = 0; i < 4; i++)
        {
            if (i != playerNum)
                updateCardCount(i, playerCardCounts[i]);
        }

        //Updates middle card
        if (centerCard != -1 && centerCard != currentDiscCard)
        {
            String middleCard = cardNumToPath(centerCard);
            addImage(middleCard, new Dimension(200,282), new Point(540, 219));
        }

        //Updates Hand
        boolean handIsSame = true;
        for (int i = 0; i < playerHand.size(); i++)
        {
            if (hand.get(i).getCardNum() != playerHand.get(i))
            {
                handIsSame = false;
            }
        }
        if (!handIsSame)
        {
            playerCards.removeAll();
            for (int i = 0; i < playerHand.size(); i++)
            {
                addCard(playerHand.get(i));
            }
        }

        //updates the current player
        updateActivePlayer(currentPlayer);

        //Updates deck
        deckCounter.setText("<html>Deck:<br/>" + deckCardCount + " cards</html>");
    }

    private void updateCardCount(int playerToUpdate, int newNum)
    {
        if (playerToUpdate == playerNum)
            return;
        int pl = playerToUpdate + 1;
        playerCardCounts[playerToUpdate].setText("<html>Player " + pl + ":<br/>" + newNum + " cards</html>");
    }

    /**
     * Adds a card to the player's hand
     * @param cardToAdd the integer representation of the card to be added
     */
    public void addCard(int cardToAdd)
    {
        if (playerCards == null)
        {
            playerCards = new MyJPanel(new FlowLayout());
            playerCards.setBackground(Color.BLACK);
            //playerCards.setBackgroundImg(background);
            frame.add(playerCards, BorderLayout.SOUTH);
        }
        try 
        {
            BufferedImage buffImg = ImageIO.read(new File(cardNumToPath(cardToAdd)));
            ImageIcon imgIcon = new ImageIcon(buffImg);
            //Resizes image
            Image image = imgIcon.getImage();
            Image newimg = image.getScaledInstance(100, 140,
                    java.awt.Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(newimg);
            //Adds image button
            CardButton newButton = new CardButton(cardToAdd);
            newButton.setIcon(imgIcon);
            newButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
            newButton.addActionListener(new CardSelectActionListener());
            playerCards.add(newButton);
            hand.add(newButton);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void addComponents()
    {
        if (components == null)
        {
            components = new MyJPanel();
            components.setLayout(null);
            frame.add(components);
        }
        // Adds the opponents' card images to the field
        addImage("images/test.png", new Dimension(100,140), new Point(100, 320)); 
        addImage("images/test.png", new Dimension(100,140), new Point(200, 80)); 
        addImage("images/test.png", new Dimension(100,140), new Point(1080, 320));
        
        //Adds the deck
        addImage("images/deck.png", new Dimension(135,180), new Point(940, 80)); 
        addDeckCounter();
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(840, 137));

        //Adds the card counters
        playerCardCounts = new JLabel[4];
        addCardCounters();

        //Adds the white backgrounds to the card counters
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(240, 360)); 
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(340, 120)); 
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(980, 360));

        

        //Adds the white box of the active player tracker
        createActivePlayerTracker(); 
        addImage("images/WhiteSquare.png", new Dimension(160,60), new Point(1120, 0));

        addPlayCardButton();
        addEndTurnButton();

        components.setVisible(true);
    }

    private void addDeckCounter()
    {
        JLabel count = new JLabel("0");
        count.setSize(100,100);
        count.setLocation(850,118);
        count.setForeground(Color.BLACK);
        count.setFont(new Font("Dialog", Font.PLAIN, 12));
        count.setVisible(true);
        components.add(count);
        deckCounter = count;
    }

    private void addCardCounters()
    {
        if (playerNum == 0)
        {
            playerCardCounts[1] = addCardCounter(new Dimension(100,100), new Point(250, 340));
            playerCardCounts[2] = addCardCounter(new Dimension(100,100), new Point(350, 100));
            playerCardCounts[3] = addCardCounter(new Dimension(100,100), new Point(990, 340));
        }
        else if (playerNum == 1)
        {
            playerCardCounts[2] = addCardCounter(new Dimension(100,100), new Point(250, 340));
            playerCardCounts[3] = addCardCounter(new Dimension(100,100), new Point(350, 100));
            playerCardCounts[0] = addCardCounter(new Dimension(100,100), new Point(990, 340));
        }
        else if (playerNum == 2)
        {
            playerCardCounts[3] = addCardCounter(new Dimension(100,100), new Point(250, 340));
            playerCardCounts[0] = addCardCounter(new Dimension(100,100), new Point(350, 100));
            playerCardCounts[1] = addCardCounter(new Dimension(100,100), new Point(990, 340));
        }
        else if (playerNum == 3)
        {
            playerCardCounts[0] = addCardCounter(new Dimension(100,100), new Point(250, 340));
            playerCardCounts[1] = addCardCounter(new Dimension(100,100), new Point(350, 100));
            playerCardCounts[2] = addCardCounter(new Dimension(100,100), new Point(990, 340));
        }
        updateCardCount(0, 0);
        updateCardCount(1, 0);
        updateCardCount(2, 0);
        updateCardCount(3, 0);
    }

    private JLabel addCardCounter(Dimension size, Point location)
    {
        JLabel count = new JLabel("0");
        count.setSize(size);
        count.setLocation(location);
        count.setForeground(Color.BLACK);
        count.setFont(new Font("Dialog", Font.PLAIN, 12));
        count.setVisible(true);
        components.add(count);
        return count;
    }

    private void addImage(String filePath, Dimension size, Point location)
    {
        try 
        {
            BufferedImage buffImg = ImageIO.read(new File(filePath));
            ImageIcon imgIcon = new ImageIcon(buffImg);
            //Resizes image
            Image image = imgIcon.getImage();
            Image newimg = image.getScaledInstance((int)size.getWidth(), (int)size.getHeight(),
                    java.awt.Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(newimg);
            //Adds image
            JLabel labelImg = new JLabel(imgIcon);
            labelImg.setSize(size);
            labelImg.setLocation(location);
            labelImg.setVisible(true);
            components.add(labelImg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void addBackground()
    {
        try 
        {
            //Gets the image
            BufferedImage buffImg = ImageIO.read(new File("images/GameBackground.jpg"));
            ImageIcon backgroundImgIcon = new ImageIcon(buffImg);
            //Resizes the image
            Image image = backgroundImgIcon.getImage();
            Image newimg = image.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT,
                    java.awt.Image.SCALE_SMOOTH);
            components.setBackgroundImg(newimg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String cardNumToPath(int cardNum)
    {
        String path = "images/Cards/";

        switch (cardNum)
        {
            case 1 << 1:
                path += "ExplodingKitten.jpg";
                break;
            case 1 << 2:
                path += "Attack.jpg";
                break;
            case 1 << 3:
                path += "Skip.jpg";
                break;
            case 1 << 4:
                path += "SeeFuture.jpg";
                break;
            case 1 << 5:
                path += "Shuffle.jpg";
                break;
            case 1 << 6:
                path += "Favor.jpg";
                break;
            case 1 << 7:
                path += "BeardCat.jpg";
                break;
            case 1 << 8:
                path += "WatermelonCat.jpg";
                break;
            case 1 << 9:
                path += "PoatoCat.jpg";
                break;
            case 1 << 10:
                path += "RainbowCat.jpg";
                break;
            case 1 << 11:
                path += "TacoCat.jpg";
                break;
        }

        return path;
    }   
    
    private class CardSelectActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            CardButton card = (CardButton) e.getSource();
            if (selectedCards.contains(card))
            {
                selectedCards.remove(card);
                card.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
            }
            else
            {
                card.setBorder(BorderFactory.createLineBorder(Color.ORANGE,5));
                selectedCards.add(card);
            }

            //playerCards.remove(card);
            //playerCards.revalidate();
            //playerCards.repaint();
        }
    }

    private class PlayCardsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (selectedCards.isEmpty())
            {
                return;
            }
            ArrayList<Integer> played = new ArrayList<Integer>();
            for (CardButton c : selectedCards)
            {
                hand.remove(c);
                playerCards.remove(c);
                played.add(c.getCardNum());
            }
            try{
                Conn.playCards(playerID, gameID, played);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private class EndTurnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                Conn.playCards(playerID, gameID, new ArrayList<Integer>());
            }
            catch (Exception f)
            {
                f.printStackTrace();
            }
        }
    }

    private class CardButton extends JButton
    {
        private int card;
        public CardButton(int cardNum)
        {
            super();
            card = cardNum;
        }

        public int getCardNum()
        {
            return card;
        }
    }

    private class MyJPanel extends JPanel {
        private Image bgImage;
    
    
        public MyJPanel(FlowLayout flowLayout)
        {
            super(flowLayout);
        }
        public MyJPanel()
        {
            super();
        }
    
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, null);
        }
    
        public void setBackgroundImg(Image img)
        {
            bgImage = img;
        }
    }
}
