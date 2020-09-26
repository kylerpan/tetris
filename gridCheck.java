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

        // setting lowest position 
        int lowest = 1000;
        int shift = 1000;
        for (ArrayList<Integer> value: blocksMap.values()) {
            int nextDown = value.get(1);
            while (!checkBound(value.get(0), nextDown)) {
                nextDown += 40;
                if (nextDown >= 880) break;
            }
            lowest = nextDown - 40;
            if (lowest - value.get(1) < shift) {
                shift = lowest - value.get(1);
            }
        }

        for (ArrayList<Integer> value: blocksMap.values()) {
            value.set(1, value.get(1) + shift);
        }

        return blocksMap;
    }


    public void draw(Graphics g, Map<String, ArrayList<Integer>> blocksMap) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        for (ArrayList<Integer> coords: blocksMap.values()){
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            g1.drawRect(coords.get(0), coords.get(1), 40, 40);
        }
    }
}
