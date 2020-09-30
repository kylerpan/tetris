import java.awt.Color;
import java.awt.Graphics;

public class block{

    // game dimensions 
	gameDimensions dim = new gameDimensions();

    int x, y;
    int size = dim.block_size;

    public block() {
        x = 410;
        y = 400;
    }

    public void draw(Graphics g) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        g.setColor(Color.white);
        g.fillRect(x, y, size, size);
        
        g1.setStroke(new java.awt.BasicStroke(2));
        g1.setColor(Color.black);
        g1.drawRect(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void setX(int newx) {
        x = newx;
    }

    public void setY(int newy) {
        y = newy;
    }
}
