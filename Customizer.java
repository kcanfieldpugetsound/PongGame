import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 * Write a description of class Customizer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customizer extends JFrame implements ActionListener
{
    // fields
    private JSlider paddleSlider;//a JSlider for the size of the paddle
    private JButton save;
    private Settings settings;

    private JSlider ballSpeedSlider;// a slider to control the initial speed of the ball

    /**
     * Constructor for objects of class Customizer
     */
    public Customizer()
    {
        // initialise instance variables

        //makes a new JFrame with a title settings
        JFrame frame = this;
        frame.setTitle("Customize");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//does nothing on close so the user must click the save button
        frame.setAlwaysOnTop(true);
        //frame.setPreferredSize(new Dimension(300,230));
        frame.setResizable(false);

        frame.getContentPane().setLayout(new BorderLayout()); //get contentPane ready

        JPanel settingsPanel = new JPanel();//make a new JPanel to put all of the buttons in except save
        JPanel savePanel = new JPanel();//make a JPanel to put the save button into

        save = new JButton("Save");
        save.addActionListener(this);

        JLabel paddleLabel = new JLabel("Set a custom paddle length:");

        paddleSlider = new JSlider(10, 60, 30);//min is 10, max is 60, initial value is 30
        paddleSlider.setPaintTicks(true);
        paddleSlider.setMajorTickSpacing(5);
        paddleSlider.setSnapToTicks(true);
        paddleSlider.setPaintLabels(true);

        //THE BALL SPEED ADJUSTER
        JLabel ballLabel = new JLabel("Set a custom initial speed for the ball:");
        ballSpeedSlider = new JSlider(2, 20, 4);
        ballSpeedSlider.setPaintTicks(true);
        ballSpeedSlider.setMajorTickSpacing(2);
        ballSpeedSlider.setSnapToTicks(true);
        ballSpeedSlider.setPaintLabels(true);

        settingsPanel.add(paddleLabel);
        settingsPanel.add(paddleSlider);
        settingsPanel.add(ballLabel);
        settingsPanel.add(ballSpeedSlider);

        savePanel.add(save);//add the save button to the panel so it exists there

        frame.getContentPane().add(settingsPanel, BorderLayout.CENTER);
        frame.getContentPane().add(savePanel, BorderLayout.SOUTH);

        frame.pack();
    }

    /**
     * This is the method from the ActionListener interface.
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == save)
        {
            //save all of the chosen options
            //save the length of the paddles   

            if(settings != null)
            {
                settings.saveCustom(paddleSlider.getValue(), ballSpeedSlider.getValue());
            }
            
            this.setVisible(false);
        } 
    }

    /**
     * This method sets which Settings object is the parent window.
     */
    public void setCallingSettings(Settings s)
    {
        this.settings = s;
    }
}