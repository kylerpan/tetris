import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class gridCheck {
    
    Map<String, ArrayList<Boolean>> columns = new HashMap<String, ArrayList<Boolean>>();

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

    public Boolean checkRBound(int fx, int y) {
        int index = y/40 - 2;
        String column = getColumn(fx);
        return columns.get(column).get(index) ;
    }

    public Boolean checkLBound(int fx, int y) {
        int index = y/40 - 2;
        String column = getColumn(fx);
        return columns.get(column).get(index) ;
    }

    public Boolean checkDBound(int x, int fy) {
        int index = fy/40 - 2;
        String column = getColumn(x);
        return columns.get(column).get(index) ;
    }

    public void setBound(int x, int y) {
        int index = y/40 - 2;
        String column = getColumn(x);
        columns.get(column).set(index, true) ;
    }

    
}
