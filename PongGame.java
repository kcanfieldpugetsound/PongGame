import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
/**
 * This is the Pong Game that will control all of the aspects of playing the game including checking to see if the ball has been hit,
 * hitting the ball, drawing the ball on the canvas, drawing the game, resetting the game, etc.
 * 
 * There can be one or two players with the computer playing intelligently if one player is playing.
 * 
 * The default game made is set to easy mode with sounds being played.
 * 
 * When the user changes any settings by using the settings window, then those changes will be made here to affect gameplay.
 * 
 * The difficulty of the game is based on the size of the paddles and the speed of the ball.
 * This can have a value of 1, for easy: large paddles and slow speed; 2, for medium: smaller paddles and a slightly faster speed; or 3 for difficult: very small paddles and fast ball speed.
 * 
 * @author (Kramer Canfield) 
 * @version (29 November 2012)
 * @version (finished 10_December_2012)
 */
public class PongGame implements ActionListener, KeyListener
{
    //fields
    protected int numberOfPlayers;//either player 1 is playing against a friend==2, or playing against the computer==1
    protected Paddle currentPaddle;//the paddle based on whether or not 
    protected Paddle paddleLeft;//the paddle on the left is for player 1
    protected Paddle paddleRight;//the paddle on the right is for player 2 or the computer
    protected boolean cpuPlaying;//true if the right paddle is controlled by the computer, false if two humans are playing
    protected boolean gameInProgress;//true if the game is currently being played
    private Ball ball;
    protected int difficulty;//the difficulty of the game based on the size of the paddles and the speed of the ball
    protected Color p1C;//The current color of player1's paddle.
    protected Color p2C;//The current color of player2's paddle.

    //these keep track of if certain keys are being pressed
    protected boolean wKeyBeingPressed;//whether or not the "w" key is being pressed
    protected boolean sKeyBeingPressed;//whether or not the "s" key is being pressed
    protected boolean upKeyBeingPressed;//whether or not the up arrow key is being pressed
    protected boolean downKeyBeingPressed;//whether or not the down arrow key is being pressed

    protected boolean playingSoundsOkay;//true if the player wants the game to play sounds, false otherwise

    protected Timer clock; //the clock that will track gameplay, speeding up the ball every so often and also running the animation

    //a random number generator that will be used to change the speed when the ball is hit and also to determine which side the ball goes to on every round
    Random randomGenerator; 

    protected int score1;//the score for player1, the left player
    protected int score2;//the score for player2, the right player

    protected MyCanvas canvas;//the canvas that will be used to draw the game
    /**
     * Constructor for objects of class PongGame
     */
    public PongGame()
    {
        //make a default game that is easy, has 2 players, plays sounds, has two white paddles of "easy" difficulty length, and a ball set to the easy speeds
        this.difficulty = 1;
        numberOfPlayers = 2;
        cpuPlaying = false;//booleans are false by default so it does not need to be declared as false if both paddles are controlled by humans, but it is good to make sure anyway
        playingSoundsOkay = true;//play sounds in the first game

        paddleLeft = new Paddle(Color.WHITE, 40, 0, 230);//makes a new paddle to be the left paddle
        paddleRight = new Paddle(Color.WHITE, 40, 490, 230);//makes a new paddle to be the right paddle
        ball = new RoundBall(4, 4);//new ball with easy speeds

        //initialise the canvas and add the KeyListener
        canvas = new MyCanvas(this);
        canvas.addKeyListener(this);

        clock = new Timer(30, this);//makes a new timer at 30 milliseconds, this will control the animation
        randomGenerator = new Random();//initializes the random generator that will be used to change the speed when the ball is hit
        resetCourt();//resets the court positions
    }

    /**
     * This is the playGame method. It uses the animation and controls the game itself.
     *
     */
    public void playGame()
    {
        moveBall();
        //check to see if any keys are being pressed, if so, move the appropriate paddle
        if(wKeyBeingPressed == true)
        {
            moveUp(paddleLeft);
        }
        if(sKeyBeingPressed == true)
        {
            moveDown(paddleLeft);
        }
        if(upKeyBeingPressed == true)
        {
            moveUp(paddleRight);
        }
        if(downKeyBeingPressed == true)
        {
            moveDown(paddleRight);
        }

        //check to see if the ball has been hit, if so, then hit the ball
        if(checkIsHitLeft()==true)
        {
            hitBall();
        }
        if(checkIsHitRight()==true && cpuPlaying == false)
        {
            hitBall();
        }
        if(checkIsHitRight()==true && cpuPlaying == true)
        {
            hitBall();
            moveComputerPaddle();
        }

        //when one player misses,
        //then add 1 to the score of the opposite player because they did NOT miss
        if(checkIsHitLeft()==false && ball.getRightSideOfNet()==false && ball.getX()<0)
        {
            score2++;//adds one to the right side player score because the ball was on the left and it was missed
            resetCourt();//reset the ball to the middle for the next round
        }
        if(checkIsHitRight()==false && ball.getRightSideOfNet()==true && ball.getX()>490)
        {
            score1++;//adds one to the left side player score because the ball was on the right and it was missed
            resetCourt();//reset the ball to the middle for the next round
        }

    }

    /**
     * This helper method checks whether or not the ball has been hit by the left paddle and returns a boolean.
     * This is based on the current position of the ball and the paddles.
     * 
     * @return hasBeenHit Whether or not the ball has been hit. True if the ball has been hit.
     */
    public boolean checkIsHitLeft()
    {
        //if the ball is on the left side of the screen and its position matches the right edge of the left paddle and it is moving to the left
        if(ball.getX()<=paddleLeft.PADDLE_WIDTH && paddleLeft.getY()<=(ball.getY()+ball.getSize()) && ball.getY()<=paddleLeft.getY()+paddleLeft.getPaddleLength() && ball.getXSpeed()<0)
        {
            currentPaddle = paddleLeft;
            return true;//the ball has been hit
        }
        return false;
    }

    /**
     * This helper method checks whether or not the ball has been hit by the right paddle and returns a boolean.
     * This is based on the current position of the ball and the paddles.
     * 
     * @return hasBeenHit Returns true if the ball has been hit.
     */
    public boolean checkIsHitRight()
    {
        //if the ball is on the right side of the screen and its right side position matches the left edge of the right paddleand it is moving to the right
        if((ball.getX()+ball.getSize()+paddleRight.PADDLE_WIDTH >= 500) && paddleRight.getY()<=(ball.getY()+ball.getSize()) && ball.getY()<=paddleRight.getY()+paddleRight.getPaddleLength() && ball.getXSpeed()>0)
        {
            currentPaddle = paddleRight;
            return true;//the ball has been hit
        }
        return false;
    }

    /**
     * This is the method that will control hitting the ball.
     */
    public void hitBall()
    {
        if(currentPaddle==paddleLeft)
        {
            ball.setXSpeed(-(ball.getXSpeed()-1));//add the random number to the speed and reverse the speed in the x-direction so it bounces
        }
        if(currentPaddle==paddleRight)
        {
            ball.setXSpeed(-(ball.getXSpeed()+1));//add the random number to the speed and reverse the speed in the x-direction so it bounces
        }
        
        //numerator of y-speed change multiplier, effectively, the absolute value of distance from middle of the ball to the middle of the paddle
        //Math.abs((ball.getY()+(ball.getSize()/2))-(currentPaddle.getY()+(currentPaddle.getPaddleLength()/2)))

        //denominator of y-speed change multiplier, effectively, the length of the paddle divided by 2
        //((currentPaddle.getPaddleLength())/2)
        //now, the vertical speed will change based on how close to the edge of the paddle the ball hits it

        ball.setYSpeed(ball.getYSpeed()+(ball.getYSpeed()*(Math.abs((ball.getY()+(ball.getSize()/2))-(currentPaddle.getY()+(currentPaddle.getPaddleLength()/2)))/((currentPaddle.getPaddleLength())/2))));

        //makes the computer beep if the user wants to play sounds
        if(playingSoundsOkay==true)
        {
            Toolkit.getDefaultToolkit().beep();//makes it beep!
        }
    }

    /**
     * This method moves the ball.
     */
    public void moveBall()
    {
        //use conditional statement to determine if the ball has hit the wall, if so, then change direction
        if (ball.getY() >= 470-ball.getSize() && ball.getYSpeed() > 0)//subtract the size so the ball does not appear to go outside the screen
        {
            ball.setYSpeed(-ball.getYSpeed());
        }
        if (ball.getY() <= 0 && ball.getYSpeed() < 0)
        {
            ball.setYSpeed(-ball.getYSpeed());
        }
        //gets the new position by adding the speed to the old position
        ball.setX(ball.getX() + ball.getXSpeed());
        ball.setY(ball.getY() + ball.getYSpeed());
    }

    /**
     * This method will reset the court. It is called every time that a point has been won.
     * A random player gets control of the ball.
     */
    public void resetCourt()
    {
        //puts the ball in the middle of the court
        ball.setX(250);
        ball.setY(250);

        //randomly chooses which side to throw the ball
        int randomNum = (randomGenerator.nextInt(2));//randomly generates either 0 or 1, 2 because noninclusive random number generator

        if(randomNum==0)//if the random number is 0
        {
            ball.setXSpeed(4);//the ball goes to the right
            ball.setYSpeed(4);
        }
        if(randomNum==1)//if the random number is 1
        {
            ball.setXSpeed(-4);//the ball goes to the left
            ball.setYSpeed(4);
        }
    }

    /**
     * This helper method will return the score for player 1.
     * @return score1 The current score of player 1.
     */
    public int getScore1()
    {
        return score1;
    }

    /**
     * This helper method will return the score for player 2.
     * @return score2 The current score of player 2.
     */
    public int getScore2()
    {
        return score2;
    }

    /**
     * This helper method will return a String representation of the current score of player 1.
     * @return score1String The String version of the score of player 1.
     */
    public String getScore1String()
    {
        return ""+getScore1();
    }

    /**
     * This helper method will return a String representation of the current score of player 2.
     * @return score2String The String version of the score of player 2.
     */
    public String getScore2String()
    {
        return ""+getScore2();
    }

    /**
     * This method will check to see if there is a winner. First to eleven wins!!
     * ...as long as that player wins by two points as per standard ping pong rules.
     * 
     * @return winnerNumber The winning paddle number. 0 if the game has not been won. 1 if the left player won. 2 if the right player won.
     */
    public int checkWinner()
    {
        if(getScore1()>=11 && (getScore1()-getScore2())>=2)
        {
            gameInProgress = false;
            return 1;
        }
        if(getScore2()>=11 && (getScore2()-getScore1())>=2)
        {
            gameInProgress = false;
            return 2;
        }
        return 0;
    }

    /**
     * This method draws the game.
     * 
     * @param g2d The graphics object used for drawing.
     */
    public void drawGame(Graphics2D g2d)
    {

        g2d.setPaint(paddleLeft.getPaddleColor());//sets the paint color to the color of the left paddle
        paddleLeft.draw(g2d);//draws the left paddle in that color
        g2d.setPaint(paddleRight.getPaddleColor());//sets the paint color to the color of the right paddle
        paddleRight.draw(g2d);//draws the right paddle in that color

        g2d.setPaint(Color.WHITE);//sets the color to white so it shows up on the black screen
        ball.draw(g2d);

        //draw the scores at the top and middle of the screen on either side of the net
        g2d.drawString(getScore1String(), 235, 30);
        g2d.drawString(getScore2String(), 260, 30);

        if(checkWinner()==1)
        {
            drawWinnerLeft(g2d);
        }
        if(checkWinner()==2)
        {
            drawWinnerRight(g2d);
        }
    }

    /**
     * This method draws a string of the left paddle winning on the screen when player1 wins.
     * @param g2d The graphics object used for drawing.
     */
    public void drawWinnerLeft(Graphics2D g2d)
    {
        g2d.drawString("PLAYER 1 WINS THE GAME!!!!", 40, 250);
    }

    /**
     * This method draws a string of the left paddle winning on the screen when player1 wins.
     * @param g2d The graphics object used for drawing.
     */
    public void drawWinnerRight(Graphics2D g2d)
    {
        //changes the win message String based on if the computer is playing or not
        if(cpuPlaying==false)
        {
            g2d.drawString("PLAYER 2 WINS THE GAME!!!!", 250, 250);
        }
        if(cpuPlaying==true)
        {
            g2d.drawString("THE COMPUTER WINS THE GAME!!!!", 250, 250);
        }
    }

    /**
     * This method will move the paddle up.
     * 
     * @param currentPaddle The paddle that will be moved.
     */
    public void moveUp(Paddle currentPaddle)
    {
        //only moves the paddle to the top edge of the screen
        if(currentPaddle.getY()-currentPaddle.getPaddleSpeed() >=0)
        {
            currentPaddle.setY(currentPaddle.getY() - currentPaddle.getPaddleSpeed());
        }   
    }

    /**
     * This method will move the paddle down.
     * 
     * @param currentPaddle The paddle that will be moved.
     */
    public void moveDown(Paddle currentPaddle)
    {
        //only moves the paddle to the bottom edge of the screen
        if(currentPaddle.getY()+currentPaddle.getPaddleSpeed()<=470-currentPaddle.getPaddleLength())
        {
            currentPaddle.setY(currentPaddle.getY() + currentPaddle.getPaddleSpeed());
        }
    }

    /**
     * This method will move the computer paddle. This method is only called if the computer is playing.
     * The computer paddle is the right paddle that is normally player 2.
     */
    public void moveComputerPaddle()
    {
        //only move the computer paddle once the ball has moved beyond the vertical line x=350 so the computer reacts
        //and sometimes will not be fast enough and then lose the round so the human has a fighting chance
        if(ball.getX() >= 350 && ball.getXSpeed() > 0)
        {
            //if the ball is above the paddle
            if(ball.getY()+ball.getSize() < paddleRight.getY())
            {
                moveUp(paddleRight);//move the paddle up
            }

            //if the ball is below the paddle
            if(ball.getY() > (paddleRight.getY()+paddleRight.getPaddleLength()))
            {
                moveDown(paddleRight);//move the paddle down
            }
        }
    }

    /**
     * This method will reset the scores back to zero.
     */
    public void resetScores()
    {
        //changes both scores back to zero
        score1 = 0;
        score2 = 0;
    }

    /**
     * This helper method will return the current difficulty of the game.
     * @return diff The current difficulty of the game.
     */
    public int getDifficulty()
    {
        return difficulty;
    }

    /**
     * This helper method gets whether or not the game is currently in progress.
     * @return gameInProgress A boolean, true if the game is currently being played.
     */
    public boolean getGameInProgress()
    {
        return gameInProgress;
    }

    /**
     * This helper method sets whether or not the game is in progress.
     * @param inProgress A boolean to change whether or not the game is currently being played.
     */
    public void setGameInProgress(boolean inProgress)
    {
        gameInProgress = inProgress;
    }

    /**
     * This helper method will return the color of player1's paddle.
     * @return p1C The current color of player1's paddle.
     */
    public Color getP1C()
    {
        return p1C;
    }

    /**
     * This helper method will return the color of player2's paddle.
     * @return p2C The current color of player2's paddle.
     */
    public Color getP2C()
    {
        return p2C;
    }

    /**
     * This helper method will get and return the current number of players.
     * @return numberOfPlayers The current number of players.
     */
    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    /**
     * This helper method will set the color of player 1, the left paddle.
     * @param c1 The new color for the left paddle.
     */
    public void setP1C(Color c1)
    {
        paddleLeft.setPaddleColor(c1);
    }

    /**
     * This helper method will set the color of player 2, the right paddle.
     * @param c2 The new color for the right paddle.
     */
    public void setP2C(Color c2)
    {
        paddleRight.setPaddleColor(c2);
    }

    /**
     * This method will set the difficulty to the new difficulty.
     * @param newD The new difficulty that will determine how to modify the game. 1 is easy, 2 is medium difficulty, and 3 is hard difficulty.
     */
    public void setDifficulty(int newD)
    {
        if(newD==1)
        {
            difficulty = 1;//make sure to set the difficulty within the game itself
            paddleLeft.setPaddleLength(40);//start with normal size paddles
            paddleRight.setPaddleLength(40);
            //start the ball at a slow speed
            ball.setXSpeed(4);
            ball.setYSpeed(4);
        }
        if(newD==2)
        {
            difficulty = 2;//make sure to set the difficulty within the game itself
            paddleLeft.setPaddleLength(32);//make the paddle lengths smaller
            paddleRight.setPaddleLength(32);
            //start the ball at a faster speed
            ball.setXSpeed(6);
            ball.setYSpeed(6);
        }
        if(newD==3)
        {
            difficulty = 3;//make sure to set the difficulty within the game itself
            paddleLeft.setPaddleLength(20);//make the paddle lengths even smaller
            paddleRight.setPaddleLength(20);
            //start the ball at a very fast speed
            ball.setXSpeed(10);
            ball.setYSpeed(10);
        }
        if(newD==4)
        {
            difficulty = 4;//make sure to set the difficulty within the game itself, 4 means custom
        }
    }

    /**
     * This method will set the paddle length for both paddles to be the specified integer length.
     * 
     * @param length The specified paddle length.
     */
    public void setAllPaddles(int length)
    {
        paddleLeft.setPaddleLength(length);
        paddleRight.setPaddleLength(length);
    }
    
    /**
     * This method will set the initial speed of the ball.
     * @param s The initial horizontal and vertical speed of the ball
     */
    public void setBall(int s)
    {
        ball.setXSpeed(s);
        ball.setYSpeed(s);
    }
    
    /**
     * This method will set the number of players for the game.
     * @param newNumP The new number of players to play the game. If it is 1, then the computer is playing against a human player. If it is 2, then two human players are playing.
     */
    public void setNumberOfPlayers(int newNumP)
    {
        if(newNumP==1)
        {
            numberOfPlayers = 1;//actually change the number of players
            cpuPlaying = true;//set the computer to be playing
        }
        if(newNumP==2)
        {
            numberOfPlayers = 2;//actually change the number of players
            cpuPlaying = false;//set the computer to not be playing
        }
    }

    /**
     * This method will get and return whether or not the user wants to play sounds during the game.
     * @return playSoundsIsOkay A boolean, true if the user wants to play the beep sound when the ball hits a paddle.
     */
    public boolean getPlaySounds()
    {
        return playingSoundsOkay;
    }

    /**
     * This method will set whether or not sounds will play during the game.
     * @param playSoundsIsOkay An integer value from the ItemEvent class based on whether or not the checkbox in settings is selected or not.
     */
    public void setPlaySounds(int playSoundsIsOkay)
    {
        if(playSoundsIsOkay == ItemEvent.SELECTED)
        {
            playingSoundsOkay = true;
        }
        if(playSoundsIsOkay == ItemEvent.DESELECTED)
        {
            playingSoundsOkay = false;
        }
    }

    /**
     * This method will get and return the canvas.
     * @return canvas The canvas for the game to be drawn on.
     */
    public MyCanvas getCanvas()
    {
        return canvas;
    }

    /**
     * This helper method starts the clock.
     */
    public void clockStart()
    {
        clock.start();
    }

    /**
     * This helper method stops the clock.
     */
    public void clockStop()
    {
        clock.stop();
    }

    //*******************************************
    // ActionListener method
    //*******************************************
    /**
     * This is the method from the ActionListener interface.
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == clock && gameInProgress==true)//only play the game if the game is in progress
        {
            canvas.repaint();//refresh the canvas
            playGame();
            if(cpuPlaying==true)//check if the computer should be playing
            {
                moveComputerPaddle();//only make the computer play the game if it is supposed to
            }
        }
    }

    //*******************************************
    // KeyListener methods
    //*******************************************
    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e) 
    {
        //keys for player 1
        if(e.getKeyCode()==KeyEvent.VK_W)
        {
            wKeyBeingPressed = true;
        }
        if(e.getKeyCode()==KeyEvent.VK_S)
        {
            sKeyBeingPressed = true;
        }

        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            upKeyBeingPressed = true;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            downKeyBeingPressed = true;
        }
    }

    /**
     * Invoked when a key has been released.
     */
    public void keyReleased(KeyEvent e) 
    {
        //keys for player 1
        if(e.getKeyCode()==KeyEvent.VK_W)
        {
            wKeyBeingPressed = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_S)
        {
            sKeyBeingPressed = false;
        }

        //keys for player 2
        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            upKeyBeingPressed = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            downKeyBeingPressed = false;
        }
    }

    /**
     * Invoked when a key has been typed.
     * This pong game does not require this.
     */
    public void keyTyped(KeyEvent e) 
    {
    }

}
