import java.awt.Dimension;
import java.awt.Toolkit;

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
        // screen_width = screenSize.width;
        block_size = screen_height * 2 / 69; // 40
        app_height = block_size * 24; // 930
        app_width = block_size * 22; // 900
        playing_height = block_size * 20; // 800
        playing_width = block_size * 10; // 400
        side_width = block_size * 6;
        top_height = block_size * 2; 
        hold_next_side = block_size * 4; // 160
    }

    // public void update(int block_size, int app_height, int app_width) {
    //     this.block_size = block_size;
    //     this.app_height = app_height;
    //     this.app_width = app_width;
    //     playing_height = block_size * 20;
    //     playing_width = block_size * 10;
    //     side_width = block_size * 6;
    //     top_height = block_size * 2; 
    //     hold_next_side = block_size * 4;
    // }

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

    public int getplaying_width() {
        return playing_width;
    }

    public int getHold_next_side() {
        return hold_next_side;
    }

    public int getBlock_size() {
        return block_size;
    }
}
