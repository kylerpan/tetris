import java.awt.Color;
import java.awt.Graphics;

public class block{

    // game dimensions 
	gameDimensions dim = new gameDimensions();

    int x, y;

    public block() {
        x = 410;
        y = 400;
    }

    public void update(int dimApp_height, int dimApp_width) {
        dim.otherUpdate(dimApp_height, dimApp_width);
    }

    public void draw(Graphics g) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        g.setColor(Color.white);
        g.fillRect(x, y, dim.getBlock_size(), dim.getBlock_size());
        
        g1.setStroke(new java.awt.BasicStroke(2));
        g1.setColor(Color.black);
        g1.drawRect(x, y, dim.getBlock_size(), dim.getBlock_size());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public gameDimensions getDim() {
        return dim;
    }

    public void setX(int newx) {
        x = newx;
    }

    public void setY(int newy) {
        y = newy;
    }
}
