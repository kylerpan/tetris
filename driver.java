import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class driver extends JPanel implements ActionListener, KeyListener {
    
    // dimensions
    int screen_height = 930; 
    int screen_width = 900; 
    int playing_height = 800;
	int playing_width = 400;
	int hold_next_side = 160;
    
	// score
	int score = 0;
	
	// new tetriminos
	int counter = 0;
	ArrayList<Integer> bag = new ArrayList<Integer>();
	Set<String> coordinates = new HashSet<String>();
	public Map<String, tetrimino> map = new HashMap<String, tetrimino>();
	public void newTetrimino() {
		char newBlock;
		if (bag.size() == 0) {
			bag.add(0);
			bag.add(1);
			bag.add(2);
			bag.add(3);
			bag.add(4);
			bag.add(5);
			bag.add(6);
		}
		int index = (int)(Math.random() * bag.size());
		int num = bag.get(index);
		bag.remove(index);

		if (num == 0) newBlock = 'I'; 
		else if (num == 1) newBlock = 'O'; 
		else if (num == 2) newBlock = 'T'; 
		else if (num == 3) newBlock = 'J'; 
		else if (num == 4) newBlock = 'L'; 
		else if (num == 5) newBlock = 'S'; 
		else newBlock = 'Z';
		
		String name = String.format("tetrimino%d", counter);
		// System.out.println(name);
		// System.out.println(newBlock);
		map.put(name, new tetrimino(newBlock, 1));
		counter++;
	}

	// column check
	gridCheck gridCheck = new gridCheck();

	// getting time
	int lastTime;
	boolean down;
	private final long startTime = System.currentTimeMillis();
    public int getSeconds() {
		long time = System.currentTimeMillis();
        return (int)((time - startTime) / 1000);
	}
	
	
	// Painting Stuff
	public void paint(Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        // Strings
		Font font = new Font ("Arya", 1, 30);
        g.setFont(font);
        String scoreTitle = String.format("SCORE: %d", score);
		g.drawString(scoreTitle, 450 - g.getFontMetrics().stringWidth(scoreTitle)/2, 50); // score
		g.drawString("HOLD", 125 - g.getFontMetrics().stringWidth("HOLD")/2, 110);
		g.drawString("NEXT", 775 - g.getFontMetrics().stringWidth("NEXT")/2, 110);
        
        // backgrounds
        g.setColor(Color.white);
		g.fillRect(250, 80, playing_width, playing_height); // playing
		g.fillRect(45, 120, hold_next_side, hold_next_side); // hold
		g.fillRect(695, 120, hold_next_side, hold_next_side); // next

		// g.setColor(Color.red);
		// g.drawLine(250, 0, 250, 1000);
		// g.drawLine(650, 0, 650, 1000);
		// g.drawLine(0, 80, 1000, 80);
		// g.drawLine(0, 880, 1000, 880);

        // playing vertical & horizontal grid lines
        g1.setStroke(new java.awt.BasicStroke(2));
		g1.setColor(Color.lightGray);
		for(int i = 40; i < playing_width; i += 40){
            g1.drawLine(250 + i, 80, 250 + i, playing_height + 80);
        }
        for(int j = 40; j < playing_height; j += 40){
            g1.drawLine(250, 80 + j, playing_width + 250, 80 + j);
		}
		
		// tetriminos
		for (tetrimino value : map.values()) {
			value.draw(g);
		}
        
        // border lines
        g1.setStroke(new java.awt.BasicStroke(3));
        g1.setColor(Color.black);
		g1.drawRect(250, 80, playing_width, playing_height); // playing	
		g1.drawRect(45, 120, hold_next_side, hold_next_side); // hold
		g1.drawRect(695, 120, hold_next_side, hold_next_side); // next

	}// end of paint
	
	// Update Stuff 
	public void update() {

		// first tetrimino
		if (map.size() == 0) {
			newTetrimino();
		}

		// bounds
		for (tetrimino value : map.values()) {
			if (value.getMoving()) {
				value.setRbound(false);
				value.setLbound(false);
				value.setDbound(false);
				for (int i = 0; i < 4; i++) {
					int FXRblockCoords = value.getBlockX(i) + 40;
					int FXLblockCoords = value.getBlockX(i) - 40;
					int FYDblockCoords = value.getBlockY(i) + 40;
					int FYTblockCoords = value.getBlockY(i) - 40;

					// game bounds
					if (FXRblockCoords > 650) {
						int shift = FXRblockCoords - 650;
						value.setX(value.getX() - shift);
						value.setRbound(true);
					}

					if (FXLblockCoords < 210) {
						int shift = 210 - FXLblockCoords;
						value.setX(value.getX() + shift);
						value.setLbound(true);
					}

					if (FYDblockCoords > 880) {
						int shift = FYDblockCoords - 880;
						value.setY(value.getY() - shift);
						value.setDbound(true);
					}

					if (FYTblockCoords < 40) {
						int shift = 40 - FYTblockCoords;
						value.setY(value.getY() + shift);
						// value.setTbound(true);
					}

					// block bounds
					boolean RBound = gridCheck.checkRBound(value.getBlockX(i) + 40, value.getBlockY(i));
					boolean LBound = gridCheck.checkLBound(value.getBlockX(i) - 40, value.getBlockY(i));
					boolean DBound = gridCheck.checkDBound(value.getBlockX(i), value.getBlockY(i) + 40);
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

		// falling
		if (lastTime - getSeconds() == 0) {
			down = false;
		} else if (getSeconds() % 1 == 0) {
			down = true;
			lastTime = getSeconds();
		}
		if (down) {
			// System.out.println(getSeconds());
			for (tetrimino value : map.values()) {
				if (value.getMoving()){
					// System.out.printf("Rbound: %b%n", value.getRbound());
					// System.out.printf("Lbound: %b%n", value.getLbound());
					// System.out.printf("Dbound: %b%n", value.getDbound());
					if (value.getDbound()) {
						value.setY(value.getY());
						value.setMoving(false);
						for (int j = 0; j < 4; j++) {
							gridCheck.setBound(value.getBlockX(j), value.getBlockY(j));
						}
						break;
					}
					value.setY(value.getY() + 40);
				}
			}
			down = false;
		}

		// new tetrimino
		int movingCounter = 0;
		for (tetrimino value : map.values()) {
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
		
		JFrame f = new JFrame();
		f.setTitle("Tetris");
		f.setSize(screen_width,screen_height);
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
		if(arg0.getKeyCode() == 39){
			for (tetrimino value : map.values()) {
				// System.out.printf("Rbound: %b%n", value.getRbound());
				if (value.getMoving() && !value.getRbound()) {
					value.moveRight();
				}
			}
		} 

		if(arg0.getKeyCode() == 37){
			for (tetrimino value : map.values()) {
				// System.out.printf("Lbound: %b%n", value.getLbound());
				if (value.getMoving() && !value.getLbound()) {
					value.moveLeft();
				}
			}
		} 

		if(arg0.getKeyCode() == 40){
			for (tetrimino value : map.values()) {
				// System.out.printf("Dbound: %b%n", value.getDbound());
				if (value.getMoving() && !value.getDbound()) {
					value.moveDown();
				}
			}
		} 
		
		if(arg0.getKeyCode() == 38){
			for (tetrimino value : map.values()) {
				if (value.getMoving()) {
					if (value.getOrientation() == 4){
						value.setOrientation(1);
					} else {
						value.setOrientation(value.getOrientation() + 1);
					}
				}
			}
        } 
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

	
}









