import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	int v = 40;
    
	// score
	int score = 0;
	
	//tetriminos
	tetrimino I = new tetrimino('I');
	tetrimino O = new tetrimino('O');
	tetrimino T = new tetrimino('T');
	tetrimino J = new tetrimino('J');
	tetrimino L = new tetrimino('L');
	tetrimino S = new tetrimino('S');
	tetrimino Z = new tetrimino('Z');
	tetrimino[] tetriminos = new tetrimino[]{I, O, T, J, L, S, Z};

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
		I.draw(g);
		// O.draw(g);
		// T.draw(g);
		// J.draw(g);
		// L.draw(g);
		// S.draw(g);
		// Z.draw(g);
        
        // border lines
        g1.setStroke(new java.awt.BasicStroke(3));
        g1.setColor(Color.black);
		g1.drawRect(250, 80, playing_width, playing_height); // playing	
		g1.drawRect(45, 120, hold_next_side, hold_next_side); // hold
		g1.drawRect(695, 120, hold_next_side, hold_next_side); // next

	}// end of paint
	
	// Update Stuff 
	public void update() {
		// falling
		if (lastTime - getSeconds() == 0) {
			down = false;
		} else if (getSeconds() % 1 == 0) {
			down = true;
			lastTime = getSeconds();
		}
		if (down) {
			System.out.println(getSeconds());
			for (int i = 0; i < tetriminos.length; i++) {
				tetriminos[i].setY(tetriminos[i].getY() + 40);
			}
			down = false;
		}

		// bounds
		for (int i = 0; i < tetriminos.length; i++) {
			if (tetriminos[i].getType() == 'I') {
				if (tetriminos[i].getY() >= 840) {
					tetriminos[i].setY(840);
					tetriminos[i].setMoving(false);
				} // bottom
			} else {
				if (tetriminos[i].getY() >= 800) {
					tetriminos[i].setY(800);
					tetriminos[i].setMoving(false);
				}
			}
			if (tetriminos[i].getX() <= 250) {
				tetriminos[i].setX(250);
			}
			if (tetriminos[i].getX() + tetriminos[i].getW() >= 650) {
				tetriminos[i].setX(650 - tetriminos[i].getW());
			}
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
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == 39){
			if (I.getMoving()) {
				I.moveRight();
			}
		} 

		if(arg0.getKeyCode() == 37){
			if (I.getMoving()) {
				I.moveLeft();
			}
		} 

		if(arg0.getKeyCode() == 40){
			if (I.getMoving()) {
				I.moveDown();
			}
        } 
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	
}









