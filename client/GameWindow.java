import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;


public class GameWindow
{
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private JFrame frame;

    //The componenets of the frame
    private MyJPanel opponenets;
    private MyJPanel playerCards;

    private int currentDiscCard;


    JLabel [] playerCardCounts;
    int playerNum;


    public GameWindow(String playerID, String gameID)
    {
        //Creates a new JFrame for the UI to be on
        frame = new JFrame("Exploding Kittens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);

        addOpponentInfo();
        addBackground();

        
        addCard();
        addCard();

        frame.setVisible(true);
        frame.setEnabled(true);
    }

    private void updateScreen(int [] playerCardCounts, int centerCard, int [] playerHand)
    {
        //Updates the opponenets' card counts
        for (int i = 0; i < 4; i++)
        {
            if (i != playerNum)
                updateCardCount(i, playerCardCounts[i]);
        }

        if (centerCard != currentDiscCard)
        {
            String middleCard = cardNumToPath(centerCard);
            addImage(middleCard, new Dimension(200,282), new Point(540, 219));
        }

        boolean handIsSame = true;
        for (int i = 0; i < playerHand.length; i++)
        {
            
        }

    }

    public void updateCardCount(int playerToUpdate, int newNum)
    {
        playerCardCounts[playerToUpdate - 1].setText("" + newNum);
    }

    private void addCard()
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
            BufferedImage buffImg = ImageIO.read(new File("images/CreditCard.jpg"));
            ImageIcon imgIcon = new ImageIcon(buffImg);
            //Resizes image
            Image image = imgIcon.getImage();
            Image newimg = image.getScaledInstance(100, 100,
                    java.awt.Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(newimg);
            //Adds image button
            JButton newButton = new JButton();
            newButton.setIcon(imgIcon);
            newButton.addActionListener(new CardActionListener());
            playerCards.add(newButton);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void addOpponentInfo()
    {
        if (opponenets == null)
        {
            opponenets = new MyJPanel();
            opponenets.setLayout(null);
            frame.add(opponenets);
        }
        // Adds the opponents' card images to the field
        addImage("images/test.png", new Dimension(100,140), new Point(100, 320)); 
        addImage("images/test.png", new Dimension(100,140), new Point(200, 80)); 
        addImage("images/test.png", new Dimension(100,140), new Point(1080, 320));
        
        //Adds the deck
        addImage("images/test.png", new Dimension(140,140), new Point(940, 80)); 

        //Adds the card counters
        addCardCounter(new Dimension(100,100), new Point(260, 340));
        addCardCounter(new Dimension(100,100), new Point(360, 100));
        addCardCounter(new Dimension(100,100), new Point(1000, 340));

        //Adds the white backgrounds to the card counters
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(240, 360)); 
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(340, 120)); 
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(980, 360));

        opponenets.setVisible(true);
    }

    private JLabel addCardCounter(Dimension size, Point location)
    {
        JLabel count = new JLabel("0");
        count.setSize(size);
        count.setLocation(location);
        count.setForeground(Color.BLACK);
        count.setFont(new Font("Dialog", Font.PLAIN, 30));
        count.setVisible(true);
        opponenets.add(count);
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
            opponenets.add(labelImg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addBackground()
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
            opponenets.setBackgroundImg(newimg);
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
    
    private class CardActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JButton card = (JButton) e.getSource();
            playerCards.remove(card);
            playerCards.revalidate();
            playerCards.repaint();
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
    

    public static void main(String [] args) {
        new GameWindow("2","2");
    }
}
