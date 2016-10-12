import java.awt.*;
import javax.swing.*;
/**
 * Write a description of class PongGUITester here.
 * 
 * @author (Kramer Canfield) 
 * @version (4 December 2012)
 * @version (finished 10_December_2012)
 */
public class PongGUITester
{
    // NO FIELDS
    /**
     * this is the main method
     */
    public static void main(String[] args)
    {
        PongGame game = new PongGame();//constructor takes no parameters
        Customizer c = new Customizer();
        Settings settings = new Settings(game, c);//makes a settings object from the game
        PongGUI pongGUI = new PongGUI(game, settings);//makes a new GUI based on the game and settings
        
        /**
         * the following code does a bunch of GUI stuff to make a new window that has a welcome message
         * I realize I could make this its own class, but I chose not to because then there would be one more class.
         */
        JFrame frame = new JFrame("WELCOME TO PONG reimagined by Kramer Canfield!");
        frame.setPreferredSize(new Dimension(550,300));
        frame.setResizable(false);//make the window not resizable
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//hide the window
        frame.getContentPane().setLayout(new BorderLayout()); //get contentPane ready
                
        //this is a very long, broken up string with the welcome message in multiple parts
        String hello1 = "\tIn this brand new remake of Atari's hit classic game PONG,";
        String hello2 = "\tyou can not only play the original game, but also enjoy the following new features:";
        String hello3 = "\t\t• Play with a friend OR against the new AI";
        String hello4 = "\t\t• Choose paddle colors";
        String hello5 = "\t\t• Change the difficulty level (customize the paddle length and ball speed)\n\n";
        String hello6 = "\tHow to Play:";
        String hello7 = "\t\tPlayer1: (left paddle) keys W and S for up and down";
        String hello8 = "\t\tPlayer2: (right paddle) arrow keys up and down";
        String s = "\tSo dive in and have fun...";//the last part of the message
        //make JLabels out of the Strings
        JLabel message1 = new JLabel(hello1);
        JLabel message2 = new JLabel(hello2);
        JLabel message3 = new JLabel(hello3);
        JLabel message4 = new JLabel(hello4);
        JLabel message5 = new JLabel(hello5);
        JLabel message6 = new JLabel(hello6);
        JLabel message7 = new JLabel(hello7);
        JLabel message8 = new JLabel(hello8);
        //make a new panel for the message
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(8,1));
        messagePanel.setPreferredSize(new Dimension(550,250));//with a preferred dimension
        //add the strings to the message
        
        messagePanel.add(message1);
        messagePanel.add(message2);
        messagePanel.add(message3);
        messagePanel.add(message4);
        messagePanel.add(message5);
        messagePanel.add(message6);
        messagePanel.add(message7);
        messagePanel.add(message8);
        //add the last message to the screen
        JPanel sPanel = new JPanel();
        sPanel.add(new JLabel(s));
        

        //add the panels to the content pane
        frame.getContentPane().add(messagePanel, BorderLayout.CENTER);//center the message panel to the middle of the frame
        frame.getContentPane().add(sPanel, BorderLayout.SOUTH);//center the other panel to the bottom of the frame
        
        frame.pack();//we always call this method
        Toolkit toolkit = Toolkit.getDefaultToolkit();//make a new toolkit so we can get the screen size in just a moment
        
        frame.setLocation(((((int)toolkit.getScreenSize().getWidth())/2)-275),(((int)toolkit.getScreenSize().getHeight())/2)-200);
        //put the center the window at the center of the screen
        
        frame.setVisible(true);//make the window visible
    }
}
