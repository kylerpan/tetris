import java.awt.Color;
import java.awt.Graphics;

public class tetrimino{

    // game dimensions  
    gameDimensions dim = new gameDimensions();
    gridCheck gridCheck = new gridCheck();

    int x, y, left, right, top, bottom;
    char type;
    int orientation;
    boolean moving, Rbound, Lbound, Dbound, Tbound;
    block block1 = new block();
    block block2 = new block();
    block block3 = new block();
    block block4 = new block();
    block[] blocks = new block[]{block1, block2, block3, block4};
    boolean[] visible = new boolean[]{true, true, true, true};
    

    public tetrimino(char type, int orientation, int blocksize) {
        dim.setBlock_size(blocksize);
        dim.setSide_width(blocksize * 6);
        dim.setTop_height(blocksize * 2);
        x = dim.getSide_width() + dim.getBlock_size() * 3;
        if (type == 'O') x  = dim.getSide_width() + dim.getBlock_size() * 4;
        y = dim.getTop_height();
        // System.out.printf("tetrimino block: %d%n", x);
        moving = true;
        Rbound = false;
        Lbound = false;
        Dbound = false;
        Tbound = false;
        this.type = type;
        this.orientation = orientation;
        placing();
    }

    public void update(boolean plus, int dimApp_height, int dimApp_width) {
        dim.otherUpdate(plus, dimApp_height, dimApp_width);
        gridCheck.otherUpdate(plus, dimApp_height, dimApp_width);
        int left = dim.getSide_width() + dim.getPlaying_width();
        int top = dim.getTop_height() + dim.getPlaying_height();
        for (int i = 0; i < 4; i++) {
            blocks[i].update(plus, dimApp_height, dimApp_width);
            int columnNum = gridCheck.getColumnNum(blocks[i].getX());
            int rowNum = gridCheck.getRowNum(blocks[i].getY());
            int blockX = blocks[i].getX() + (plus ? columnNum + 5 : -(columnNum + 5));
            int blockY = blocks[i].getY() + (plus ? rowNum + 2 : -(rowNum + 2));
            if (blockX < left) left = blockX;
            if (blockY < top) top = blockY;
        }
        left = (gridCheck.getColumnNum(left) + 5) * dim.block_size;
        top = (gridCheck.getRowNum(top) + 2) * dim.block_size;
        x = left;
        y = top;
        placing();
    }

    private void placing() {
        if (orientation == 1){
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + i * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size();
                right = left + dim.getBlock_size() * 4;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + i * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x + dim.getBlock_size());
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'J'){
                blocks[0].setX(x);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'L'){
                blocks[0].setX(x + dim.getBlock_size() * 2);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i + 1) * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i) * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
        }
        if (orientation == 2) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size() * 2);
                    blocks[i].setY(y - dim.getBlock_size() + (i) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size() * 2;
                top = y - dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 4;
                right = left + dim.getBlock_size();
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + i * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x + dim.getBlock_size() * 2);
                blocks[0].setY(y + dim.getBlock_size());
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'J'){
                blocks[0].setX(x + dim.getBlock_size() * 2);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'L'){
                blocks[0].setX(x + dim.getBlock_size() * 2);
                blocks[0].setY(y + dim.getBlock_size() * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i) * dim.getBlock_size());
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size() * 2);
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + dim.getBlock_size() * 2);
                    blocks[i].setY(y + (i) * dim.getBlock_size());
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
        }
        if (orientation == 3) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + i * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y + dim.getBlock_size();
                bottom = top + dim.getBlock_size();
                right = left + dim.getBlock_size() * 4;
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + i * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x + dim.getBlock_size());
                blocks[0].setY(y + dim.getBlock_size() * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y + dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'J'){
                blocks[0].setX(x + dim.getBlock_size() * 2);
                blocks[0].setY(y + dim.getBlock_size() * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y + dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'L'){
                blocks[0].setX(x);
                blocks[0].setY(y + dim.getBlock_size() * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x;
                top = y + dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i + 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size() * 2);
                }
                left = x;
                top = y + dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + (i) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 1) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size() * 2);
                }
                left = x;
                top = y + dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 3;
            }
        }
        if (orientation == 4) {
            if (type == 'I'){
                for (int i = 0; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y - dim.getBlock_size() + (i) * dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y - dim.getBlock_size();
                bottom = top + dim.getBlock_size() * 4;
                right = left + dim.getBlock_size();
            }
            if (type == 'O'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + i * dim.getBlock_size());
                    blocks[i].setY(y);
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + (i - 2) * dim.getBlock_size());
                    blocks[i].setY(y + dim.getBlock_size());
                }
                left = x + dim.getBlock_size();
                top = y;
                bottom = top + dim.getBlock_size() * 2;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'T'){
                blocks[0].setX(x);
                blocks[0].setY(y + dim.getBlock_size());
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'J'){
                blocks[0].setX(x);
                blocks[0].setY(y + dim.getBlock_size() * 2);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'L'){
                blocks[0].setX(x);
                blocks[0].setY(y);
                for (int i = 1; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'S'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x);
                    blocks[i].setY(y + (i) * dim.getBlock_size());
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
            }
            if (type == 'Z'){
                for (int i = 0; i < 2; i++){
                    blocks[i].setX(x + dim.getBlock_size());
                    blocks[i].setY(y + (i) * dim.getBlock_size());
                }
                for (int i = 2; i < 4; i++){
                    blocks[i].setX(x);
                    blocks[i].setY(y + (i - 1) * dim.getBlock_size());
                }
                left = x;
                top = y;
                bottom = top + dim.getBlock_size() * 3;
                right = left + dim.getBlock_size() * 2;
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
                g.fillRect(blocks[j].getX(), blocks[j].getY(), dim.getBlock_size(), dim.getBlock_size());
                g1.setStroke(new java.awt.BasicStroke(2));
                g1.setColor(Color.black);
                g1.drawRect(blocks[j].getX(), blocks[j].getY(), dim.getBlock_size(), dim.getBlock_size());
            }
        }
    }

    public int getBlock_size() {
        return dim.block_size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public boolean getTbound() {
        return Tbound;
    }

    public int getOrientation() {
        return orientation;
    }

    public void refreshPlacing() {
        placing();
    }

    public void setX(int newX) {
        x = newX;
        placing();
    }

    public void setY(int newY) {
        y = newY;
        placing();
    }

    public void setLeft(int newLeft) {
        int diff = left - newLeft;
        for (int i = 0; i < 4; i++) {
            blocks[i].setX(blocks[i].getX() - diff);
        }
        left = newLeft;
        placing();
    }

    public void setRight(int newRight) {
        int diff = right - newRight;
        for (int i = 0; i < 4; i++) {
            blocks[i].setX(blocks[i].getX() - diff);
        }
        right = newRight;
        placing();
    }

    public void setTop(int newTop) {
        int diff = top - newTop;
        for (int i = 0; i < 4; i++) {
            blocks[i].setY(blocks[i].getY() - diff);
        }
        top = newTop;
        placing();
    }

    public void setBottom(int newBottom) {
        int diff = bottom - newBottom;
        for (int i = 0; i < 4; i++) {
            blocks[i].setY(blocks[i].getY() - diff);
        }
        bottom = newBottom;
        placing();
    }

    public void setBlockX(int index, int newX) {
        blocks[index].setX(newX);
    }

    public void setBlockY(int index, int newY) {
        blocks[index].setY(newY);
    }

    // public void setBlockSize(int newSize) {
    //     for (int i = 0; i < 4; i++) {
    //         blocks[i].setSize(newSize);
    //     }
    //     // dim.getBlock_size() = newSize;
    // }

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

    public void setTbound(boolean newTbound) {
        Tbound = newTbound;
    }

    public void setOrientation(int newOrientation) {
        orientation = newOrientation;
        placing();
    }

    public void setVisible(int index, boolean newbool) {
        visible[index] = newbool;
    }

    public void moveRight() {
        setX(x + dim.getBlock_size());
        setLeft(left + dim.getBlock_size());
        placing();
    }

    public void moveLeft() {
        setX(x - dim.getBlock_size());
        setLeft(left - dim.getBlock_size());
        placing();
    }

    public void moveDown() {
        setY(y + dim.getBlock_size());
        setTop(top + dim.getBlock_size());
        placing();
    }
}
