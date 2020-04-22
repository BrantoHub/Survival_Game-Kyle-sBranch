package gameObjects;
import managers.*;
import rectangles.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Arrows extends GameObject
{
    //Variables used for the arrows whenever they are created
    private Random rnd;
    private BoundingRect2D collisionBox;
    private BufferedImage spriteSheet;
    private AffineTransform at;
    public static int FixedX;
    public static final int TILE_WIDTH = 490;
    public static final int TILE_HEIGHT = 95;
    public static final float SCALE = 0.2f;
    //Overloaded constructor that takes in the sprite image path so that it may be drawn
    public Arrows(String spriteFileName)
    {
        at = new AffineTransform();
        rnd = new Random();
        //Load the image for aBoneObject
        loadSprite(spriteFileName);

        spriteSheet = sprite;
        //Generating the random position that the arrow will be shot at
        int RandomY = (int) (Math.random() * (4 - 1 + 1) + 1);
        //sprite = spriteSheet.getSubimage((0*TILE_WIDTH )+ 60, (4*TILE_HEIGHT)+ 95, TILE_WIDTH, TILE_HEIGHT);
        //Seeing that the position will not be in fact one, so that it will be on the screen
        if(RandomY != 1)
        {
            sprite = spriteSheet.getSubimage((0 * TILE_WIDTH) + 60, (RandomY * TILE_HEIGHT) + 30, TILE_WIDTH, TILE_HEIGHT);
        }
        else {
            sprite = spriteSheet.getSubimage((0 * TILE_WIDTH) + 60, (1 * TILE_HEIGHT) + 2, TILE_WIDTH, TILE_HEIGHT);
        }



        //gameObjects.Arrows are placed on the screen in random positions in the game play area

        //xPos = rnd.nextInt((managers.GameManager.WIDTH * 4)-50);
        //yPos = rnd.nextInt(500) * -1;
        xPos = (GameManager.WIDTH *4) + 260;
        FixedX = xPos;
        yPos = rnd.nextInt((GameManager.HEIGHT*4) - 50);
        collisionBox = new BoundingRect2D(xPos, yPos, sprite.getWidth(), sprite.getHeight(), SCALE);
        visible = true;
    }

    public void UpdateCollisionBoxPosition()
    {

        collisionBox.updateBoundingBox(this.xPos, this.yPos);


    }
    /***********************************************
     *
     * Overloaded function to force the player and other
     * objects to draw themselves on the screen
     * Have to pass in the graphics object associated with
     * the screen
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        at.setToIdentity();
        at.scale(SCALE, SCALE);
        at.translate(xPos, yPos);
        g2d.drawImage(this.sprite, at, null);
        //g2d.drawImage(this.sprite, xPos, yPos, null);
        UpdateCollisionBoxPosition();
        collisionBox.draw(g2d);



    }
    //Gets the collision box of the arrows so that it can be checked if it intersects with the player bounding box
    public Rectangle2D getCollisionBox() {
        return collisionBox.getBoundingBox();
    }
    public void Shoot(GameScreen Screenref, GameManager ManagerRef)
    {
        while (xPos*Arrows.SCALE > 0)
        {
            xPos -= 50;
            boolean Collided = false;
            if(ManagerRef.getAlive()){
                Screenref.repaint();
                Collided = ManagerRef.checkCollisions();
            }


            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Collided) {
                GameAudio DeathSound = new GameAudio("Audio/death.wav", false);
                return;
            }


        }

    }


}

