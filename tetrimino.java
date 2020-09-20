import java.awt.Color;
import java.awt.Graphics;

public class tetrimino {

    int x, y;
    char type;
    block block1 = new block();
    block block2 = new block();
    block block3 = new block();
    block block4 = new block();
    block[] blocks = new block[]{block1, block2, block3, block4};
    
    public tetrimino(char type) {
        this.type = type;
        if (type == 'I'){
            x = 250;
            y = 80;
            for (int i = 0; i < 4; i++){
                blocks[i].setX(x + i * 40);
                blocks[i].setY(y);
            }
        }
        if (type == 'O'){
            x = 450;
            y = 80;
            for (int i = 0; i < 2; i++){
                blocks[i].setX(x + i * 40);
                blocks[i].setY(y);
            }
            for (int i = 2; i < 4; i++){
                blocks[i].setX(x + (i - 2) * 40);
                blocks[i].setY(y + 40);
            }
        }
        if (type == 'T'){
            x = 250;
            y = 160;
            blocks[0].setX(x + 40);
            blocks[0].setY(y);
            for (int i = 1; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            
        }
        if (type == 'J'){
            x = 250;
            y = 280;
            blocks[0].setX(x);
            blocks[0].setY(y);
            for (int i = 1; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            
        }
        if (type == 'L'){
            x = 250;
            y = 400;
            blocks[0].setX(x + 80);
            blocks[0].setY(y);
            for (int i = 1; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
            
        }
        if (type == 'S'){
            x = 250;
            y = 520;
            for (int i = 0; i < 2; i++){
                blocks[i].setX(x + (i + 1) * 40);
                blocks[i].setY(y);
            }
            for (int i = 2; i < 4; i++){
                blocks[i].setX(x + (i - 2) * 40);
                blocks[i].setY(y + 40);
            }
        }
        if (type == 'Z'){
            x = 250;
            y = 640;
            for (int i = 0; i < 2; i++){
                blocks[i].setX(x + (i) * 40);
                blocks[i].setY(y);
            }
            for (int i = 2; i < 4; i++){
                blocks[i].setX(x + (i - 1) * 40);
                blocks[i].setY(y + 40);
            }
        }
    }

    public void draw(Graphics g) {
        java.awt.Graphics2D g1 = (java.awt.Graphics2D) g.create();

        if (type == 'I') {
            g.setColor(new Color(0, 201, 221));
        }
        if (type == 'O') {
            g.setColor(new Color(254, 238, 44));
        }
        if (type == 'T') {
            g.setColor(new Color(153, 0, 186));
        }
        if (type == 'J') {
            g.setColor(new Color(14, 101, 198));
        }
        if (type == 'L') {
            g.setColor(new Color(247, 167, 0));
        }
        if (type == 'S') {
            g.setColor(new Color(0, 210, 44));
        }
        if (type == 'Z') {
            g.setColor(new Color(170, 2, 0));
        }


        for (int i = 0; i < 4; i++){
            g.fillRect(blocks[i].getX(), blocks[i].getY(), blocks[i].getSize(), blocks[i].getSize());
            g1.setStroke(new java.awt.BasicStroke(2));
            g1.setColor(Color.black);
            g1.drawRect(blocks[i].getX(), blocks[i].getY(), blocks[i].getSize(), blocks[i].getSize());
        }
        
    }
}
