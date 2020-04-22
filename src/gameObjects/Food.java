package gameObjects;
import managers.*;
import rectangles.*;
import rectangles.BoundingRect2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Food extends GameObject {
    //Creating initial variables that are related to the food object
    private int energy;
    public static float SCALE = 0.2f;
    public static int PicDimensions = 185;
    private Random rand;
    private BoundingRect2D collisionBox;
    private AffineTransform at;
    private BufferedImage spriteSheet;


    //Overloaded constructor used to set the random in the food as well as actually get the sprites that are being used in the food
    public Food(String spriteFileName)
    {

        rand = new Random();
        at = new AffineTransform();
        GenerateEnergy();
        loadSprite(spriteFileName);
        spriteSheet = sprite;
        LoadFood();
    }

    //Function that both gets the actually sprite image as well as putting the image on the game screen on a random x and y position
    private void LoadFood() {
        int RandomY = rand.nextInt(3);
        int RandomX = rand.nextInt(3);
        //sprite = spriteSheet.getSubimage((0*TILE_WIDTH )+ 60, (4*TILE_HEIGHT)+ 95, TILE_WIDTH, TILE_HEIGHT);

        sprite = spriteSheet.getSubimage((PicDimensions * RandomX) , (RandomY * PicDimensions) , PicDimensions, PicDimensions);

        xPos = ((rand.nextInt((GameManager.WIDTH - 10) + 1) + 10) - 10) * 4;
        yPos = ((rand.nextInt((GameManager.HEIGHT - 10) + 1) + 10) - 10) * 4;
        collisionBox = new BoundingRect2D(xPos, yPos, sprite.getWidth(), sprite.getHeight(), SCALE);
        visible = true;

    }
    // Generates random energy that is than assigned to a food object on the screen
    public void GenerateEnergy()
    {
        energy = rand.nextInt((120-10)+1) +10;
    }
    @Override
    public void draw(Graphics g)
    //Actual drawing of the food object on the screen as well as making the collision box used for the food so it may be intersected
    {
        Graphics2D g2d = (Graphics2D)g;
        at.setToIdentity();
        at.scale(SCALE, SCALE);
        at.translate(xPos, yPos);
        g2d.drawImage(this.sprite, at, null);
        //g2d.drawImage(this.sprite, xPos, yPos, null);

        collisionBox.draw(g2d);
    }
    public Rectangle2D getCollisionBox() {
        return collisionBox.getBoundingBox();
    }
    public int getEnergy()
    {
        return energy;
    }

}
