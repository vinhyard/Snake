import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.*;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    //screen width and height
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // object size
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    //delay timer
    static final int DELAY=75;
    //2 arrays - hold coordinates of body parts of snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    // set initial amount of body parts of snake
    int bodyParts = 6;
    int applesEaten;
    // x and y coordinate of apple location 
    int appleX;
    int appleY;
    // U = up, R = right, D = down, L = left
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();


    }
    //declare methods
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running) {

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            // snake body
            for(int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("Int Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            //display score of game
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    //populate game w/ apple
    public void newApple() {
        // appear someone in x axis
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }
    //move function
    public void move() {
        //move snake
        for(int i = bodyParts; i>0;i--) {
            x[i] = x[i - 1];
            y[i] = y[i-1];
        }
        //change direction
        switch(direction) {
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;

        }
    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten ++;
            newApple();
        }

    }
    // check if head collides with body
    public void checkCollisions() {
        for(int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;

            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        // if head touches right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        // if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Int Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        //display score of game
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());
        // Game over txt
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        // center font 
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        // put text in center of screen width
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();

        }
        repaint();
    }

    //inner class
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            // change directions with keys
            switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(direction != 'R') {
                    direction = 'L';
                }    
                break;
            case KeyEvent.VK_RIGHT:
                if(direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if(direction != 'D') {
                    direction = 'U';
                
                }
                break;
            case KeyEvent.VK_DOWN:
                if(direction != 'U') {
                    direction = 'D';
                }
            }
        }
    }
    
}
