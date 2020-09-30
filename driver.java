import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class driver extends JPanel implements ActionListener, KeyListener {

	// jframe
	JFrame f = new JFrame();
    
	// score
	int previous = 0;
	int score = 0;

	// grid checks
	gridCheck gridCheck = new gridCheck();

	// game dimensions 
	gameDimensions dim = new gameDimensions();
	
	// real time dimensions
	int rt_app_height = dim.app_height;
	int rt_app_width = dim.app_width;

	// fall interval
	int interval = 10;
	
	// new tetriminos
	int counter = 0;
	tetrimino next;
	ArrayList<Integer> bag = new ArrayList<Integer>();
	Set<String> coordinates = new HashSet<String>();
	public void newTetrimino() {
		char newBlock;
		if (bag.size() == 0) {
			for (int i = 0; i < 7; i++) {
				bag.add(i);
			}
			Collections.shuffle(bag);
		} 
		int num = bag.get(0);
		bag.remove(0);
		if (bag.size() == 0) {
			for (int i = 0; i < 7; i++) {
				bag.add(i);
			}
			Collections.shuffle(bag);
		}
		int nextNum = bag.get(0);
		char nextBlock;

		if (num == 0) newBlock = 'I'; 
		else if (num == 1) newBlock = 'O'; 
		else if (num == 2) newBlock = 'T'; 
		else if (num == 3) newBlock = 'J'; 
		else if (num == 4) newBlock = 'L'; 
		else if (num == 5) newBlock = 'S'; 
		else newBlock = 'Z';

		if (nextNum == 0) nextBlock = 'I'; 
		else if (nextNum == 1) nextBlock = 'O'; 
		else if (nextNum == 2) nextBlock = 'T'; 
		else if (nextNum == 3) nextBlock = 'J'; 
		else if (nextNum == 4) nextBlock = 'L'; 
		else if (nextNum == 5) nextBlock = 'S'; 
		else nextBlock = 'Z';
		
		String name = String.format("tetrimino%d", counter);
		gridCheck.getMap().put(name, new tetrimino(newBlock, 1));
		counter++;
		next = new tetrimino(nextBlock, 1);
	}

	// getting time
	int lastTime;
	boolean down;
	private final long startTime = System.currentTimeMillis();
    public int getSeconds() {
		long time = System.currentTimeMillis();
        return (int)((time - startTime) / 100);
	}
	
	
	// Painting Stuff
	public void paint(Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        // Strings
		Font font = new Font ("Arya", 1, 30);
        g.setFont(font);
        String scoreTitle = String.format("SCORE: %d", score);
		g.drawString(scoreTitle, dim.block_size * 11 - g.getFontMetrics().stringWidth(scoreTitle)/2, dim.block_size * 3 / 2); // score
		g.drawString("HOLD", dim.block_size * 3 - g.getFontMetrics().stringWidth("HOLD")/2, dim.block_size * 5 / 2);
		g.drawString("NEXT", dim.block_size * 19 - g.getFontMetrics().stringWidth("NEXT")/2, dim.block_size * 5 / 2);
        
        // backgrounds
        g.setColor(Color.white);
		g.fillRect(dim.side_width, dim.top_height, dim.playing_width, dim.playing_height); // playing
		g.fillRect(dim.block_size, dim.block_size * 3, dim.hold_next_side, dim.hold_next_side); // hold
		g.fillRect(dim.side_width + dim.block_size * 11, dim.block_size * 3, dim.hold_next_side, dim.hold_next_side); // next

        // playing vertical & horizontal grid lines
        g1.setStroke(new java.awt.BasicStroke(2));
		g1.setColor(Color.lightGray);
		for(int i = dim.block_size; i < dim.playing_width; i += dim.block_size){
            g1.drawLine(dim.side_width + i, dim.top_height, dim.side_width + i, dim.top_height + dim.playing_height);
        }
        for(int j = dim.block_size; j < dim.playing_height; j += dim.block_size){
            g1.drawLine(dim.side_width, dim.top_height + j, dim.side_width + dim.playing_width, dim.top_height + j);
		}

		// predicted position
		for (tetrimino value : gridCheck.getMap().values()) {
			if (value.getMoving()) {
				Map<String, ArrayList<Integer>> blocks = gridCheck.lowestPosition(value);
				gridCheck.draw(g, blocks);
			}
		}
		
		// tetriminos
		for (tetrimino value : gridCheck.getMap().values()) {
			value.draw(g);
		}

		// next piece
		next.setX(dim.block_size * 19 - next.getW() / 2);
		next.setY(dim.block_size * 5 - next.getH() / 2);
		next.draw(g);
        
        // border lines
        g1.setStroke(new java.awt.BasicStroke(3));
        g1.setColor(Color.black);
		g1.drawRect(dim.side_width, dim.top_height, dim.playing_width, dim.playing_height); // playing	
		g1.drawRect(dim.block_size, dim.block_size * 3, dim.hold_next_side, dim.hold_next_side); // hold
		g1.drawRect(dim.block_size * 17, dim.block_size * 3, dim.hold_next_side, dim.hold_next_side); // next

	}// end of paint
	
	// Update Stuff 
	public void update() {
		// System.out.println(f.getContentPane().getHeight());
		// System.out.println(f.getContentPane().getWidth());
		// System.out.println(dim.app_height);
		// System.out.println(dim.app_width);
		// rt_app_width = f.getContentPane().getWidth();
		// rt_app_height = rt_app_width * 24 / 22;
		// if (dim.app_width != rt_app_width && rt_app_width != 0) {
		// 	dim.update(rt_app_width / 22, rt_app_height, rt_app_width);
		// }

		// first tetrimino
		if (gridCheck.getMap().size() == 0) {
			newTetrimino();
		}

		// bounds
		for (tetrimino value : gridCheck.getMap().values()) {
			if (value.getMoving()) {
				value.setRbound(false);
				value.setLbound(false);
				value.setDbound(false);
				for (int i = 0; i < 4; i++) {
					int FXRblockCoords = value.getBlockX(i) + dim.block_size;
					int FXLblockCoords = value.getBlockX(i) - dim.block_size;
					int FYDblockCoords = value.getBlockY(i) + dim.block_size;
					int FYTblockCoords = value.getBlockY(i) - dim.block_size;

					// game bounds
					if (FXRblockCoords > dim.side_width + dim.playing_width) {
						int shift = FXRblockCoords - (dim.side_width + dim.playing_width);
						value.setX(value.getX() - shift);
						value.setRbound(true);
					}

					if (FXLblockCoords < dim.side_width - dim.block_size) {
						int shift = dim.side_width - dim.block_size - FXLblockCoords;
						value.setX(value.getX() + shift);
						value.setLbound(true);
					}

					if (FYDblockCoords > dim.top_height + dim.playing_height) {
						int shift = FYDblockCoords - (dim.top_height + dim.playing_height);
						value.setY(value.getY() - shift);
						value.setDbound(true);
					}

					if (FYTblockCoords < dim.top_height + dim.block_size) {
						int shift = dim.top_height - FYTblockCoords;
						value.setY(value.getY() + shift);
					}

					// block bounds
					boolean RBound = gridCheck.checkBound(value.getBlockX(i) + dim.block_size, value.getBlockY(i));
					boolean LBound = gridCheck.checkBound(value.getBlockX(i) - dim.block_size, value.getBlockY(i));
					boolean DBound = gridCheck.checkBound(value.getBlockX(i), value.getBlockY(i) + dim.block_size);
					if (RBound) {
						value.setRbound(true);
					} 
					
					if (LBound) {
						value.setLbound(true);
					} 
					
					if (DBound) {
						value.setDbound(true);
					} 
				}
			}
		}
		
		// changing interval
		if (score != previous){
			if (score % 10 == 0 && score > 0 ) {
				if (interval == 0) {
					interval = 0;
				} else {
					interval -= 2;
				}
				previous = score;
			}
		}

		// falling
		if (lastTime - getSeconds() == 0) {
			down = false;
		} else if (getSeconds() % interval == 0) {
			down = true;
			lastTime = getSeconds();
		}
		if (down) {
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving()){
					if (value.getDbound()) {
						value.setY(value.getY());
						value.setMoving(false);
						for (int j = 0; j < 4; j++) {
							gridCheck.setBound(value.getBlockX(j), value.getBlockY(j));
						}
						break;
					}
					value.setY(value.getY() + dim.block_size);
				}
			}
			down = false;
		}

		// clearing 
		for (int i = 19; i >= 0; i--) {
			int counter = 0;
			for (int j = 1; j < 11; j++) {
				String column = String.format("column%d", j);
				if (gridCheck.getColumns().get(column).get(i)) {
					counter++;
				}
			}
			if (counter == 10) {
				gridCheck.clearline(i);
				score++;
			}
		}

		// new tetrimino
		int movingCounter = 0;
		for (tetrimino value : gridCheck.getMap().values()) {
			if (value.getMoving()) movingCounter++; 
		}
		if (movingCounter == 0) newTetrimino();


	}// end of update
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
	
	public static void main(String[] arg) {
		driver d = new driver();
	}
	public driver(){
		f.setTitle("Tetris");
		f.setSize(dim.app_width, dim.app_height);
		f.setBackground(Color.black);
		f.setResizable(false);
		f.addKeyListener(this);
		f.add(this);
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	Timer t;


	@Override
	public void keyPressed(KeyEvent arg0) {
		// right
		if(arg0.getKeyCode() == 39){ 
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving() && !value.getRbound()) {
					value.moveRight();
				}
			}
		} 

		// left
		if(arg0.getKeyCode() == 37){
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving() && !value.getLbound()) {
					value.moveLeft();
				}
			}
		} 

		// down
		if(arg0.getKeyCode() == 40){
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving() && !value.getDbound()) {
					value.moveDown();
				}
			}
		} 
		
		// up and x
		if(arg0.getKeyCode() == 38 || arg0.getKeyCode() == 88){
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving()) {
					if (value.getOrientation() == 4){
						value.setOrientation(1);
					} else {
						value.setOrientation(value.getOrientation() + 1);
					}
				}
			}
		} 
		
		// spacebar
		if(arg0.getKeyCode() == 32){
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving()) {
					gridCheck.instantDrop(value);
					value.setMoving(false);
					for (int j = 0; j < 4; j++) {
						gridCheck.setBound(value.getBlockX(j), value.getBlockY(j));
					}
				}
			}
		} 
		
		// z and ctrl
		if(arg0.getKeyCode() == 90 || arg0.getKeyCode() == 17){
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving()) {
					if (value.getOrientation() == 1){
						value.setOrientation(4);
					} else {
						value.setOrientation(value.getOrientation() - 1);
					}
				}
			}
		} 

		// c and shift
		if(arg0.getKeyCode() == 67 || arg0.getKeyCode() == 16){
			System.out.println("c or shift");
		} 

		// escape
		if(arg0.getKeyCode() == 27){
			System.out.println("escape");
		} 
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

	
}









