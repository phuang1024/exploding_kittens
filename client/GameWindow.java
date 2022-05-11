import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class GameWindow
{
    JFrame frame;
    int initialHeight = 720;
    int initialWidth = 1280;

    JLabel [] playerCardCounts;
    int playerNum;

    public GameWindow(int playerNum)
    {
        //Creates a new JFrame for the UI to be on
        frame = new JFrame("Exploding Kittens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(initialWidth, initialHeight);
        frame.setResizable(false);

        //Creates the background image
        try 
        {
            //Gets the image
            BufferedImage buffImg = ImageIO.read(new File("images/GameBackground.jpg"));
            ImageIcon backgroundImgIcon = new ImageIcon(buffImg);
            //Resizes the image
            Image image = backgroundImgIcon.getImage();
            Image newimg = image.getScaledInstance(initialWidth, initialHeight,
                    java.awt.Image.SCALE_SMOOTH);
            backgroundImgIcon = new ImageIcon(newimg);
            //Adds the image
            JLabel labelImg = new JLabel(backgroundImgIcon);
            frame.setContentPane(labelImg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Creates a sample image one top
        //addImage("images/index.jpg", new Dimension(200,100), new Point(100, 200));

        // Adds the opponents' card images to the field
        addImage("images/CardBack.jpg", new Dimension(100,140), new Point(100, 320)); 
        addImage("images/CardBack.jpg", new Dimension(100,140), new Point(200, 80)); 
        addImage("images/CardBack.jpg", new Dimension(100,140), new Point(1080, 320)); 

        //Adds the card counters
        addCardCounter(new Dimension(100,100), new Point(260, 340));
        addCardCounter(new Dimension(100,100), new Point(360, 100));
        addCardCounter(new Dimension(100,100), new Point(1000, 340));

        //Adds the white backgrounds to the card counters
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(240, 360)); 
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(340, 120)); 
        addImage("images/WhiteSquare.png", new Dimension(60,60), new Point(980, 360)); 

        playerCardCounts = new JLabel[4];


        frame.setVisible(true);
        frame.setEnabled(true);
    }

    public void updateCardCount(int playerToUpdate, int newNum)
    {
        playerCardCounts[playerToUpdate - 1].setText("" + newNum);
    }

    private JLabel addCardCounter(Dimension size, Point location)
    {
        JLabel count = new JLabel("0");
        count.setSize(size);
        count.setLocation(location);
        count.setForeground(Color.BLACK);
        count.setFont(new Font("Dialog", Font.PLAIN, 30));
        count.setVisible(true);
        frame.add(count);
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
            frame.add(labelImg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args) {
        new GameWindow(1);
    }
}
