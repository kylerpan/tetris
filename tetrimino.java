import java.awt.Color;
import java.awt.Graphics;

public class tetrimino{

    int x, y, w;
    char type;
    boolean moving;
    block block1 = new block();
    block block2 = new block();
    block block3 = new block();
    block block4 = new block();
    block[] blocks = new block[]{block1, block2, block3, block4};

    public tetrimino(char type) {
        x = 370;
        y = 80;
        moving = true;
        this.type = type;
        placing();
    }

    private void placing() {
        if (type == 'I'){
            for (int i = 0; i < 4; i++){
                blocks[i].setX(x + i * 40);
                blocks[i].setY(y);
            }
            w = 160;
        }
        if (type == 'O'){
            for (int i = 0; i < 2; i++){
                blocks[i].setX(x + i * 40);
                blocks[i].setY(y);
            }
            for (int i = 2; i < 4; i++){
                blocks[i].setX(x + (i - 2) * 40);
                blocks[i].setY(y + 40);
            }
            w = 80;
        }
        if (type == 'T'){
            blocks[0].setX(x + 40);
            blocks[0].setY(y);
            for (int i = 1; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            w = 120;
        }
        if (type == 'J'){
            blocks[0].setX(x);
            blocks[0].setY(y);
            for (int i = 1; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            w = 120;
        }
        if (type == 'L'){
            blocks[0].setX(x + 80);
            blocks[0].setY(y);
            for (int i = 1; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            w = 120;
        }
        if (type == 'S'){
            for (int i = 0; i < 2; i++){
                blocks[i].setX(x + (i + 1) * 40);
                blocks[i].setY(y);
            }
            for (int i = 2; i < 4; i++){
                blocks[i].setX(x + (i - 2) * 40);
                blocks[i].setY(y + 40);
            }
            w = 120;
        }
        if (type == 'Z'){
            for (int i = 0; i < 2; i++){
                blocks[i].setX(x + (i) * 40);
                blocks[i].setY(y);
            }
            for (int i = 2; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            w = 120;
        }
    }

    public void draw(Graphics g) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        if (type == 'I') {
            g.setColor(new Color(0, 201, 221));
        }
        if (type == 'O') {
            g.setColor(new Color(254, 238, 44));
        }
        if (type == 'T') {
            g.setColor(new Color(153, 0, 186));
        }
        if (type == 'J') {
            g.setColor(new Color(14, 101, 198));
        }
        if (type == 'L') {
            g.setColor(new Color(247, 167, 0));
        }
        if (type == 'S') {
            g.setColor(new Color(0, 210, 44));
        }
        if (type == 'Z') {
            g.setColor(new Color(170, 2, 0));
        }

        for (int i = 0; i < 4; i++){
            g.fillRect(blocks[i].getX(), blocks[i].getY(), blocks[i].getSize(), blocks[i].getSize());
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            g1.drawRect(blocks[i].getX(), blocks[i].getY(), blocks[i].getSize(), blocks[i].getSize());
        }
        
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public char getType() {
        return type;
    }

    public boolean getMoving() {
        return moving;
    }

    public void setX(int newx) {
        x = newx;
        placing();
    }

    public void setY(int newy) {
        y = newy;
        placing();
    }

    public void setMoving(boolean newMoving) {
        moving = newMoving;
    }

    public void moveRight() {
        x += 40;
        placing();
    }

    public void moveLeft() {
        x -= 40;
        placing();
    }

    public void moveDown() {
        y += 40;
        placing();
    }
}
