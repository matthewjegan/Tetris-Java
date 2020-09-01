import javax.swing.*;
import java.awt.event.KeyListener;

public class Window {
    private JFrame window;
    private Board board;
    public static final int WIDTH = 315, HEIGHT = 635;

    public Window(){
        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        board = new Board();
        window.add(board);
        window.addKeyListener(board);

        window.setVisible(true);
    }

    public static void main(String[] args){
        new Window();
    }
}
