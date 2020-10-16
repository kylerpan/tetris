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
        app_height = block_size * 24; // 930
        app_width = block_size * 22; // 900
        playing_height = block_size * 20; // 800
        playing_width = block_size * 10; // 400
        side_width = block_size * 6;
        top_height = block_size * 2; 
        hold_next_side = block_size * 4; // 160
    }

    public void update(boolean plus){
        if (plus) {
            app_height += 24;
            app_width += 22;
        } else {
            app_height -= 24;
            app_width -= 22;
        }
        block_size = app_height / 24;
        playing_height = block_size * 20;
        playing_width = block_size * 10;
        side_width = block_size * 6;
        top_height = block_size * 2; 
        hold_next_side = block_size * 4;
    }

    public void otherUpdate(boolean plus, int dimApp_height, int dimApp_width){
        app_height = dimApp_height;
        app_width = dimApp_width;
        block_size = app_height / 24;
        playing_height = block_size * 20;
        playing_width = block_size * 10;
        side_width = block_size * 6;
        top_height = block_size * 2; 
        hold_next_side = block_size * 4;
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

    public int getTop_height() {
        return top_height;
    }

    public int getSide_width() {
        return side_width;
    }

    public int getHold_next_side() {
        return hold_next_side;
    }

    public int getBlock_size() {
        return block_size;
    }

    public void setBlock_size(int newBlock_size) {
        block_size = newBlock_size;
    }

    public void setSide_width(int newSide_width) {
        side_width = newSide_width;
    }

    public void setTop_height(int newTop_height) {
        top_height = newTop_height;
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
