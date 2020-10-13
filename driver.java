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

	// holding
	boolean hold = false;
	tetrimino holdPiece;

	// pause
	boolean pause = false;

	// grid checks
	gridCheck gridCheck = new gridCheck();

	// game dimensions 
	gameDimensions dim = new gameDimensions();
	
	// real time dimensions
	int rt_app_height = dim.getApp_height();
	int rt_app_width = dim.getApp_width();

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
		
		// controls
		g.drawString("\u2191", dim.getBlock_size() * 1, dim.getBlock_size() * 10);
		g.drawString("- rotate right", dim.getBlock_size() * 2, dim.getBlock_size() * 10);
		g.drawString("\u2192", dim.getBlock_size() * 1, dim.getBlock_size() * 21 / 2);
		g.drawString("- move right", dim.getBlock_size() * 2, dim.getBlock_size() * 21 / 2);
		g.drawString("\u2193", dim.getBlock_size() * 1, dim.getBlock_size() * 11);
		g.drawString("- soft drop", dim.getBlock_size() * 2, dim.getBlock_size() * 11);
		g.drawString("\u2190", dim.getBlock_size() * 1, dim.getBlock_size() * 23 / 2);
		g.drawString("- move left", dim.getBlock_size() * 2, dim.getBlock_size() * 23 / 2);
		g.drawString("\u2190", dim.getBlock_size() * 1, dim.getBlock_size() * 23 / 2);
		g.drawString("- move left", dim.getBlock_size() * 2, dim.getBlock_size() * 23 / 2);
		g.drawString("z", dim.getBlock_size() * 1, dim.getBlock_size() * 25 / 2);
		g.drawString("- rotate left", dim.getBlock_size() * 2, dim.getBlock_size() * 25 / 2);
		g.drawString("c", dim.getBlock_size() * 1, dim.getBlock_size() * 13);
		g.drawString("- hold", dim.getBlock_size() * 2, dim.getBlock_size() * 13);
		g.drawString("esc", dim.getBlock_size() * 1, dim.getBlock_size() * 27 / 2);
		g.drawString("- pause", dim.getBlock_size() * 2, dim.getBlock_size() * 27 / 2);
		g.drawString("⎵", dim.getBlock_size() * 1, dim.getBlock_size() * 12);
		g.drawString("- hard drop", dim.getBlock_size() * 2, dim.getBlock_size() * 12);

        // strings
		Font font = new Font ("Arya", 1, 30);
        g.setFont(font);
        String scoreTitle = String.format("SCORE: %d", score);
		g.drawString(scoreTitle, dim.getBlock_size() * 11 - g.getFontMetrics().stringWidth(scoreTitle)/2, dim.getBlock_size() * 3 / 2); // score
		g.drawString("HOLD", dim.getBlock_size() * 3 - g.getFontMetrics().stringWidth("HOLD")/2, dim.getBlock_size() * 5 / 2);
		g.drawString("NEXT", dim.getBlock_size() * 19 - g.getFontMetrics().stringWidth("NEXT")/2, dim.getBlock_size() * 5 / 2);
        
        // backgrounds
        g.setColor(Color.white);
		g.fillRect(dim.getSide_width(), dim.getTop_height(), dim.getPlaying_width(), dim.getPlaying_height()); // playing
		g.fillRect(dim.getBlock_size(), dim.getBlock_size() * 3, dim.getHold_next_side(), dim.getHold_next_side()); // hold
		g.fillRect(dim.getSide_width() + dim.getBlock_size() * 11, dim.getBlock_size() * 3, dim.getHold_next_side(), dim.getHold_next_side()); // next

        // playing vertical & horizontal grid lines
        g1.setStroke(new java.awt.BasicStroke(2));
		g1.setColor(Color.lightGray);
		for(int i = dim.getBlock_size(); i < dim.getPlaying_width(); i += dim.getBlock_size()){
            g1.drawLine(dim.getSide_width() + i, dim.getTop_height(), dim.getSide_width() + i, dim.getTop_height() + dim.getPlaying_height());
        }
        for(int j = dim.getBlock_size(); j < dim.getPlaying_height(); j += dim.getBlock_size()){
            g1.drawLine(dim.getSide_width(), dim.getTop_height() + j, dim.getSide_width() + dim.getPlaying_width(), dim.getTop_height() + j);
		}

		// predicted position
		// for (tetrimino value : gridCheck.getMap().values()) {
		// 	if (value.getMoving()) {
		// 		Map<Integer, ArrayList<Integer>> blocks = gridCheck.predictedInstantDrop(value);
		// 		gridCheck.draw(g, blocks);
		// 	}
		// }
		
		// tetriminos
		for (tetrimino value : gridCheck.getMap().values()) {
			value.draw(g);
		}

		// next piece
		next.setX(dim.getBlock_size() * 19 - (next.getRight() - next.getLeft()) / 2);
		next.setY(dim.getBlock_size() * 5 - (next.getBottom() - next.getTop()) / 2);
		next.draw(g);
        
        // border lines
        g1.setStroke(new java.awt.BasicStroke(3));
        g1.setColor(Color.black);
		g1.drawRect(dim.getSide_width(), dim.getTop_height(), dim.getPlaying_width(), dim.getPlaying_height()); // playing	
		g1.drawRect(dim.getBlock_size(), dim.getBlock_size() * 3, dim.getHold_next_side(), dim.getHold_next_side()); // hold
		g1.drawRect(dim.getBlock_size() * 17, dim.getBlock_size() * 3, dim.getHold_next_side(), dim.getHold_next_side()); // next

		if (pause) {
			g.setColor(new Color(0, 0, 0, 57));
			g.fillRect(0, 0, dim.app_width, dim.app_height);

			g.setColor(new Color(240, 240, 240));
			g.fillRoundRect(dim.getBlock_size() * 13 / 2, dim.getBlock_size() * 7 / 2, dim.getBlock_size() * 9, dim.getBlock_size() * 17, 15, 15);
			g1.drawRoundRect(dim.getBlock_size() * 13 / 2, dim.getBlock_size() * 7 / 2, dim.getBlock_size() * 9, dim.getBlock_size() * 17, 15, 15);

			g1.setStroke(new java.awt.BasicStroke(2));
			g1.setColor(Color.black);
			g.fillRoundRect(dim.getBlock_size() * 15 / 2, dim.getBlock_size() * 10, dim.getBlock_size() * 7, dim.getBlock_size() * 5 / 4, 15, 15);
			g1.drawRoundRect(dim.getBlock_size() * 15 / 2, dim.getBlock_size() * 10, dim.getBlock_size() * 7, dim.getBlock_size() * 5 / 4, 15, 15);
			g.fillRoundRect(dim.getBlock_size() * 15 / 2, dim.getBlock_size() * 8, dim.getBlock_size() * 2, dim.getBlock_size() * 5 / 4, 15, 15);
			g1.drawRoundRect(dim.getBlock_size() * 15 / 2, dim.getBlock_size() * 8, dim.getBlock_size() * 2, dim.getBlock_size() * 5 / 4, 15, 15);
			g.fillRoundRect(dim.getBlock_size() * 25 / 2, dim.getBlock_size() * 8, dim.getBlock_size() * 2, dim.getBlock_size() * 5 / 4, 15, 15);
			g1.drawRoundRect(dim.getBlock_size() * 25 / 2, dim.getBlock_size() * 8, dim.getBlock_size() * 2, dim.getBlock_size() * 5 / 4, 15, 15);
			g.setColor(new Color(43, 127, 25));
			g.fillRoundRect(dim.getBlock_size() * 15 / 2, dim.getBlock_size() * 6, dim.getBlock_size() * 7, dim.getBlock_size() * 5 / 4, 15, 15);
			g1.drawRoundRect(dim.getBlock_size() * 15 / 2, dim.getBlock_size() * 6, dim.getBlock_size() * 7, dim.getBlock_size() * 5 / 4, 15, 15);

			g.setColor(Color.black);
        	g.setFont(new Font ("Arya", 1, 25));
			g.drawString("esc - resume", dim.getBlock_size() * 11 - g.getFontMetrics().stringWidth("esc - resume")/2, dim.getBlock_size() * 27 / 4);
			g.drawString("-", dim.getBlock_size() * 17 / 2 - g.getFontMetrics().stringWidth("-")/2, dim.getBlock_size() * 35 / 4);
			g.drawString("size", dim.getBlock_size() * 11 - g.getFontMetrics().stringWidth("size")/2, dim.getBlock_size() * 35 / 4);
			g.drawString("+", dim.getBlock_size() * 27 / 2 - g.getFontMetrics().stringWidth("+")/2, dim.getBlock_size() * 35 / 4);
			g.drawString("q - restart", dim.getBlock_size() * 11 - g.getFontMetrics().stringWidth("q - restart")/2, dim.getBlock_size() * 43 / 4);

			g.setFont(new Font ("Arya", 1, 40));
			g.setColor(Color.black);
			g.drawString("PAUSED", dim.getBlock_size() * 11 - g.getFontMetrics().stringWidth("PAUSED")/2, dim.getBlock_size() * 5);

		}

	}// end of paint
	
	// Update Stuff 
	public void update() {

		if (!pause) {
			
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

					// game bounds
					if (value.getRight() >= dim.getSide_width() + dim.getPlaying_width()) {
						if (value.getRight() > dim.getSide_width() + dim.getPlaying_width()) {
							int shift = value.getRight() - (dim.getSide_width() + dim.getPlaying_width());
							value.setX(value.getX() - shift);
						}
						value.setRbound(true);
					}

					if (value.getLeft() <= dim.getSide_width()) {
						if (value.getLeft() < dim.getSide_width()) {
							int shift = dim.getSide_width() - value.getLeft();
							value.setX(value.getX() + shift);
						}
						value.setLbound(true);
					}

					if (value.getBottom() >= dim.getTop_height() + dim.getPlaying_height()) {
						if (value.getBottom() > dim.getTop_height() + dim.getPlaying_height()) {
							int shift = value.getBottom() - (dim.getTop_height() + dim.getPlaying_height());
							value.setY(value.getY() - shift);
						}
						value.setDbound(true);
					}

					if (value.getTop() <= dim.getTop_height()) {
						if (value.getTop() < dim.getTop_height()) {
							int shift =  dim.getTop_height() - value.getTop();
							value.setY(value.getY() + shift);
						}
						value.setTbound(true);
					}

					// block bounds
					for (int i = 0; i < 4; i++) {
						boolean RBound = gridCheck.checkBound(value.getBlockX(i) + dim.getBlock_size(), value.getBlockY(i));
						boolean LBound = gridCheck.checkBound(value.getBlockX(i) - dim.getBlock_size(), value.getBlockY(i));
						boolean DBound = gridCheck.checkBound(value.getBlockX(i), value.getBlockY(i) + dim.getBlock_size());
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
							hold = false;
							break;
						}
						value.setY(value.getY() + dim.getBlock_size());
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
		}

	}// end of update
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
	
	public static void main(String[] arg) {
		driver d = new driver();
	}
	public driver() {
		f.setTitle("Tetris");
		f.setSize(dim.getApp_width(), dim.getApp_height());
		f.setResizable(true);
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
		if (arg0.getKeyCode() == 39 && !pause) { 
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving() && !value.getRbound()) {
					value.moveRight();
				}
			}
		} 

		// left
		if (arg0.getKeyCode() == 37 && !pause) {
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving() && !value.getLbound()) {
					value.moveLeft();
				}
			}
		} 

		// down
		if (arg0.getKeyCode() == 40 && !pause) {
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving() && !value.getDbound()) {
					value.moveDown();
				}
			}
		} 
		
		// up
		if (arg0.getKeyCode() == 38 && !pause) {
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
		if (arg0.getKeyCode() == 32 && !pause) {
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving()) {
					gridCheck.instantDrop(value);
					value.setMoving(false);
					hold = false;
					for (int j = 0; j < 4; j++) {
						gridCheck.setBound(value.getBlockX(j), value.getBlockY(j));
					}
				}
			}
		} 
		
		// z 
		if (arg0.getKeyCode() == 90 && !pause) {
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

		// c 
		if (arg0.getKeyCode() == 67 && !pause) {
			for (tetrimino value : gridCheck.getMap().values()) {
				if (value.getMoving()) {
					if (!hold) {
						if (holdPiece != null) {
							char previous = value.getType();
							value.setType(holdPiece.getType());
							value.setX(dim.getSide_width() + dim.getBlock_size() * 4);
							value.setY(dim.getTop_height());
							holdPiece.setType(previous);
							holdPiece.setOrientation(1);
							holdPiece.setX(dim.getBlock_size() * 3 - (holdPiece.getRight() - holdPiece.getLeft()) / 2);
							holdPiece.setY(dim.getBlock_size() * 5 - (holdPiece.getBottom() - holdPiece.getTop()) / 2);
						} else {
							holdPiece = value;
							holdPiece.setOrientation(1);
							holdPiece.setX(dim.getBlock_size() * 3 - (holdPiece.getRight() - holdPiece.getLeft()) / 2);
							holdPiece.setY(dim.getBlock_size() * 5 - (holdPiece.getBottom() - holdPiece.getTop()) / 2);
							value.setMoving(false);
						}
						hold = true;
					}
				}
			}
		} 

		// escape
		if (arg0.getKeyCode() == 27) {
			if (pause) pause = false;
			else pause = true;
		} 

		// minus
		if (arg0.getKeyCode() == 45 && pause) { 
			dim.minusAppDim();
			dim.update(false, gridCheck, gridCheck.getMap().values());
			repaint();
			f.setSize(dim.getApp_width(), dim.getApp_height());
		}

		// plus
		if (arg0.getKeyCode() == 61 && pause) { 
			dim.plusAppDim();
			dim.update(true, gridCheck, gridCheck.getMap().values());
			repaint();
			f.setSize(dim.getApp_width(), dim.getApp_height());
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

	
}









