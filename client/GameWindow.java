import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameWindow extends JFrame
{
    public GameWindow()
    {
        super("Exploding Kittens");
        JPanel panel = new JPanel();
        GridBagLayout gbLayout = new GridBagLayout();
        panel.setLayout(gbLayout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setBounds(200, 200, 500, 280);
        setVisible(true);
    }

    public static void main(String [] args) {
        new GameWindow();
    }
}
