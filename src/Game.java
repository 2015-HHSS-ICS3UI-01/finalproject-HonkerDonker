import sun.audio.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author ramli8368
 */
public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener {
    
     public void Death(){
         System.out.println(GameOver);
    }

    // Height and Width of our game
    static final int WIDTH = 1000;
    static final int HEIGHT = 700;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    int frames;
    
    int score = 0;
    int image = 0;
    int[] EX = new int[3];
    int[] EW = new int[3];
    int[] EH = new int[3];
    int[] EY = new int[3];
    int enemyX = -120;
    int enemy2X = 920;
    int enemy3X = -450;
    int x = 100;
    int y = 100;
    boolean buttonPressed = false;
    Rectangle player = new Rectangle(500, 500, 200, 300);
    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    //block
    ArrayList<Rectangle> blocks = new ArrayList<>();
    int gravity = 1;
    int frameCount = 0;
    // keyboard variables
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    
    BufferedImage Background = loadImage("GameBackground.jpg");
        BufferedImage Character = loadImage("Character_edited-1.png");
    BufferedImage Enemy = loadImage("k.png");
    BufferedImage Enemy2 = loadImage("Knowledge.png");
    BufferedImage Enemy3 = loadImage("Wiener.png");
    BufferedImage Meme = loadImage("owxzNDC.jpg");
    BufferedImage Meme2 = loadImage("Bvd4WCBIEAAIGUF.jpg");
    BufferedImage Meme3 = loadImage("P7w02Qa.jpg");
    BufferedImage D = loadImage("images.jpg");
    BufferedImage A = loadImage("Apps-Keyboard-icon.png");
    BufferedImage Space = loadImage("computer_key_Space_bar.png");
    BufferedImage GameOver = loadImage("xt6pj.jpg");
    
    Font font = new Font("arial", Font.PLAIN, 40);
    
    
    public BufferedImage loadImage(String filename){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filename));
        }catch(Exception e){
            System.out.println("Error loading " + filename);
        }
        return img;
    }
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        
        g.drawImage(Background, 0, 0, WIDTH, HEIGHT, null);
        g.drawImage(Character, player.x, player.y, player.width, player.height, null);
        g.drawImage(D, 350, 600, 60, 60, null);
        g.drawImage(A, 250, 600, 60, 60, null);
        g.drawImage(Space, 500, 600, 300, 60, null);
        
        for(Rectangle block: blocks){
        g.drawImage(Enemy2, enemy2X, 425, 200, 150, null);
        g.drawImage(Enemy, enemyX, 425, 200, 150, null);
        g.drawImage(Enemy3, enemy3X, 370, 250, 200, null);
}
         if(buttonPressed){
            g.drawImage(Meme, -50, 400, 1200, 200, null);
            g.drawImage(Meme2, -50, 200, 1200, 200, null);
            g.drawImage(Meme3, -20, 50, 1200, 200, null);
        }
         g.setFont(font);
         g.drawString("" + score, 500, 600);
         
          
         
        // GAME DRAWING ENDS HERE
    }

   
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        blocks.add(new Rectangle(enemyX,425,200,150));
        blocks.add(new Rectangle(enemy2X,425,200,150));
        blocks.add(new Rectangle(enemy3X, 270, 250, 200));
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

           
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            

            x = moveX;
            y = moveY;

            if (left) {
                moveX = -5;
            }else if
             (right) {
                moveX = + 5;
            }else{
                moveX = 0;}
            frameCount++;

            if (frameCount >= 1) {


                // gravity pulling player down
                moveY = moveY + gravity;
                frameCount = 0;
            }


            // jump being pressed and not in the air
            if (jump && prevJump == false && !inAir) {
                // make a big change in Y direction
                moveY = -17;
                inAir = true;
            }
            // keeps track of jump key changes
            prevJump = jump;

            //move the player
            player.x = player.x + moveX;
            player.y = player.y + moveY;
            // if feet of player becomes lower than the screen
            if (player.y + player.height > HEIGHT) {
                player.y = HEIGHT - player.height;
                moveY = 0;
                inAir = false;
            }
            
            enemyX++;
            enemy2X--;
            enemy3X++;
            
            
            for(int i = 0; i < 2; i++){      
               if(player.x > EX[i] + 200 || player.x + player.width < EX[i] || player.y > EY[i] + 150 || player.y + player.height < EY[i]){
                   score++; 
                }            
                
            }
                 
            if(enemyX > WIDTH){
                enemyX = 10;
            }
            if(enemy2X > WIDTH){
                enemy2X = 10;
            }
            if(enemy3X > WIDTH){
                enemy3X = 10;
            }
            
            

            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        game.addMouseListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = true;
        } else if (key == KeyEvent.VK_D) {
            right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = false;
        } else if (key == KeyEvent.VK_D) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            buttonPressed = true;
    }
    }

    @Override
    public void mouseReleased(MouseEvent e) { 
        if(e.getButton() == MouseEvent.BUTTON1){
            buttonPressed = false;
    }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}