import javax.swing.*;
import java.awt.*;
/**
 * A custom-made "canvas" for drawing something.
 * In this case it will be used for drawing the pong game itself, the black background, and the dashed line that is the net in the middle.
 * 
 * Source code provided by Joel Ross. Modified by Kramer Canfield.
 * 
 * @author (Kramer Canfield) 
 * @version (started 11/26/2012)
 * @version (finished 10_December_2012)
 */
//we extend JPanel so that we can add this canvas to JFrames and such
//we are a type of JPanel, so that when normal GUI stuff expects a JPanel
//we can work as well
public class MyCanvas extends JPanel
{
    //can add any special fields or constructors if we want to track things (e.g., location of a ball in pong, etc).
    
    public PongGame game;//the game that will be drawn
    
    /**
     * Constructor for the canvas.
     * 
     * @param game The game to use inside the canvas so we can call its draw method.
     */
    public MyCanvas(PongGame game)
    {
        this.game = game;//initializes the game
    }
    
    /**
     * Overrides normal paintComponent method with our own custom painting
     * @param g a "graphics context" that the JPanel's contents are being drawn on. See 
     *          http://docs.oracle.com/javase/6/docs/api/java/awt/Graphics.html
     */
    //This is the method that gets called whenever the JPanel is drawn on the screen--so when it is made
    //visible, when we call "repaint()", etc
    //Basically we customize what we want to be shown/drawn on this JPanel
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //make sure to call parent's (JPanel's) version of paintComponent, 
                                 //in case there are any other pieces of the panel that are needed
        Graphics2D g2d = (Graphics2D)g; //cast our graphics context into a Graphics2D object.
                                        //this gives us more fun methods. See:
                                        //http://docs.oracle.com/javase/6/docs/api/java/awt/Graphics2D.html


        //these are all drawing methods from the Graphics and Graphics2D classes
        g2d.setPaint(Color.BLACK);
        //g2d.fillRect(0,0,500,470); //draw a rectangle to act as the main background screen for the game
        g2d.fillRect(0,0,500,500);
        
        //the array for the dashes
        float[] dashes = {20,20,20,20};
        
        //set the paint color to white so we can draw the net in the color white
        g2d.setPaint(Color.WHITE);
        //changes the stroke to make a dashed stroke to draw the net later
        g2d.setStroke(new BasicStroke((float)2.5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, (float)4.0, dashes,(float)0.0));
        
        g2d.drawLine(250, 0, 250, 500);//draws the net on the screen
        
        //draw the game itself
        game.drawGame(g2d);
        
    } 
    
    //:)
    // it's a smiley face that Joel drew in his comments.
    //I like smiley faces, so I left it there...
    
}
