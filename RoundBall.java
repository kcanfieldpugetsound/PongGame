import java.awt.*;
/**
 * Write a description of class RoundBall here.
 * 
 * @author (Kramer Canfield) 
 * @version (15 December 2012)
 */
public class RoundBall extends Ball
{
    // fields
    private int diameter;//the diameter of the ball

    /**
     * Constructor for objects of class RoundBall
     * 
     * @param horizontalSpeed The new horizontal speed of the ball..
     * @param verticalSpeed The new vertical speed of the ball.
     */
    public RoundBall(int horizontalSpeed, int verticalSpeed)
    {
        // initialise instance variables
        diameter = 10;
    }
    
    /**
     * This is the draw method for the round ball.
     * 
     * @param g2d A Graphics2D object to draw on.
     */
    public void draw(Graphics2D g2d)
    {
        g2d.fillOval(xPos, yPos, 10, 10);
    }
}
