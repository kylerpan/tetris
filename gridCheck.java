import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class gridCheck {

    // game dimensions  
    gameDimensions dim = new gameDimensions();
    
    Map<String, ArrayList<Boolean>> columns = new HashMap<String, ArrayList<Boolean>>();
    Map<String, ArrayList<Integer>> blocksMap = new HashMap<String, ArrayList<Integer>>();
    Map<String, tetrimino> map = new HashMap<String, tetrimino>();

    public gridCheck(){
        for (int i = 0; i < 12; i++) {
            String column = String.format("column%d", i);
            ArrayList<Boolean> check = new ArrayList<Boolean>();
            if (i == 0 || i == 11) {
                for (int j = 0; j < 21; j++) {
                    check.add(true);
                }
            } else {
                for (int j = 0; j < 21; j++) {
                    if (j == 20) {
                        check.add(true);
                    } else {
                        check.add(false);
                    }
                }
            }
            columns.put(column, check);
        }
    }

    public void update(boolean plus) {
        dim.update(plus);
    }

    public Map<String, ArrayList<Boolean>> getColumns() {
        return columns;
    }

    public String getColumn(int x) {
        String column = "";
        for (int i = 0; i < 12; i++){
            if (x == dim.getSide_width() + (i - 1) * dim.getBlock_size()) {
                column = String.format("column%d", i);
                break;
            }
        }
        return column;
    }

    public int getColumnNum(int x) {
        int column = 0;
        int lowest = 10000;
        for (int i = 0; i < 12; i++){
            System.out.println(x);
            // System.out.println(dim.getSide_width() + (i - 1) * dim.getBlock_size());
            int diff = x - (dim.getSide_width() + (i - 1) * dim.getBlock_size());
            // System.out.println(Math.abs(diff));
            if (Math.abs(diff) < lowest) {
                lowest = Math.abs(diff);
                column = i;
            }
        }
        System.out.printf("index: %d%n", column);
        return column;
    }

    public int getRowNum(int y) {
        int row = 0;
        int lowest = 10000;
        for (int i = 0; i < 20; i++){
            int diff = y - dim.getTop_height() - (i) * dim.getBlock_size();
            if (Math.abs(diff) < lowest) {
                lowest = Math.abs(diff);
                row = i;
            }
        }
        return row;
    }

    public Map<String, tetrimino> getMap(){
        return map;
    }

    public Boolean checkBound(int x, int y) {
        int index = y/dim.getBlock_size() - 2;
        String column = getColumn(x);
        return columns.get(column).get(index) ;
    }

    public void setBound(int x, int y) {
        int index = y/dim.getBlock_size() - 2;
        String column = getColumn(x);
        columns.get(column).set(index, true);
    }

    public int lowestPositionOffset(tetrimino tetrimino) {
        int lowest = dim.getTop_height() + dim.getPlaying_height();
        int shift = dim.getTop_height() + dim.getPlaying_height();
        for (int i = 0; i < 4; i++) {
            int nextDown = tetrimino.getBlockY(i) + dim.getBlock_size();
            while (!checkBound(tetrimino.getBlockX(i), nextDown)) {
                nextDown += dim.getBlock_size();
            }
            lowest = nextDown - dim.getBlock_size();
            if (lowest - tetrimino.getBlockY(i) < shift) {
                shift = lowest - tetrimino.getBlockY(i);
            }
        }
        return shift;
    }

    public tetrimino instantDrop (tetrimino tetrimino) {
        int offset = lowestPositionOffset(tetrimino);
        for (int i = 0; i < 4; i++) {
            tetrimino.setBlockY(i, tetrimino.getBlockY(i) + offset);
        }
        tetrimino.setY(tetrimino.getY() + offset);
        return tetrimino;
    }

    public Map<Integer, ArrayList<Integer>> predictedInstantDrop (tetrimino tetrimino) {
        Map<Integer, ArrayList<Integer>> coords = new HashMap<Integer, ArrayList<Integer>>();
        int offset = lowestPositionOffset(tetrimino);
        for (int i = 0; i < 4 ; i++) {
            ArrayList<Integer> blockCoords = new ArrayList<Integer>();
            blockCoords.add(tetrimino.getBlockX(i));
            blockCoords.add(tetrimino.getBlockY(i) + offset);
            coords.put(i, blockCoords);
        }
        return coords;
    }

    public Map<String, tetrimino> getMap(Map<String, tetrimino> theMap) {
        return theMap;
    }

    public void clearline(int row) {
        for (ArrayList<Boolean> value : columns.values()) {
            value.remove(row);
            value.add(0, false);
        }
        for (tetrimino tetrimino : map.values()) {
            for (int i = 0; i < 4; i++) {
                if (tetrimino.getBlockY(i) == (row + 2) * dim.getBlock_size() && tetrimino.getBlockX(i) >= (dim.getSide_width() - dim.getBlock_size()) && tetrimino.getBlockX(i) <= (dim.getSide_width() + dim.playing_width)) {
                    tetrimino.setVisible(i, false);
                }
            }
        }
        for (tetrimino tetrimino : map.values()) {
            for (int i = 0; i < 4; i++) {
                if (tetrimino.getBlockY(i) < (row + 2) * dim.getBlock_size() && tetrimino.getBlockX(i) >= (dim.getSide_width() - dim.getBlock_size()) && tetrimino.getBlockX(i) <= (dim.getSide_width() + dim.playing_width)) {
                    tetrimino.setBlockY(i, tetrimino.getBlockY(i) + dim.getBlock_size());
                }
            }
        }
    }

    public void draw(Graphics g, Map<Integer, ArrayList<Integer>> blocks) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();
        for (int i = 0; i < 4; i++) {
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            g1.drawRect(blocks.get(i).get(0), blocks.get(i).get(1), dim.getBlock_size(), dim.getBlock_size());
        }
    }
}
