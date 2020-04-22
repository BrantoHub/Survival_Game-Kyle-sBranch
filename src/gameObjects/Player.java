package gameObjects;

import gameObjects.GameObject;
import rectangles.BoundingRect2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/****************************************************************
 * gameObjects.Player -- The player object in the game
 */
public class Player extends GameObject {
    private String playerName;  //The name of the player
    private int playerHealth; //The current player's health
    public static final int PLAYER_SPEED = 5;
    //public final static int PLAYER_HEALTH = 10;
    public static float PLAYER_SIZE = 1.2f;
    public final static int TILE_SIZE = 32;
    private AffineTransform at;


    private int subImageX, subImageY;
    private BufferedImage spriteSheet;
    public void setPlayerSize(float pSize)
    {
        PLAYER_SIZE = pSize;
    }

    public void updatePositions()
    {
        this.setxPos((this.getxPos ())+ (int) (PLAYER_SIZE));
        this.setyPos(this.getyPos ()+ (int) (PLAYER_SIZE));
    }



    private BoundingRect2D collisionBox;
    //Set the player's name to what was passed into the constructor
    //Set the player's health
    public Player(String spriteFileName, int pWidth, int pHeight)
    {
        at = new AffineTransform();
        subImageX=0;
        subImageY=0;
        //Load the image for theplayer
        loadSprite(spriteFileName);
        spriteSheet = sprite;
       // playerName = pPlayerName;
        //Set the player's health to that of the constant
        //in the game manager
        //playerHealth = PLAYER_HEALTH;
        //gameObjects.Player always starts out in the same position
        //Left center of the screen.
        xPos = (int)((pWidth/2) -70);
        yPos = (int)((pHeight-sprite.getHeight()) - 90);


        visible = true;
        //collisionBox = new rectangles.BoundingRect2D(xPos, yPos, sprite.getWidth(), sprite.getHeight(), PLAYER_SIZE);

    }

    public void UpdateCollisionBoxPosition()
    {

        collisionBox.updateBoundingBox(this.xPos, this.yPos);

    }
    /***********************************************
     *
     * Overrriden function to force the player and other
     * objects to draw themselves on the screen
     * Have to pass in the graphics object associated with
     * the screen
     */
    public void draw(Graphics g) {
        getSubSprite();
        Graphics2D g2d = (Graphics2D) g;
        at.setToIdentity();
        at.scale(PLAYER_SIZE, PLAYER_SIZE);
        at.translate(xPos, yPos);
        g2d.drawImage(this.sprite, at, null);
        collisionBox = new BoundingRect2D(xPos, yPos, sprite.getWidth(), sprite.getHeight(), PLAYER_SIZE);
       // System.out.println("drawing gameObjects.Player");
        collisionBox.draw(g2d);
        //g.drawImage(this.sprite, xPos, yPos, null);
        //collisionBox.draw(g);
    }
    public Rectangle2D getCollisionBox() {

        return collisionBox.getBoundingBox();
    }

    public  void getSubSprite()
    {
        sprite = spriteSheet.getSubimage(subImageX*TILE_SIZE, subImageY*TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    //Accessors and Mutators
    public int getSubImageX() {
        return subImageX;
    }

    public void setSubImageX(int subImageX) {
        this.subImageX = subImageX;
    }

    public int getSubImageY() {
        return subImageY;
    }

    public void setSubImageY(int subImageY) {
        this.subImageY = subImageY;
    }

}
