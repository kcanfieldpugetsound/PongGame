import java.awt.*;
/**
 * This is the paddle that the Pong game will use. They can be controlled by the player(s) and
 * are drawn on the screen as rectangles...
 * 
 * @author (Kramer Canfield) 
 * @version (26_November_2012)
 * @version (finished 10_December_2012)
 */
public class Paddle 
{
    // fields
    public static final int PADDLE_WIDTH = 10;//the horizontal thickness of the paddle--this will be much smaller than the length of the paddle
    private int paddleLength;//the vertical dimension of the paddle
    private Color paddleColor;//the color of the paddle
    //the length and width and color will be important for drawing the paddle on the screen

    protected int xPos;//the horizontal position of the paddle--even though this is always the same for each paddle
    protected int yPos;//the vertical position of the paddle--this will change a lot during the game
    protected int speed;//the speed of the paddle (in the vertical direction)

    /**
     * Constructor for objects of class Paddle
     * 
     * @param newColor The given color to draw the paddle with.
     * @param length The length of the paddle to be determined by the difficulty of the game.
     */
    public Paddle(Color newColor, int length, int x, int y)
    {
        // initialise instance variables
        //assign parameters to the fields
        paddleLength = length;
        paddleColor = newColor;
        xPos = x;
        yPos = y;
        speed = 10;//the speed will be ten
    }

    /**
     * Second constructor for objects of class Paddle
     * 
     */
    public Paddle()
    {
    
    }

    /**
     * This is the draw method for the paddle.
     * Because the paddle is actually a square, this version will use a method that draws a rectangle of equal width and height
     * (the size of the paddle)
     * 
     * @param canvas The canvas that the paddle will be drawn on.
     */
    public void draw(Graphics2D g2d)
    {
        //draws a rectangle to be the paddle
        g2d.fillRect(xPos, yPos, PADDLE_WIDTH, paddleLength);
    }

    /**
     * A helper method that returns the vertical position of the paddle.
     * @return The vertical position of the paddle.
     */
    public int getY()
    {
        return yPos;
    }

    /**
     * A helper method that gets and returns the horizontal position of the paddle.
     * 
     * @return The horizontal position of the paddle.
     */
    public int getX()
    {
        return xPos;
    }

    /**
     * A helper method that gets and returns the length of the paddle.
     * 
     * @return paddleLength The vertical length of the paddle.
     */
    public int getPaddleLength()
    {
        return paddleLength;
    }

    /**
     * This helper method will set the paddle's length.
     * @param l The new length of the paddle.
     */
    public void setPaddleLength(int l)
    {
        paddleLength = l;
    }

    /**
     * A helper method that gets and returns the speed of the paddle.
     * 
     * @return speed The vertical speed of the paddle.
     */
    public int getPaddleSpeed()
    {
        return speed;
    }

    /**
     * A helper method that sets the paddle's position.
     * 
     * @param newPosition The new vertical position of the paddle.
     */
    public void setY(int newPosition)
    {
        yPos = newPosition;
    }

    /**
     * A helper method that gets and returns the color of the paddle.
     * 
     * @return paddleColor The color of the paddle.
     */
    public Color getPaddleColor()
    {
        return paddleColor;
    }

    /**
     * A helper method that sets the color of the paddle.
     * 
     * @param newPaddleColor The color of the paddle.
     */
    public void setPaddleColor(Color newPaddleColor)
    {
        paddleColor = newPaddleColor;
    }
}