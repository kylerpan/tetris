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
    int old_block_size;

    public gameDimensions(){
        screen_height = screenSize.height;
        block_size = screen_height * 2 / 69; // 40
        old_block_size = screen_height * 2 / 69;
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
            int left = side_width + playing_width;
            int top = top_height + playing_height;
            for (int i = 0; i < 4; i++) {
                value.setBlockSize(block_size);
                if (value.getBlockX(i) < left) left = value.getBlockX(i);
                if (value.getBlockY(i) < top) top = value.getBlockY(i);
            }
            left = (gridCheck.getColumnNum(left) + 5) * block_size;
            top = (gridCheck.getRowNum(top) + 2) * block_size;
            value.setX(left);
            value.setY(top);
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
