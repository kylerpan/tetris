import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class gridCheck {
    
    Map<String, ArrayList<Boolean>> columns = new HashMap<String, ArrayList<Boolean>>();
    block block1 = new block();
    block block2 = new block();
    block block3 = new block();
    block block4 = new block();
    block[] blocks = new block[]{block1, block2, block3, block4};
    Map<String, ArrayList<Integer>> blocksMap = new HashMap<String, ArrayList<Integer>>();

    public gridCheck(){
        for (int i = 0; i < 12; i++) {
            String column = String.format("column%d", i);
            ArrayList<Boolean> check = new ArrayList<Boolean>();
            if (i == 0 || i == 11) {
                for (int j = 0; j < 21; j++) {
                    if (j == 20) {
                        check.add(true);
                    } else {
                        check.add(true);
                    }
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

    public String getColumn(int x) {
        String column = "";
        for (int i = 0; i < 12; i++){
            if (x == 210 + i * 40) {
                column = String.format("column%d", i);
                break;
            }
        }
        return column;
    }

    public Boolean checkBound(int x, int y) {
        int index = y/40 - 2;
        String column = getColumn(x);
        return columns.get(column).get(index) ;
    }

    public void setBound(int x, int y) {
        int index = y/40 - 2;
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

        // getting lowest position
        int lowest = 1000;
        int shift = 0;
        for (int i = 0; i < 4; i++) {
            String column = getColumn(tetrimino.getBlockX(i));
            String block = String.format("block%d", i + 1);
            for (int j = 0; j < 21; j++) {
                if (columns.get(column).get(j)) {
                    System.out.printf("lowest: %d%n", lowest);
                    // System.out.printf("current: %d%n", (j + 1) * 40);
                    if((j + 1) * 40 < lowest) {
                        lowest = (j + 1) * 40;
                        System.out.printf("new lowest: %d%n", lowest);
                    }
                    shift = lowest - blocksMap.get(block).get(1);
                }
            }
            
        }

        // setting lowest position 
        for (ArrayList<Integer> value: blocksMap.values()) {
            value.set(1, value.get(1) + shift);
            if (value.get(1) > 840) {
                int shift2 = value.get(1) - 840;
                for (ArrayList<Integer> value2: blocksMap.values()) {
                    value2.set(1, value2.get(1) - shift2);
                }
            }
        }

        return blocksMap;
    }

    // public void refreshPosition(Map<String, ArrayList<Integer>> blocks) {

    //     // all block positions
    //     for (int i = 0; i < 4; i++) {
    //         String name = String.format("block%d", i + 1);
    //         ArrayList<Integer> coords = new ArrayList<Integer>();
    //         coords.set(0, blocks.get(name).get(0));
    //         coords.set(1, blocks.get(name).get(0));
    //     }
    // }

    public void draw(Graphics g, Map<String, ArrayList<Integer>> blocksMap) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        for (ArrayList<Integer> coords: blocksMap.values()){
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            // System.out.printf("%d, %d%n", coords.get(0), coords.get(1));
            g1.drawRect(coords.get(0), coords.get(1), 40, 40);
        }
    }
}
