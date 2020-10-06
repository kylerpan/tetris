import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Collection;

public class gameDimensions {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screen_height;
	int screen_width;
    int app_height;
    int app_width;
    int playing_height;
    int playing_width;
    int side_width;
    int top_height;
    int hold_next_side; 
    int block_size;

    public gameDimensions(){
        screen_height = screenSize.height;
        block_size = screen_height * 2 / 69; // 40
        app_height = block_size * 24; // 930
        app_width = block_size * 22; // 900
        playing_height = block_size * 20; // 800
        playing_width = block_size * 10; // 400
        side_width = block_size * 6;
        top_height = block_size * 2; 
        hold_next_side = block_size * 4; // 160
    }

    public void update(boolean plus, gridCheck gridCheck, Collection<tetrimino> tetriminos){
        block_size = app_height / 24;
        playing_height = block_size * 20;
        playing_width = block_size * 10;
        side_width = block_size * 6;
        top_height = block_size * 2; 
        hold_next_side = block_size * 4;
        for (tetrimino value : tetriminos) {
            System.out.println("ran");
            for (int i = 0; i < 4; i++) {
                int columnNum = gridCheck.getColumnNum(value.getBlockX(i));
                int rowNum = gridCheck.getRowNum(value.getBlockY(i));
                if (plus) {
                    value.setBlockX(i, value.getBlockX(i) + columnNum + 5);
                    value.setBlockY(i, value.getBlockY(i) + rowNum + 2);
                    // value.setX(value.getX() + columnNum + 5);
                    // value.setY(value.getY() + rowNum + 2);
                    System.out.println(value.getBlockY(i));
                } else {
                    value.setBlockX(i, value.getBlockX(i) - columnNum - 5);
                    value.setBlockY(i, value.getBlockY(i) - rowNum - 2);
                    // value.setX(value.getX() - columnNum - 5);
                    // value.setY(value.getY() - rowNum - 2);
                    System.out.println(value.getBlockY(i));
                }
                value.setBlockSize(block_size);
            }
        }
        gridCheck.update(null, top_height, side_width, block_size, playing_height);
    }

    public int getScreen_height() {
        return screen_height;
    }

    public int getScreen_width() {
        return screen_width;
    }

    public int getApp_height() {
        return app_height;
    }

    public int getApp_width() {
        return app_width;
    }

    public int getPlaying_height() {
        return playing_height;
    }

    public int getPlaying_width() {
        return playing_width;
    }

    public int getSideWidth() {
        return side_width;
    }

    public int getHold_next_side() {
        return hold_next_side;
    }

    public int getBlock_size() {
        return block_size;
    }

    public void minusAppDim() {
        app_height -= 24;
        app_width -= 22;
    }

    public void plusAppDim() {
        app_height += 24;
        app_width += 22;
    }
}
