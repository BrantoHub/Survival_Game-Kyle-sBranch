package managers;

import gameObjects.*;
import rectangles.*;
import managers.*;
import managers.GameAudio;
import managers.GameScreen;
import managers.NorthPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
public class GameManager extends JFrame implements Runnable{
    // Size of window when created
    //-----------------------------------------
    public final static int WIDTH = 330;
    public final static int HEIGHT = 700;
    //-----------------------------------------
    // Non changing ints that determine number of food and arrows on screen
    public final static int NUMARROWS=6;
    public static final int NUMFOOD =9;
    //-----------------------------------------
    // Score achieved whenever food is eaten
    private int Player_Score;
    //-----------------------------------------
    // Used in run thread to determine if the player has been shot by an arrow or not
    private boolean alive;
    //-----------------------------------------
    // Cheat used for easy win
    private boolean cheat;
    //-----------------------------------------
    private ArrayList<Food> food;
    private boolean GameEnd;
    //-----------------------------------------
    NorthPanel PanelNorth;
    private GameScreen gameScreen;  //The screen on which we will draw
    private GameKeyListener keyListener; //The listener for key presses
    private Player player; //The player in the dog and bone game
    private ArrayList<GameObject> gameObjects; //all game objects except the player
    public int eatenfood;
    private Thread loop;
    private Random rand;
    private boolean Shoot;
    //-----------------------------------------
    // Default Constructor
    public GameManager()
    {
        Player_Score =0; //Setting initial score
        eatenfood =0;
        alive = true; //Making it so the thread will keep running
        cheat = false;
        GameEnd = false;
        this.setTitle("Jungle Adventure"); //set the title of the window
        this.setSize(WIDTH, HEIGHT); //set the width and height of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        PanelNorth = new NorthPanel(this);
        gameScreen = new GameScreen(); //Instantiate the panel on which we will draw
        this.add(PanelNorth, BorderLayout.NORTH); // add JPanel to north of the screen
        this.add(gameScreen, BorderLayout.CENTER);  //add the JPanel to the center of the screen

        this.setVisible(true); // set the window to be visible
        keyListener = new GameKeyListener(); //instantiate the listener for key presses

        addKeyListener(keyListener); //add the key listener to the JFrame
        Shoot = false;
        rand = new Random();
        SetUpGame();

    }



    /********************************************************************
     * SetupGame: used for the initialization process for the game
     * Create the player
     * Create the game objects (gameObjects.Arrows in this case)
     */
    private void SetUpGame()
    {
        //GameAudio audio = new GameAudio("Audio/TangoMusic (1).wav", true);
        //Instantiate the gameObjects.Player
        player = new Player("Images/spritesheet.png", getWindowWidth(), getWindowHeight());
        //Instantiate the game objects.  We will have 6 arrows in the game
        gameObjects = new ArrayList<GameObject>();
        food = new ArrayList<Food>();
        gameScreen.setPlayerReferenceInScreen((Player)player);
        gameScreen.setGameObjectsReferenceInScreen(gameObjects);
        gameScreen.setFoodinScreen(food);
        //Generate the ARROWS on the screen
        for (int i=0; i<NUMARROWS; i++) {
            GenerateArrows();
        }
        for (int i =0; i<NUMFOOD; i++)
        {
            GenerateFood();
        }

        RunGame();
    }

    //Adds the sprites of the food images to a array list in order to be put onto the screen
    private void GenerateFood()
    {
        food.add(new Food("Images/Food2.png"));
    }

    //Adds the sprites of the arrows to the gameobjects list
    private void GenerateArrows()
    {
        gameObjects.add(new Arrows("Images/arrows2.png"));
    }

    //Function that starts the loop used in order to run the thread as well as repaint the screen if needed
    public void RunGame()
    {
        gameScreen.repaint();
        repaint();
        loop = new Thread(this);
        loop.start();

    }

    //Thread
    //Keeps running while the player is still alive (has not been shot)
    @Override
    public void run() {
        while(alive) {
            // For loop that goes through the game objects and shoots the arrow in the general location of the player
            for (GameObject aGameObject : gameObjects) {

                //aGameObject.setyPos((aGameObject.getyPos() + rand.nextInt(10*4)) );
                if(player.getyPos()* Player.PLAYER_SIZE > ((aGameObject.getyPos()* Arrows.SCALE)-30)
                    && (player.getyPos()* Player.PLAYER_SIZE < ((aGameObject.getyPos()*Arrows.SCALE)+30)))
                {
                    GameAudio ArrowImpact = new GameAudio("Audio/ArrowImpact.wav", false);
                    Shoot = true;
                    Arrows arrow = (Arrows) aGameObject;
                    arrow.Shoot(gameScreen, this);

                }

            }
            //---------------------------------------------------------------------------------------------------------------------
            //---------------------------------------------------------------------------------------------------------------------
            //----------------------------------------------------------------------------------------------------------------------
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkCollisions();
            //For loop that actively checks if the arrow is off screen, if so it knows to re shoot
            for(GameObject anArrow :gameObjects) {

                checkArrowOffScreen((Arrows)anArrow);
            }
            gameScreen.repaint();
            displayWinScreen();

        }
    }
    //Method that if the player has eaten all the food than a dialog box will appear prompting the player with a win screen, as well as displaying score and the option to quit/ replay the game.
    private void displayWinScreen() {
        if(eatenfood == NUMFOOD)
        {
            int winning = JOptionPane.showConfirmDialog(null, "Congratulations! Want to play again?", "You Won!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (winning == JOptionPane.NO_OPTION) {
                this.dispose();
                System.exit(0);
            } else if (winning == JOptionPane.YES_OPTION) {
                //If player chooses to play again a new game manager is initialized
                GameManager newGameManager = new GameManager();
                this.dispose();
                gameScreen = null;
            } else if (winning == JOptionPane.CLOSED_OPTION) {
                this.dispose();
                System.exit(0);
            }
        }
    }
    // Method that if the player is shot with an arrow and dies this will pop up, simply stating they can replay or exit the game
    private void displayEndScreen()
    {
        int ending = JOptionPane.showConfirmDialog(null, "Play Again?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ending == JOptionPane.NO_OPTION) {
            this.dispose();
            System.exit(0);
        } else if (ending == JOptionPane.YES_OPTION) {
            GameManager newGameManager = new GameManager();
            this.dispose();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (ending == JOptionPane.CLOSED_OPTION) {
            this.dispose();
            System.exit(0);
        }
    }

    //Key listener class for both moving the player object itself and going through the sprite sheet and making it appear the sprite is walking in a direction
    private class GameKeyListener implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            //Alive is still being used so if the player has been shot or has won the game they cannot keep walking around the screen
            if(alive) {
                Player p = (Player) player;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    p.setxPos(p.getxPos() + Player.PLAYER_SPEED);
                    p.setSubImageY(2); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    p.setxPos(p.getxPos() - Player.PLAYER_SPEED);
                    p.setSubImageY(1); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    p.setyPos(p.getyPos() - Player.PLAYER_SPEED);
                    p.setSubImageY(3); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    p.setyPos(p.getyPos() + Player.PLAYER_SPEED);
                    p.setSubImageY(0); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }
                }
                p.UpdateCollisionBoxPosition();
                repaint();
            }
        }
        // Whenever a key is no longer being pressed it changed the sprite image to a more neutral looking forward position
        @Override
        public void keyReleased(KeyEvent e) {
            //Set the place to the middle picture in the first row...standing position
            Player p = (Player) player;
            if(alive)
                p.setSubImageX(1);

        }

    }

    public void checkArrowOffScreen(Arrows anArrow)
    {
        if ((anArrow.getxPos()<= 0) )
        {
            anArrow.setxPos(Arrows.FixedX);
            Shoot= false;
            System.out.println("We are not shooting anymore and bone is back to pos");
        }
    }

    //Accessors and mutator
    //-------------------------------------------------------------------------
    public int getWindowHeight()
    {
        return this.getHeight();
    }

    public int getWindowWidth()
    {
        return this.getWidth();
    }
    public void setAlive(boolean pAlive)
    {
        alive = pAlive;
    }
    public boolean getAlive()
    {
        return alive;
    }
    public void setCheat(boolean pCheat)
    {
        cheat = pCheat;
    }
    public boolean getCheat()
    {
        return cheat;
    }
    //-------------------------------------------------------------------------

    //Check collision function that is being called in the thread that determines if the player has intersected with a food object or an arrow object
    public boolean checkCollisions()
    {
        boolean collided = false;
        //If the cheat button is not pressed this just makes it so the arrows will actually kill the player
        if(!cheat)
        {



            for (GameObject anObject : gameObjects) {
                Arrows anArrow = (Arrows) anObject;
                if (player.getCollisionBox().intersects(anArrow.getCollisionBox())) {
                    displayEndScreen();
                    collided = true;
                    alive = false;
                    PanelNorth.CheckButtonProperties();
                }
            }

        }
        //Initial check to see if the player has intersected with any of the food objects
        //It than will add it to a list to be removed
        ArrayList<Food> objectToBeRemoved = new ArrayList<Food>();
        for (Food afood : food) {

            if (player.getCollisionBox().intersects(afood.getCollisionBox())) {

                objectToBeRemoved.add(afood);
                PanelNorth.CheckButtonProperties();
                GameAudio YummySound = new GameAudio("Audio/yummy.wav", false);
                Player p = (Player) player;
                p.updatePositions();
                //p.setPlayerSize(p.PLAYER_SIZE + 0.02f);
                p.updatePositions();


            }
        }
        //For loop that actually removes the food object from the array list whenever the player eats it
        for (Food afood : objectToBeRemoved) {

            food.remove(afood);
            eatenfood += 1;
            Player_Score += afood.getEnergy();
            System.out.println("gameObjects.Player Score: " + Player_Score);
            System.out.println("gameObjects.Food Eaten: " + eatenfood);
        }
        gameScreen.setFoodinScreen(food);
        gameScreen.repaint();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return collided;
    }
}
