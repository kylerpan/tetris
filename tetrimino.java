import java.awt.Color;
import java.awt.Graphics;

public class tetrimino{

    // game dimensions  
    gameDimensions dim = new gameDimensions();

    int x, y, h, w;
    char type;
    int orientation;
    boolean moving, Rbound, Lbound, Dbound;
    block block1 = new block();
    block block2 = new block();
    block block3 = new block();
    block block4 = new block();
    block[] blocks = new block[]{block1, block2, block3, block4};
    boolean[] visible = new boolean[]{true, true, true, true};
    

    public tetrimino(char type, int orientation) {
        x = dim.side_width + dim.block_size * 3;
        y = dim.top_height;
        moving = true;
        Rbound = false;
        Lbound = false;
        Dbound = false;
        this.type = type;
        this.orientation = orientation;
        placing();
    }

    public void update(tetrimino value, gameDimensions dimensions) {
        System.out.println(dim.block_size);
        dim = dimensions;
        x = value.getX();
        y = value.getY();
        placing();
        System.out.println(dim.block_size);
    }

    private void placing() {
        if (orientation == 1){
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + i * dim.block_size);
                    blocks[i].setY(y);
                }
                h = dim.block_size;
                w = dim.block_size * 4;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + i * dim.block_size);
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x + dim.block_size);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'J'){
                blocks[0].setX(x);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'L'){
                blocks[0].setX(x + dim.block_size * 2);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i + 1) * dim.block_size);
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i) * dim.block_size);
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
        }
        if (orientation == 2) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + dim.block_size * 2);
                    blocks[i].setY(y - dim.block_size + (i) * dim.block_size);
                }
                h = dim.block_size * 4;
                w = dim.block_size;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i) * dim.block_size);
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x + dim.block_size * 2);
                blocks[0].setY(y + dim.block_size);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'J'){
                blocks[0].setX(x + dim.block_size * 2);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'L'){
                blocks[0].setX(x + dim.block_size * 2);
                blocks[0].setY(y + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + dim.block_size * 2);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + dim.block_size * 2);
                    blocks[i].setY(y + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
        }
        if (orientation == 3) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + i * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size;
                w = dim.block_size * 4;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + i * dim.block_size);
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x + dim.block_size);
                blocks[0].setY(y + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'J'){
                blocks[0].setX(x + dim.block_size * 2);
                blocks[0].setY(y + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'L'){
                blocks[0].setX(x);
                blocks[0].setY(y + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i + 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.block_size);
                    blocks[i].setY(y + dim.block_size * 2);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.block_size);
                    blocks[i].setY(y + dim.block_size * 2);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 3;
            }
        }
        if (orientation == 4) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y - dim.block_size + (i) * dim.block_size);
                }
                h = dim.block_size * 4;
                w = dim.block_size;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i) * dim.block_size);
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.block_size);
                    blocks[i].setY(y + dim.block_size);
                }
                h = dim.block_size * 2;
                w = dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x);
                blocks[0].setY(y + dim.block_size);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'J'){
                blocks[0].setX(x);
                blocks[0].setY(y + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'L'){
                blocks[0].setX(x);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x);
                    blocks[i].setY(y + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + dim.block_size);
                    blocks[i].setY(y + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x);
                    blocks[i].setY(y + (i - 1) * dim.block_size);
                }
                h = dim.block_size * 3;
                w = dim.block_size * 2;
            }
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

        for (int j = 0; j < 4; j++){
            if (visible[j]){
                g.fillRect(blocks[j].getX(), blocks[j].getY(), blocks[j].getSize(), blocks[j].getSize());
                g1.setStroke(new java.awt.BasicStroke(2));
                g1.setColor(Color.black);
                g1.drawRect(blocks[j].getX(), blocks[j].getY(), blocks[j].getSize(), blocks[j].getSize());
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBlockX(int number) {
        return blocks[number].getX();
    }

    public int getBlockY(int number) {
        return blocks[number].getY();
    }

    public int getH() {
        return h;
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

    public boolean getRbound() {
        return Rbound;
    }

    public boolean getLbound() {
        return Lbound;
    }

    public boolean getDbound() {
        return Dbound;
    }

    public int getOrientation() {
        return orientation;
    }

    public void refreshPlacing() {
        placing();
    }

    public void setX(int newx) {
        x = newx;
        placing();
    }

    public void setY(int newy) {
        y = newy;
        placing();
    }

    public void setBlockX(int index, int newX) {
        blocks[index].setX(newX);
    }

    public void setBlockY(int index, int newY) {
        blocks[index].setY(newY);
    }

    public void setBlockSize(int newSize) {
        for (int i = 0; i < 4; i++) {
            blocks[i].setSize(newSize);
        }
    }

    public void setType(char newType) {
        type = newType;
        placing();
    }

    public void setMoving(boolean newMoving) {
        moving = newMoving;
    }
    
    public void setRbound(boolean newRbound) {
        Rbound = newRbound;
    }

    public void setLbound(boolean newLbound) {
        Lbound = newLbound;
    }

    public void setDbound(boolean newDbound) {
        Dbound = newDbound;
    }

    public void setOrientation(int newOrientation) {
        orientation = newOrientation;
        placing();
    }

    public void setVisible(int index, boolean newbool) {
        visible[index] = newbool;
    }

    public void moveRight() {
        x += dim.block_size;
        placing();
    }

    public void moveLeft() {
        x -= dim.block_size;
        placing();
    }

    public void moveDown() {
        y += dim.block_size;
        placing();
    }
}
