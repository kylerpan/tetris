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

    public void update(tetrimino moving, int newTop_height, int newSide_width, int newBlock_size, int newPlaying_height) {
        dim.top_height = newTop_height;
        dim.side_width = newSide_width;
        dim.block_size = newBlock_size;
        dim.playing_height = newPlaying_height;
        ArrayList<Integer> columnList = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++){
            columnList.add(dim.top_height + i * dim.block_size);
		}
		System.out.println(columnList);
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

    public int getColumnNum(int x) {
        int column = 0;
        for (int i = 0; i < 12; i++){
            if (x == dim.side_width + (i - 1) * dim.block_size) {
                column = i;
                // System.out.println(column);
                break;
            }
        }
        return column;
    }

    public int getRowNum(int y) {
        int row = 0;
        for (int i = 0; i < 20; i++){
            if (y == dim.top_height + i * dim.block_size) {
                row = i;
                System.out.println(row);
                break;
            }
        }
        return row;
    }

    public Map<String, tetrimino> getMap(){
        return map;
    }

    public Boolean checkBound(int x, int y) {
        int index = y/dim.block_size - 2;
        String column = getColumn(x);
        return columns.get(column).get(index) ;
    }

    public void setBound(int x, int y) {
        int index = y/dim.block_size - 2;
        String column = getColumn(x);
        columns.get(column).set(index, true);
    }

    // public Map<String, ArrayList<Integer>> lowestPosition(tetrimino tetrimino) {

    //     // all block positions
    //     for (int i = 0; i < 4; i++) {
    //         String name = String.format("block%d", i + 1);
    //         ArrayList<Integer> coords = new ArrayList<Integer>();
    //         coords.add(tetrimino.getBlockX(i));
    //         coords.add(tetrimino.getBlockY(i));
    //         blocksMap.put(name, coords);
    //     }

    //     // setting lowest position 
    //     int lowest = 10000000;
    //     int shift = 10000000;
    //     for (ArrayList<Integer> value: blocksMap.values()) {
    //         int nextDown = value.get(1);
    //         while (!checkBound(value.get(0), nextDown)) {
    //             nextDown += dim.block_size;
    //             if (nextDown >= (dim.side_width - dim.block_size) + dim.playing_height) break;
    //         }
    //         lowest = nextDown - dim.block_size;
    //         if (lowest - value.get(1) < shift) {
    //             shift = lowest - value.get(1);
    //         }
    //     }

    //     for (ArrayList<Integer> value: blocksMap.values()) {
    //         value.set(1, value.get(1) + shift);
    //     }

    //     return blocksMap;
    // }

    // public tetrimino instantDrop(tetrimino tetrimino) {
    //     Map<String, ArrayList<Integer>> blocks = lowestPosition(tetrimino);
    //     int index = 0;
    //     int offset = 0;
    //     for (ArrayList<Integer> value: blocks.values()) {
    //         offset = value.get(1) - tetrimino.getBlockY(index);
    //         tetrimino.setBlockY(index, value.get(1));
    //         index++;
    //     }
    //     tetrimino.setY(tetrimino.getY() + offset);
    //     return tetrimino;
    // }

    public int lowestPositionOffset(tetrimino tetrimino) {
        int lowest = dim.top_height + dim.block_size * 20;
        for (int i = 0; i < 4; i++) {
            String column = getColumn(tetrimino.getBlockX(i));
            int index = 0;
            for (boolean taken : columns.get(column)) {
                if (taken && (index * dim.block_size + dim.top_height) < lowest) {
                    lowest = index * dim.block_size + dim.top_height;
                } else {
                    index++;
                }
            }
        }
        return lowest - tetrimino.getBottom();
    }

    public tetrimino instantDrop (tetrimino tetrimino) {
        int offset = lowestPositionOffset(tetrimino);
        for (int i = 0; i < 4; i++) {
            tetrimino.setBlockY(i, tetrimino.getBlockY(i) + offset);
        }
        tetrimino.setTop(tetrimino.getTop() + offset);
        return tetrimino;
    }

    public tetrimino predictedInstantDrop (tetrimino tetrimino) {
        tetrimino predicted = tetrimino;
        int offset = lowestPositionOffset(predicted);
        for (int i = 0; i < 4; i++) {
            predicted.setBlockY(i, predicted.getBlockY(i) + offset);
        }
        predicted.setTop(predicted.getTop() + offset);
        return predicted;
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

    public void draw(Graphics g, tetrimino blocks) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        for (int i = 0; i < 4; i++) {
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            g1.drawRect(blocks.getBlockX(i), blocks.getBlockY(i), dim.block_size, dim.block_size);
        }
    }
}
