/**
 * @author Divyansh 
 * 
 * Game Class
 * Using HashSet instead of a ArrayList , 
 * for pure performance purpose , fast look up time
 */


import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener ,KeyListener{
    class Block{
        int x; 
        int y ;
        int width ;
        int height ;
        Image image;
        int startX;
        int startY;
        char direction = 'U';
        int velocityX = 0 ;
        int velocityY = 0 ;


        Block(Image image , int x , int y , int width , int height){
            this.image = image;
            this.x = x ;
            this.y = y ;
            this.width = width;
            this.height = height;
            this.startX = x ;
            this.startY = y;
        }
        void updateDirection(char direction ){
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            int attemptedVX = this.velocityX;
            int attemptedVY = this.velocityY;
            for(Block wall : walls){
                if(collision(this, wall)){
                    this.x -= attemptedVX;
                    this.y -= attemptedVY;
                    this.direction = prevDirection;
                    updateVelocity();
                    break;
                }
            }
        }
        void updateVelocity(){
            if(this.direction == 'U'){
                this.velocityY = -tileSize/4;
                this.velocityX = 0;
            }
            else if(this.direction == 'D'){
                this.velocityY = tileSize/4;
                this.velocityX = 0;
            }
            else if(this.direction == 'L'){
                this.velocityY = 0;
                this.velocityX = -tileSize/4;
            }
            else if(this.direction == 'R'){
                this.velocityY = 0;
                this.velocityX = tileSize/4;
            }
        }
        void reset(){
            this.x = this.startX;
            this.y = this.startY;
        }
    }
    private int rowCount = 21 ;
    private int colCount = 19 ;
    private int tileSize = 32;
    private int boardWidth = colCount*tileSize;
    private int boardHeight = rowCount*tileSize;
    private Image wallImage;
    /* Ghost Image */
    private Image blueGhostImage;
    private Image redGhostImage;
    private Image pinkGhostImage;
    private Image orangeGhostImage;


    /* PacMan Image */
    private Image pacmanDownImage;
    private Image pacmanUpImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };


    HashSet<Block> walls ;
    HashSet<Block> foods;
    HashSet<Block> ghostset;
    Block pacman;

    /** game loop */
    Timer gameLoop;
    char[] direction = {'U','D','L','R'};
    Random random = new Random();
    int score = 0 ;
    int lives = 3 ;

    boolean gameOver = false;

    PacMan(){
        setPreferredSize(new Dimension(boardWidth , boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        // load image 
        wallImage = new ImageIcon(getClass().getResource("./assets/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./assets/blueGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./assets/redGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./assets/pinkGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./assets/orangeGhost.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./assets/pacmanDown.png")).getImage();
        pacmanUpImage = new ImageIcon(getClass().getResource("./assets/pacmanUp.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./assets/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./assets/pacmanRight.png")).getImage();

        loadMap();
        for(Block ghost : ghostset){
            char newDirection = direction[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }

        // how long it takes to start timer , millisecond gone between frames 
        gameLoop = new Timer(50 , this); // 20 fps (1000/50)
        gameLoop.start();


    }

    public void loadMap(){
        walls =  new HashSet<Block>();
        foods = new HashSet<Block>();
        ghostset = new HashSet<Block>();

        for(int r = 0 ; r < rowCount ; r++){
            for(int c = 0 ; c < colCount ; c++){
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;

                if(tileMapChar == 'X'){
                    Block wall = new Block(wallImage , x,y , tileSize,tileSize);
                    walls.add(wall);
                }
                else if(tileMapChar == 'b' ){
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghostset.add(ghost);
                }
                else if(tileMapChar == 'r' ){
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghostset.add(ghost);
                }
                else if(tileMapChar == 'p' ){
                    Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghostset.add(ghost);
                }
                else if(tileMapChar == 'o' ){
                    Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghostset.add(ghost);
                }
                else if(tileMapChar == 'P' ){
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if(tileMapChar == ' '){
                    Block food = new Block(null , x + 14 , y + 14 , 4 , 4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(pacman.image, pacman.x , pacman.y , pacman.width , pacman .height , null);

        for(Block ghost : ghostset){
            g.drawImage(ghost.image , ghost.x ,ghost.y , ghost.width , ghost.height , null);
        }
        for(Block wall : walls){
            g.drawImage(wall.image , wall.x , wall.y , wall.width , wall.height , null);
        }
        g.setColor(Color.WHITE);
        for(Block food : foods){
            g.fillRect(food.x, food.y , food.width , food.height);
        }
        g.setFont(new Font("Arial " , Font.PLAIN, 18));
        if(gameOver){
            g.drawString("Game Over " + String.valueOf(score) , tileSize/2, tileSize/2);
        }
        else{
            g.drawString("x" + String.valueOf(lives) + "Score:" +String.valueOf(score) , tileSize/2, tileSize/2 );
        }
    }
    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        for(Block wall : walls){
            if(collision(pacman , wall)){
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        /**check ghost collisions */
        for(Block ghost : ghostset){
            if(collision(ghost,pacman)){
                lives -= 1;
                if(lives == 0 ){
                    gameOver = true; 
                    return ;
                }
                resetPostions();
                return;
            }
        }
        /** move ghosts */
        for(Block ghost : ghostset){
            if(ghost.y >= tileSize*9 && ghost.y < tileSize*9 + tileSize/4 && ghost.direction!='U' && ghost.direction != 'D'){
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            
            for(Block wall : walls){
                if(collision(wall, ghost) || ghost.x<0 || ghost.x + ghost.width >= boardWidth){
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = direction[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }
        // check for food collision 
        HashSet<Block> foodsEaten = new HashSet<Block>();
        for(Block food : foods){
            if(collision(pacman, food)){
                foodsEaten.add(food);
                score += 10;
            }
        }
        foods.removeAll(foodsEaten);

        // reset food if pacman eats food 
         if(foods.isEmpty()){
            loadMap();
            resetPostions();
        }


    }

    /** collision detection function  */
    public boolean collision(Block a , Block b){
        return a.x < b.x + b.width && 
               a.x + a.width > b.x && 
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    public void resetPostions(){
        pacman.reset();
        pacman.velocityX = 0 ;
        pacman.velocityY = 0 ;
        for(Block ghost:ghostset){
            ghost.reset();
            char newDirection = direction[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
        if(gameOver ){
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            pacman.updateDirection('U');
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            pacman.updateDirection('D');
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            pacman.updateDirection('L');
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            pacman.updateDirection('R');
        }

        if(pacman.direction == 'U'){
            pacman.image = pacmanUpImage;
        }
        else if(pacman.direction == 'D'){
            pacman.image = pacmanDownImage;
        }
        else if(pacman.direction == 'L'){
            pacman.image = pacmanLeftImage;
        }
        else if(pacman.direction == 'R'){
            pacman.image = pacmanRightImage;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(gameOver ){
            loadMap();
            resetPostions();
            lives = 3 ;
            score = 0 ;
            gameOver=false;
            gameLoop.start();
        }
    }
}
