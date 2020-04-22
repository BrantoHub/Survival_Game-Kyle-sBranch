package rectangles;
import gameObjects.*;
import managers.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BoundingRect2D extends GameObject {
    private Rectangle2D.Double boundingBox;



    private int spriteWidth;
    private int spriteHeight;
    private float scale;
    // Overloaded constructor that takes in the x and y postion of the sprite in order to create the correct size of the bounding box
    public BoundingRect2D(int pXPos, int pYPos,
                             int pWidth, int pHeight, float pscale)
    {
        xPos = pXPos;
        yPos = pYPos;
        scale = pscale;
        spriteWidth = pWidth;
        spriteHeight = pHeight;

        boundingBox=  new Rectangle2D.Double(xPos*scale,yPos*scale,
                spriteWidth*scale,
                spriteHeight*scale);
    }

    //Function called whenever the sprite moves in order to allow the bounding box to be moved with it
    public void updateBoundingBox(int pX, int pY)
    {
        boundingBox.x=pX*scale;
        boundingBox.y=pY*scale;
    }
    @Override
    //Actually is what is calling the drawing the new graphics whenever the bounding box is updated
    public void draw(Graphics g) {
        //System.out.println("Drawing bounding box" + xPos + " " + yPos);
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(boundingBox);
        // COPY AND PASTE THIS

    }
    //Function actually doing the drawing
    public void draw(Graphics g, AffineTransform at) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(boundingBox);


    }
    public Rectangle2D getBoundingBox() {


        return boundingBox;
    }


}
