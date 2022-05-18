import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import javax.imageio.ImageIO;

public class QueueWindow
{
    private JFrame frame;
    private JPanel panel;

    public QueueWindow()
    {
        //Set up frame
        frame = new JFrame("Launcher");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 600, 400);
        frame.setResizable(false);


        //set up panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        frame.add(panel);

        //add image
        try{
            BufferedImage buffImg = ImageIO.read(new File("images/logo.jpg"));
            ImageIcon imgIcon = new ImageIcon(buffImg);
            //Resizes image
            Image image = imgIcon.getImage();
            Image newimg = image.getScaledInstance(210,180, java.awt.Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(newimg);
            //Adds image
            JLabel labelImg = new JLabel(imgIcon);
            labelImg.setVisible(true);
            labelImg.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(labelImg);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        //add button
        JButton qButton = new JButton("Queue for a new game");
        qButton.setPreferredSize(new Dimension(200,100));
        qButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        qButton.addActionListener(new QueueActionListener());
        panel.add(qButton);


        frame.setVisible(true);
    }

    private class QueueActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            JTextArea label = new JTextArea("Queueing...");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setFont(new Font("Dialog", Font.PLAIN, 30));
            panel.add(label);

            try{
                String id = Conn.getId();
                String gameID = Conn.joinGame(id);
                new GameWindow(id, gameID);
                frame.dispose();
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }

        }
    }

    public static void main(String [] args) {
        new QueueWindow();
    }
}
