import java.awt.*;
/**
 * This is the ball that will be used for the Pong game for my final project.
 * Just like the original game, it will be a square ball.
 * 
 * @author (Kramer_Canfield) 
 * @version (22_November_2012)
 * @version (finished 10_December_2012)
 */
public class Ball
{
    // fields
    private int size;//the size of the ball, this will be used for both the width and height
    //that way, if we want to change the size of the ball, all we have to do is change it in one place: in the constructor
    protected int xPos;//the horizontal position of the ball
    protected int yPos;//the vertical position of the ball
    protected int xSpeed;//the speed in the horizontal direction
    protected int ySpeed;//the speed in the vertical direction
    private boolean rightSideOfNet;//whether or not the ball is on the right side of the net

    /**
     * Constructor for objects of class Ball
     * 
     * @param horizontalSpeed The new horizontal speed of the ball..
     * @param verticalSpeed The new vertical speed of the ball.
     */
    public Ball(int horizontalSpeed, int verticalSpeed)
    {
        // initialise instance variables
        size = 10;
        xPos = 245;
        yPos = 245;
        xSpeed = horizontalSpeed;
        ySpeed = -verticalSpeed;//negative vertical speed so that the ball goes up
        rightSideOfNet = true;
    }
    
    
    /**
     * Second Constructor for objects of class Ball
     * 
     * @param horizontalSpeed The new horizontal speed of the ball..
     * @param verticalSpeed The new vertical speed of the ball.
     */
    public Ball()
    {
        // initialise instance variables
        size = 10;
        xPos = 245;
        yPos = 245;
        xSpeed = 4;
        ySpeed = -4;//negative vertical speed so that the ball goes up
        rightSideOfNet = true;
    }

    /**
     * This is the draw method for the ball.
     * Because the ball is actually a square, this version will use a method that draws a rectangle of equal width and height
     * (the size of the ball)
     * 
     * @param canvas The canvas that the ball will be drawn on.
     */
    public void draw(Graphics2D g2d)
    {
        g2d.fillRect(xPos, yPos, size, size);//draws a rectangle of equal height and width..IT'S A SQUARE...
    }


    /**
     * This helper method gets and returns which side of the net the ball is currently on.
     * 
     * @return rightSideOfNet Whether or not the ball is on the right side of the net.
     */
    public boolean getRightSideOfNet()
    {
        if(xPos<=250)//if the ball has a position that is less than 250 horizontally
        {
            return false;//then the ball is on the left
        }
        return true;//otherwise, it is on the right
    }

    /**
     * This helper method gets and returns the horizontal position of the ball.
     * 
     * @return xPos The horizontal position of the ball.
     */
    public int getX()
    {
        return xPos;
    }

    /**
     * This helper method gets and returns the vertical position of the ball.
     * 
     * @return xPos The vertical position of the ball.
     */
    public int getY()
    {
        return yPos;
    }
    
    /**
     * This helper method sets the horizontal position of the ball.
     * 
     * @param newX The new horizontal position of the ball.
     */
    public void setX(int newX)
    {
        xPos = newX;
    }

    /**
     * This helper method sets the vertical position of the ball.
     * 
     * @param newY The new vertical position of the ball.
     */
    public void setY(int newY)
    {
        yPos = newY;
    }
    
    /**
     * This helper method gets and returns the horizontal speed of the ball.
     * 
     * @return xSpeed The horizontal speed of the ball.
     */
    public int getXSpeed()
    {
        return xSpeed;
    }

    /**
     * This helper method gets and returns the vertical speed of the ball.
     * 
     * @return ySpeed The vertical speed of the ball.
     */
    public int getYSpeed()
    {
        return ySpeed;
    }

    /**
     * This helper method sets the new horizontal speed of the ball.
     * 
     * @param newXSpeed The integer value of the new horizontal speed.
     * @return xSpeed The horizontal speed position of the ball.
     */
    public void setXSpeed(int newXSpeed)
    {
        xSpeed = newXSpeed;//assigns the new speed
    }

    /**
     * This helper method sets the new vertical speed of the ball.
     * 
     * @param newYSpeed The integer value of the new vertical speed.
     */
    public void setYSpeed(int newYSpeed)
    {
        ySpeed = newYSpeed;//assigns the new speed
    }

    /**
     * This method will get and return the size of the ball.
     * @return size The size of the ball.
     */
    public int getSize()
    {
        return size;
    }
}
