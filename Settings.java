import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class PongGUI here.
 * 
 * @author (Kramer Canfield) 
 * @version (4 December 2012)
 */
public class Settings extends JFrame implements ActionListener, ItemListener
{
    // fields

    Toolkit toolkit;//the toolkit that I will use to make the window appear where I want it to appear

    private JRadioButton numPlayers1RadioButton;//the button to choose that the number of players is one
    private JButton player1ColorButton;//the button to chose the color for player 1
    private JColorChooser colorChooser1;//the color chooser itself for player 1
    private JLabel P1ColorChooseLabel;// a label to describe the button
    private Color currentP1Color;//the color for P1

    private JRadioButton numPlayers2RadioButton;//the button to choose that the number of players is two
    private JButton player2ColorButton;//the button to chose the color for player 2
    private JColorChooser colorChooser2;//the color chooser itself for player 2
    private JLabel P2ColorChooseLabel;// a label to describe the button
    private Color currentP2Color;//the color for P2

    private int currentDifficulty;//the difficulty of the game
    private JRadioButton diff1;//a button to select the difficulty as easy
    private JRadioButton diff2;//a button to select the difficulty as medium
    private JRadioButton diff3;//a button to select the difficulty as hard
    private JRadioButton diffCustom;// a button to represent a custom difficulty selection

    private JButton save;//a JButton that will be used to save all of the selected options

    private Checkbox playSoundCheckBox;//the checkbox to let the user decide if they want to hear the sound in the game or not
    private boolean playSoundsGood;// true if the user wants to play sounds

    private PongGUI gui;//the gui that is the parent window

    private PongGame game;//the game that will be modified by changing the settings

    private Customizer customizer;

    

    /**
     * Constructor for objects of class PongGUI
     * @param game The Pong Game.
     */
    public Settings(PongGame game, Customizer customizer)
    {
        // initialise instance variables

        this.game = game;//initialise the game 

        //makes a new JFrame with a title settings
        JFrame frame = this;
        frame.setTitle("Settings");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//does nothing on close so the user must click teh save button
        frame.setAlwaysOnTop(true);
        frame.setPreferredSize(new Dimension(310,240));
        frame.setResizable(false);

        frame.getContentPane().setLayout(new BorderLayout()); //get contentPane ready

        //one or two player options

        //make a JRadioButton to select the one-player option
        numPlayers1RadioButton = new JRadioButton("One Player");
        numPlayers1RadioButton.addActionListener(this);

        //make a button, label and color chooser so the player can choose the color of his/her paddle
        player1ColorButton = new JButton("P1 Color");
        player1ColorButton.addActionListener(this);
        P1ColorChooseLabel = new JLabel("Choose the color for Player 1.");
        colorChooser1 = new JColorChooser(Color.WHITE);

        //make a JRadioButton to select the two-player option
        numPlayers2RadioButton = new JRadioButton("Two Players");
        numPlayers2RadioButton.addActionListener(this);

        //make a button, label and color chooser so the player can choose the color of his/her paddle
        player2ColorButton = new JButton("P2 Color");
        player2ColorButton.addActionListener(this);
        P2ColorChooseLabel = new JLabel("Choose the color for Player 2.");
        colorChooser2 = new JColorChooser(Color.WHITE);

        //the difficulty buttons
        diff1 = new JRadioButton("Easy");
        diff1.addActionListener(this);
        diff2 = new JRadioButton("Medium");
        diff2.addActionListener(this);
        diff3 = new JRadioButton("Hard");
        diff3.addActionListener(this);
        diffCustom = new JRadioButton("Custom");
        diffCustom.addActionListener(this);

        this.customizer = customizer;

        //the checkbox to play sounds
        playSoundCheckBox = new Checkbox("play sounds", true);
        playSoundCheckBox.addItemListener(this);

        //the save button
        save = new JButton("Save");
        save.addActionListener(this);

        //make a button group for the option of number of players because there can only be either one or two players at a time
        //this is an either-or choice
        ButtonGroup numPlayersGroup = new ButtonGroup();
        numPlayersGroup.add(numPlayers1RadioButton);
        numPlayersGroup.add(numPlayers2RadioButton);

        //make a button group for the difficulty option because you can only have one difficulty option selected at a time.
        ButtonGroup diffGroup = new ButtonGroup();
        diffGroup.add(diff1);
        diffGroup.add(diff2);
        diffGroup.add(diff3);
        diffGroup.add(diffCustom);

        JPanel settingsPanel = new JPanel();//make a new JPanel to put all of the buttons in except save
        JPanel savePanel = new JPanel();//make a JPanel to put the save button into

        settingsPanel.add(numPlayers1RadioButton);//adds the one player button to the settings panel
        settingsPanel.add(numPlayers2RadioButton);//adds the two player button to the settings panel
        settingsPanel.add(player1ColorButton);//adds the button and label to the panel for choosing a color for player 1
        settingsPanel.add(P1ColorChooseLabel);
        settingsPanel.add(player2ColorButton);//adds the button and label to the panel for choosing a color for player 2
        settingsPanel.add(P2ColorChooseLabel);

        //add the difficulty option buttons to the panel
        settingsPanel.add(diff1);
        settingsPanel.add(diff2);
        settingsPanel.add(diff3);
        settingsPanel.add(diffCustom);

        settingsPanel.add(playSoundCheckBox);//adds the checkbox to play sounds

        savePanel.add(save);//add the save button to the panel so it exists there

        frame.getContentPane().add(settingsPanel, BorderLayout.CENTER);
        frame.getContentPane().add(savePanel, BorderLayout.SOUTH);

        //set default settings in settings and in game
        //the default options are to have both paddles be the color white, difficulty set to easy, and the number of players is automatically two
        currentP1Color = Color.WHITE;
        game.setP1C(Color.WHITE);
        currentP2Color = Color.WHITE;
        game.setP2C(Color.WHITE);
        numPlayers1RadioButton.setSelected(true);
        game.setNumberOfPlayers(1);
        currentDifficulty = 1;
        game.setDifficulty(1);
        diff1.setSelected(true);
        playSoundsGood = true;
        game.setPlaySounds(ItemEvent.SELECTED);
        playSoundCheckBox.setState(true);
        //end of setting default settings in settings window and in game

        toolkit = Toolkit.getDefaultToolkit();//use the toolkit to get the screen size

        frame.pack();
        frame.setLocation((((int)toolkit.getScreenSize().getWidth())/2)+300,((((int)toolkit.getScreenSize().getHeight())/2)+50));//center the window using the toolkit
        frame.setVisible(true);

    }


    /**
     * This gets and returns player 1's color
     * @return P1Color
     */
    public Color getP1Color()
    {
        return currentP1Color;
    }

    /**
     * This gets and returns player 2's color
     * @return P2Color
     */
    public Color getP2Color()
    {
        return currentP2Color;
    }

    /**
     * This gets and returns the difficulty of the game.
     * @return currentDifficulty The difficulty of the game.
     */
    public int getCurrentDifficulty()
    {
        if(diff1.isSelected()==true)
        {
            return 1;
        }
        if(diff2.isSelected()==true)
        {
            return 2;
        }
        if(diff3.isSelected()==true)
        {
            return 3;
        }
        else
        {
            return 4;
        }
    }

    /**
     * This method returns the number of players by seeing if the one-player button is selected.
     * @return num The number of players that will be playing the game.
     */
    public int getNumPlayers()
    {
        if(numPlayers1RadioButton.isSelected()==true)
        {
            return 1;
        }
        return 2;
    }

    /**
     * This method gets and returns whether or not the settings window is visible.
     * @return visible True if the Settings panel is showing.
     */
    public boolean getSettingsVisible()
    {
        if(this.isVisible()==true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This method will get and return the player 1 color button. This is called in HangmanGUI.
     * 
     * @return player1ColorButton The button to bring up the color chooser for player 1.
     */
    public JButton getP1CButton()
    {
        return player1ColorButton;
    }

    /**
     * This method will get and return the player 2 color button. This is called in HangmanGUI.
     * 
     * @return player2ColorButton The button to bring up the color chooser for player 2.
     */
    public JButton getP2CButton()
    {
        return player2ColorButton;
    }

    /**
     * This method will help with saving the custom settings.
     * @param plength The custom length of the paddle.
     * @param ballSpeed The custom initial speed of the ball.
     */
    public void saveCustom(int plength, int ballSpeed)
    {
        game.setAllPaddles(plength);
        game.setBall(ballSpeed);
    }

    //*******************************************
    // ActionListener method
    //*******************************************
    /**
     * This is the method from the ActionListener interface.
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == player1ColorButton)
        {
            //show the JColorChooser and save the chosen color
            currentP1Color = JColorChooser.showDialog(null, "Player 1 Color Chooser", getP1Color());
            game.setP1C(currentP1Color);//assign the color in the game
        }
        if(e.getSource() == player2ColorButton)
        {
            //show the JColorChooser and save the chosen color
            currentP2Color = JColorChooser.showDialog(null, "Player 2 Color Chooser", getP2Color());
            game.setP2C(currentP2Color);//assign the color in the game
        }
        if(e.getSource() == diffCustom)
        {
            customizer.setVisible(true);//make the customizer visible
            customizer.setCallingSettings(this);//call our special method on the cstomizer 
            customizer.repaint();//refresh settings
        }
        if(e.getSource() == save)
        {
            //save all of the chosen options
            if(game != null)
            {
                game.setP1C(getP1Color());
                game.setP2C(getP2Color());
                game.setDifficulty(getCurrentDifficulty());
                game.setNumberOfPlayers(getNumPlayers());

                game.clockStop();
                game.resetScores();
                game.resetCourt();
            }

            setVisible(false);

            //the scores are reset when the user clicks reset, but the numbers displayed do not change until a new game is started

            if(gui != null)
            {
                gui.enableButtons();//enable all of the buttons

            }
        }
    }

    /**
     * This method sets which PongGUI object is the parent window.
     */
    public void setCallingGUI(PongGUI gui)
    {
        this.gui = gui;
    }

    //*******************************************
    // ItemListener method
    //*******************************************
    /**
     * Invoked when an item has been selected or deselected by the user.
     */
    public void itemStateChanged(ItemEvent e)
    {
        //these are ti assign the game playing sounds or not playing sounds based on the checkbox being selected or not
        if(e.getStateChange()==ItemEvent.SELECTED)
        {
            game.setPlaySounds(ItemEvent.SELECTED);
        }
        if(e.getStateChange()==ItemEvent.DESELECTED)
        {
            game.setPlaySounds(ItemEvent.DESELECTED);
        }

    }

}
