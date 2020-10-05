import java.awt.Color;
import java.awt.Graphics;
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

    public void update(tetrimino moving, int newSide_width, int newBlock_size, int newPlaying_height) {
        dim.side_width = newSide_width;
        dim.block_size = newBlock_size;
        dim.playing_height = newPlaying_height;
        // lowestPosition(moving);
    }

    public Map<String, ArrayList<Boolean>> getColumns() {
        return columns;
    }

    public String getColumn(int x) {
        String column = "";
        for (int i = 0; i < 12; i++){
            if (x == dim.side_width + (i - 1) * dim.block_size) {
                column = String.format("column%d", i);
                break;
            }
        }
        return column;
    }

    public Map<String, tetrimino> getMap(){
        return map;
    }

    public Boolean checkBound(int x, int y) {
        int index = y/dim.block_size - 2;
        System.out.println(x);
        String column = getColumn(x);
        return columns.get(column).get(index) ;
    }

    public void setBound(int x, int y) {
        int index = y/dim.block_size - 2;
        String column = getColumn(x);
        columns.get(column).set(index, true);
    }

    public Map<String, ArrayList<Integer>> lowestPosition(tetrimino tetrimino) {

        // all block positions
        for (int i = 0; i < 4; i++) {
            String name = String.format("block%d", i + 1);
            ArrayList<Integer> coords = new ArrayList<Integer>();
            coords.add(tetrimino.getBlockX(i));
            coords.add(tetrimino.getBlockY(i));
            blocksMap.put(name, coords);
        }

        // setting lowest position 
        int lowest = 10000000;
        int shift = 10000000;
        for (ArrayList<Integer> value: blocksMap.values()) {
            int nextDown = value.get(1);
            while (!checkBound(value.get(0), nextDown)) {
                nextDown += dim.block_size;
                if (nextDown >= (dim.side_width - dim.block_size) + dim.playing_height) break;
            }
            lowest = nextDown - dim.block_size;
            if (lowest - value.get(1) < shift) {
                shift = lowest - value.get(1);
            }
        }

        for (ArrayList<Integer> value: blocksMap.values()) {
            value.set(1, value.get(1) + shift);
        }

        return blocksMap;
    }

    public tetrimino instantDrop(tetrimino tetrimino) {
        Map<String, ArrayList<Integer>> blocks = lowestPosition(tetrimino);
        int index = 0;
        int offset = 0;
        for (ArrayList<Integer> value: blocks.values()) {
            offset = value.get(1) - tetrimino.getBlockY(index);
            tetrimino.setBlockY(index, value.get(1));
            index++;
        }
        tetrimino.setY(tetrimino.getY() + offset);
        return tetrimino;
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
                if (tetrimino.getBlockY(i) == (row + 2) * dim.block_size && tetrimino.getBlockX(i) >= (dim.side_width - dim.block_size) && tetrimino.getBlockX(i) <= (dim.side_width + dim.playing_width)) {
                    tetrimino.setVisible(i, false);
                }
            }
        }
        for (tetrimino tetrimino : map.values()) {
            for (int i = 0; i < 4; i++) {
                if (tetrimino.getBlockY(i) < (row + 2) * dim.block_size && tetrimino.getBlockX(i) >= (dim.side_width - dim.block_size) && tetrimino.getBlockX(i) <= (dim.side_width + dim.playing_width)) {
                    tetrimino.setBlockY(i, tetrimino.getBlockY(i) + dim.block_size);
                }
            }
        }
    }

    public void draw(Graphics g, Map<String, ArrayList<Integer>> blocksMap) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        for (ArrayList<Integer> coords: blocksMap.values()){
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            g1.drawRect(coords.get(0), coords.get(1), dim.block_size, dim.block_size);
        }
    }
}
