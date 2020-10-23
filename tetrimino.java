import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
    ArrayList<Integer> xyNum = new ArrayList<Integer>(2);
    ArrayList<Integer> columnNum = new ArrayList<Integer>(4);
    ArrayList<Integer> rowNum = new ArrayList<Integer>(4);


    public tetrimino(char type, int orientation, int blocksize) {
        dim.setBlock_size(blocksize);
        dim.setSide_width(blocksize * 6);
        dim.setTop_height(blocksize * 2);
        x = dim.getSide_width() + dim.getBlock_size() * 3;
        // if (type == 'O') x = dim.getSide_width() + dim.getBlock_size() * 4;
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
        for (int i = 0; i < 4; i++) {
            if (columnNum.size() < 4) {
                columnNum.add(gridCheck.getColumnNum(blocks[i].getX()));
                rowNum.add(gridCheck.getRowNum(blocks[i].getY()));
            }
        }
        if (xyNum.size() < 2) {
            xyNum.add(4);
            xyNum.add(0);
        }
    }

    public void update(boolean plus, int dimApp_height, int dimApp_width, int index) {
        dim.otherUpdate(dimApp_height, dimApp_width);
        gridCheck.otherUpdate(dimApp_height, dimApp_width);
        if (moving) {
            int left = dim.getSide_width() + dim.getPlaying_width();
            int top = dim.getTop_height() + dim.getPlaying_height();
            // System.out.printf("%n--block %d--%n", index);
            for (int i = 0; i < 4; i++) {
                blocks[i].update(dimApp_height, dimApp_width);
                // System.out.println(columnNum.get(i));
                if (columnNum.get(i) < left) left = columnNum.get(i);
                if (rowNum.get(i) < top) top = rowNum.get(i);
            }
            // System.out.println(xyNum.get(0));
            x = x + (plus ? xyNum.get(0) + 5 : -(xyNum.get(0) + 5));
            y = y + (plus ? xyNum.get(1) + 2 : -(xyNum.get(1) + 2));
            left = (left + 5) * dim.getBlock_size();
            top = (top + 2) * dim.getBlock_size();
            placing();
        } else {
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i < 20; i++) {
                numbers.add((2 + i) * dim.getBlock_size());
            }
            System.out.println(numbers);
            for (int i = 0; i < 4; i++) {
                blocks[i].update(dimApp_height, dimApp_width);
                blocks[i].setX(blocks[i].getX() + (plus ? columnNum.get(i) + 5 : -(columnNum.get(i) + 5)));
                blocks[i].setY(blocks[i].getY() + (plus ? rowNum.get(i) + 2 : -(rowNum.get(i) + 2)));
                System.out.println(blocks[i].getY());
            }
        }
        
    }

    // public void nextUpdate(int dimApp_height, int dimApp_width, int index) {
    //     dim.otherUpdate(dimApp_height, dimApp_width);
    //     gridCheck.otherUpdate(dimApp_height, dimApp_width);
    //     int left = dim.getSide_width() + dim.getPlaying_width();
    //     int top = dim.getTop_height() + dim.getPlaying_height();
    //     System.out.printf("%n--block %d--%n", index);
    //     for (int i = 0; i < 4; i++) {
    //         blocks[i].update(dimApp_height, dimApp_width);
    //         System.out.println(columnNum.get(i));
    //         if (columnNum.get(i) < left) left = columnNum.get(i);
    //         if (rowNum.get(i) < top) top = rowNum.get(i);
    //     }
    //     System.out.println(xyNum.get(0));
    //     x = x + (plus ? xyNum.get(0) + 5 : -(xyNum.get(0) + 5));
    //     y = y + (plus ? xyNum.get(1) + 2 : -(xyNum.get(1) + 2));
    //     left = (left + 5) * dim.block_size;
    //     top = (top + 2) * dim.block_size;
    //     placing();
    // }

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
                left = x;
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
                left = x;
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
                left = x;
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
                left = x;
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

    public ArrayList<Integer> getColumnNum() {
        return columnNum;
    }

    public ArrayList<Integer> getRowNum() {
        return rowNum;
    }

    public ArrayList<Integer> getxyNum() {
        return xyNum;
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

        // game bounds
        if (right >= dim.getSide_width() + dim.getPlaying_width()) {
            if (right > dim.getSide_width() + dim.getPlaying_width()) {
                int shift = right - (dim.getSide_width() + dim.getPlaying_width());
                setX(x - shift);
            }
            Rbound = true;
        }

        if (left <= dim.getSide_width()) {
            if (left < dim.getSide_width()) {
                int shift = dim.getSide_width() - left;
                setX(x + shift);
            }
            Lbound = true;
        }

        if (bottom >= dim.getTop_height() + dim.getPlaying_height()) {
            if (bottom > dim.getTop_height() + dim.getPlaying_height()) {
                int shift = bottom - (dim.getTop_height() + dim.getPlaying_height());
                setY(y - shift);
            }
            Dbound = true;
        }

        if (top <= dim.getTop_height()) {
            if (top < dim.getTop_height()) {
                int shift =  dim.getTop_height() - top;
                setY(y + shift);
            }
            Tbound = true;
        }

        for (int i = 0; i < 4; i++) {
            columnNum.set(i, gridCheck.getColumnNum(blocks[i].getX()));
            rowNum.set(i, gridCheck.getRowNum(blocks[i].getY()));
        }
    }

    public void setVisible(int index, boolean newbool) {
        visible[index] = newbool;
    }

    public void setColumnNum(int change) {
        for (int i = 0; i < 4; i++) {
            columnNum.set(i, columnNum.get(i) + change);
        }
    }

    public void setRowNum(int change) {
        for (int i = 0; i < 4; i++) {
            rowNum.set(i, rowNum.get(i) + change);
        }
    }

    public void setRowNumI(int index, int change) {
        rowNum.set(index, rowNum.get(index) + change);
    }

    public void setFColumnNum(int index, int x) {
        columnNum.set(index, gridCheck.getColumnNum(x));
    }

    public void setFRowNum(int index, int y) {
        rowNum.set(index, gridCheck.getRowNum(y));
    }

    public void setxyNum(int newx, int newy) {
        xyNum.set(0, newx);
        xyNum.set(1, newy);
    }

    public void moveRight() {
        setX(x + dim.getBlock_size());
        setLeft(left + dim.getBlock_size());
        placing();
        setColumnNum(1);
        setxyNum(xyNum.get(0) + 1, xyNum.get(1));
    }

    public void moveLeft() {
        setX(x - dim.getBlock_size());
        setLeft(left - dim.getBlock_size());
        placing();
        setColumnNum(-1);
        setxyNum(xyNum.get(0) - 1, xyNum.get(1));
    }

    public void moveDown() {
        setY(y + dim.getBlock_size());
        setTop(top + dim.getBlock_size());
        placing();
        setRowNum(1);
        setxyNum(xyNum.get(0), xyNum.get(1) + 1);
    }
}
