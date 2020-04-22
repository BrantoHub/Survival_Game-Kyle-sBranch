package rectangles;
import gameObjects.*;
import managers.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BoundingRectangle extends GameObject {
    private Rectangle boundingBox;



    private int spriteWidth;
    private int spriteHeight;

    public BoundingRectangle(int pXPos, int pYPos,
                             int pWidth, int pHeight)
    {
        xPos = pXPos;
        yPos = pYPos;
        spriteWidth = pWidth;
        spriteHeight = pHeight;

        boundingBox = new Rectangle(xPos, yPos, pWidth, pHeight);
    }


    public void updateBoundingBox(int pX, int pY)
        {
        boundingBox.x=pX;
        boundingBox.y=pY;
    }
    @Override
    public void draw(Graphics g) {
        //System.out.println("Drawing bounding box" + xPos + " " + yPos);
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(boundingBox);
        // COPY AND PASTE THIS

    }
    public void draw(Graphics g, AffineTransform at) {
        //System.out.println("Drawing bounding box" + xPos + " " + yPos);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.draw(boundingBox);
        // COPY AND PASTE THIS
        g2d.draw(at.createTransformedShape(boundingBox));
    }


    //Accessors and Mutators
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }
}
