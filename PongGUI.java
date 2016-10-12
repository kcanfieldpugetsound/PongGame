import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class PongGUI here.
 * 
 * @author (Kramer Canfield) 
 * @version (3 December 2012)
 * @version (finished 10_December_2012)
 */
public class PongGUI implements ActionListener
{
    // fields
    private JButton startButton;//the button to start and resume the game
    private JButton pauseButton;//the button to pause the game
    private JButton resetButton;//the button to reset the game and scores back to the current settings and scores of zero
    private JButton settingsButton;//the button to open the settings windoe
    private PongGame game;//the pong game that will be played inside the GUI
    private JPanel gamePanel;//the panel that the game will be played within

    Toolkit toolkit;//the tookit that we will use to get the screen size

    public Settings settings;//the settings object that we can use to modify the game

    /**
     * Constructor for objects of class PongGUI
     * 
     * @param game The pong game that will be used by the GUI
     * @param settings The settings panel that will go with the GUI.
     */
    public PongGUI(PongGame game, Settings settings)
    {
        // initialise instance variables

        //initialize the game and settings objects
        this.game = game;
        this.settings = settings;

        //make the settings visible
        settings.setVisible(true);

        //make a new JFrame with a title of Pong
        JFrame frame = new JFrame();
        frame.setTitle("Pong");

        //closing the window will quit the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);//you cannot resize the window

        frame.getContentPane().setLayout(new BorderLayout()); //get contentPane ready

        //initialize the start button with a preferred size and text on the button that says "Start Game"
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);//add the action listener so the button can be clicked
        startButton.setPreferredSize(new Dimension(60,30));

        //initialize the pause button
        pauseButton = new JButton("Pause Game");
        pauseButton.addActionListener(this);//add the action listener so the button can be clicked

        //initialize the reset button
        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(this);//add the action listener so the button can be clicked

        //initialize the settings button
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);//add the action listener so the button can be clicked

        //make a new button panel with a grid layout with one row and four columns
        JPanel buttonPanel = new JPanel();//make a new JPanel to put the buttons in
        buttonPanel.setLayout(new GridLayout(1,4));
        //add all four buttons to the panel
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(settingsButton);

        //initialize gamePanel to be the canvas from the game
        gamePanel = game.getCanvas();
        gamePanel.setPreferredSize(new Dimension(500,470));//set a size for the game panel
        gamePanel.setFocusable(true);

        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);//add the game to the center
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);//add the button panel to the bottom of the panel

        toolkit = Toolkit.getDefaultToolkit();
        //a static method to get the toolkit which will be used momentarily to help place the window in the center of the screen

        frame.pack();
        frame.setLocation((((int)toolkit.getScreenSize().getWidth())/2)-250,(((int)toolkit.getScreenSize().getHeight())/2)-250);//set the location of the window to be in the center of the screen 
        frame.setVisible(true);
    }

    /**
     * This method enables all of the buttons.
     */
    public void enableButtons()
    {
        startButton.setEnabled(true);
        pauseButton.setEnabled(true);
        resetButton.setEnabled(true);
        settingsButton.setEnabled(true);
    }

    //*******************************************
    // ActionListener method
    //*******************************************
    /**
     * This is the method from the ActionListener interface.
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == startButton)
        {
            game.clockStart();//start the game clock
            game.setGameInProgress(true);//set the game to be in progress

            //un-enable all buttons except for the pause button
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resetButton.setEnabled(false);
            settingsButton.setEnabled(false);

            //request focus for the game panel
            gamePanel.requestFocus();

        }
        if(e.getSource() == pauseButton)
        {
            //when the game is paused, we want to enable the start, reset, and settings buttons, but not the pause button itself
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            resetButton.setEnabled(true);
            settingsButton.setEnabled(true);
            //also, stop the game's clock, tell the game it is not in progress, and request focus back to the game panel
            game.clockStop();
            game.setGameInProgress(false);
            gamePanel.requestFocus();
        }
        if(e.getSource() == resetButton)
        {
            game.clockStop();//stop the game clock
            game.resetScores();
            game.resetCourt();
            //the scores are reset when the user clicks reset, but the numbers displayed do not change until a new game is started
            gamePanel.requestFocus();
        }
        if(e.getSource() == settingsButton)
        {
            settings.setVisible(true);//make settings visible
            settings.setCallingGUI(this);//call our special method on settings 
            settings.repaint();//refresh settings

            //do not enable any buttons so the "save" button in settings must be pressed before playing again
            startButton.setEnabled(false);
            pauseButton.setEnabled(false);
            resetButton.setEnabled(false);
            settingsButton.setEnabled(false);
        }
    }
}