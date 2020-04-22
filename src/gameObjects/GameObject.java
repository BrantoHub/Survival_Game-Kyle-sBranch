package gameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***************************************************************
 * gameObjects.GameObject:
 * Purpose: Stores common information about objects/players in a simple game
 * Since it will not be instantiated, it was made abstract.
 */
public abstract class GameObject {
    //x and y position of the game object
    protected int xPos;
    protected int yPos;
    protected boolean visible;
    protected BufferedImage sprite;
    protected Rectangle boundingBox;

    /*************************************************************
     * Default constructor:  sets a game object to position 0,0
     */
    public GameObject()
    {
        //Default x and y position of the game Object
        xPos = 0;
        yPos = 0;
    }

    /***************************************************************
     * Overloaded Constructor   An x and y position of an object if it is known ahead of time
     */

    private GameObject(int pX, int pY)
    {
        xPos = pX;
        xPos = pY;
    }
    /*************************************************************
     * loadSprite:  Takes the filename of the image associated with the
     * game objects.  For now, this assumes all game objects will hava a
     * picture associated with them and will not be drawn
     */
    protected void loadSprite(String pFileName)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sprite = (BufferedImage) image;

    }
    /*************************************************************
     *
     * draw():  every object will draw itself on the screen so we
     * must pass the pen associated with the screen in.  This an an
     * abstract class since not every object will draw itself the same way
     */
    public abstract void draw(Graphics g);

    /*************************************************************
     *
     * Accessors and Mutators
     */
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }


}
