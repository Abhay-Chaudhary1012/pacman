import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * 
 * 
 * App
 * 
 * Parent of Pacman class 
 * 
 * @author Divyansh 
 * 
 */



public class App {
    public static void main(String[] args) throws Exception {
        int rowCount = 21 ;
        int colCount = 19 ;
        int tileSize = 32;
        int boardWidth = colCount*tileSize;
        int boardHeight = rowCount*tileSize;
        
        JFrame frame = new JFrame("Pac Man");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set application icon
        Image icon = new ImageIcon(App.class.getResource("/assets/icon.png")).getImage();
        frame.setIconImage(icon);

        PacMan pacMangame = new PacMan();
        frame.add(pacMangame);
        frame.pack();
        pacMangame.requestFocus();
        frame.setVisible(true);
        
   }
}
