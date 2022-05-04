import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class GameWindow extends JFrame
{
    JFrame frame;
    JLabel displayField;
    ImageIcon image;

    public GameWindow()
    {
        frame = new JFrame("Exploding Kittens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try 
        {
            image = new ImageIcon(getClass().getResource("images/GameBackground.jpg"));
            displayField = new JLabel(image);
            frame.add(displayField);
        } catch(Exception e) {
            e.printStackTrace();
        }
        frame.setSize(800,600);
        frame.setVisible(true);
    }
    
    public static void main(String [] args) {
        new GameWindow();
    }
   
    /*public GameWindow()
    {
        super("Exploding Kittens");
        JPanel panel = new JPanel();
        GridBagLayout gbLayout = new GridBagLayout();
        panel.setLayout(gbLayout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setBounds(200, 200, 500, 300);
        
        try 
        {
            JPanel panel1 = new JPanel();
            setBounds(200, 200, 500, 300);
            BufferedImage img = ImageIO.read(new File("images/GameBackground.png"));
            JLabel pic = new JLabel(new ImageIcon(img));
            panel1.add(pic);
            add(panel1);
            setSize(400, 400);
            setLayout(null);
        } 
        catch (IOException e) {

        }

        setVisible(true);
    }*/
}
