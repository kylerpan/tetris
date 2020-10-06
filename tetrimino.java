import java.awt.Color;
import java.awt.Graphics;

public class tetrimino{

    // game dimensions  
    gameDimensions dim = new gameDimensions();

    int left, right, top, bottom;
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
        left = dim.side_width + dim.block_size * 3;
        top = dim.top_height;
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
        left = value.getLeft();
        top = value.getTop();
        placing();
        System.out.println(dim.block_size);
    }

    private void placing() {
        if (orientation == 1){
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(left + i * dim.block_size);
                    blocks[i].setY(top);
                }
                bottom = top + dim.block_size;
                right = left + dim.block_size * 4;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + i * dim.block_size);
                    blocks[i].setY(top);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 2) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(left + dim.block_size);
                blocks[0].setY(top);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'J'){
                blocks[0].setX(left);
                blocks[0].setY(top);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'L'){
                blocks[0].setX(left + dim.block_size * 2);
                blocks[0].setY(top);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + (i + 1) * dim.block_size);
                    blocks[i].setY(top);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 2) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + (i) * dim.block_size);
                    blocks[i].setY(top);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
        }
        if (orientation == 2) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(left + dim.block_size * 2);
                    blocks[i].setY(top - dim.block_size + (i) * dim.block_size);
                }
                bottom = top + dim.block_size * 4;
                right = left + dim.block_size;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + (i) * dim.block_size);
                    blocks[i].setY(top);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 2) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(left + dim.block_size * 2);
                blocks[0].setY(top + dim.block_size);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'J'){
                blocks[0].setX(left + dim.block_size * 2);
                blocks[0].setY(top);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'L'){
                blocks[0].setX(left + dim.block_size * 2);
                blocks[0].setY(top + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + dim.block_size * 2);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + dim.block_size * 2);
                    blocks[i].setY(top + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
        }
        if (orientation == 3) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(left + i * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size;
                right = left + dim.block_size * 4;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + i * dim.block_size);
                    blocks[i].setY(top);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 2) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(left + dim.block_size);
                blocks[0].setY(top + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'J'){
                blocks[0].setX(left + dim.block_size * 2);
                blocks[0].setY(top + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'L'){
                blocks[0].setX(left);
                blocks[0].setY(top + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + (i + 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 2) * dim.block_size);
                    blocks[i].setY(top + dim.block_size * 2);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + (i) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 1) * dim.block_size);
                    blocks[i].setY(top + dim.block_size * 2);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 3;
            }
        }
        if (orientation == 4) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top - dim.block_size + (i) * dim.block_size);
                }
                bottom = top + dim.block_size * 4;
                right = left + dim.block_size;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + (i) * dim.block_size);
                    blocks[i].setY(top);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + (i - 2) * dim.block_size);
                    blocks[i].setY(top + dim.block_size);
                }
                bottom = top + dim.block_size * 2;
                right = left + dim.block_size * 2;
            }
            if (type == 'T'){
                blocks[0].setX(left);
                blocks[0].setY(top + dim.block_size);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'J'){
                blocks[0].setX(left);
                blocks[0].setY(top + dim.block_size * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'L'){
                blocks[0].setX(left);
                blocks[0].setY(top);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left);
                    blocks[i].setY(top + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(left + dim.block_size);
                    blocks[i].setY(top + (i) * dim.block_size);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(left);
                    blocks[i].setY(top + (i - 1) * dim.block_size);
                }
                bottom = top + dim.block_size * 3;
                right = left + dim.block_size * 2;
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

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getBlockX(int number) {
        return blocks[number].getX();
    }

    public int getBlockY(int number) {
        return blocks[number].getY();
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

    public void setLeft(int newLeft) {
        int diff = left - newLeft;
        for (int i = 0; i < 4; i++) {
            blocks[i].setX(blocks[i].getX() - diff);
        }
        placing();
    }

    public void setTop(int newTop) {
        int diff = top - newTop;
        for (int i = 0; i < 4; i++) {
            blocks[i].setY(blocks[i].getY() - diff);
        }
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
        for (int i = 0; i < 4; i++) {
            blocks[i].setX(blocks[i].getX() + dim.block_size);
        }
        placing();
    }

    public void moveLeft() {
        for (int i = 0; i < 4; i++) {
            blocks[i].setX(blocks[i].getX() - dim.block_size);
        }
        placing();
    }

    public void moveDown() {
        for (int i = 0; i < 4; i++) {
            blocks[i].setY(blocks[i].getY() + dim.block_size);
        }
        placing();
    }
}
