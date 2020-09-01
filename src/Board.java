import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board extends JPanel implements KeyListener {

    private final int blockSize = 30;
    private final int bWidth = 10, bHeight = 20;
    private int[][] board = new int[bHeight][bWidth];

    private BufferedImage blocks;
    private Pieces pieces;
    private Shape nextShape;

    private Timer timer;
    private boolean gameOver = false;
    private final int FPS = 60;
    private final int delay = 1000 / FPS;

    public Board(){
        try {
            blocks = ImageIO.read(Board.class.getResource("/blocks.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pieces = new Pieces(this, blocks);
        setNextShape();

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
                repaint();
            }
        });
        timer.start();
    }

    public void setNextShape(){
        nextShape = pieces.randomPiece();

        for (int row = 0; row < nextShape.getMatrix().length; row++){
            for(int col = 0; col < nextShape.getMatrix()[0].length; col++){
                if (nextShape.getMatrix()[row][col] != 0){
                    if (board[row][col + 4] != 0){
                        gameOver = true;
                    }
                }
            }
        }
    }

    public void refresh(){
        nextShape.refresh();
        if (gameOver){
            timer.stop();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        nextShape.draw(g);

        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[row].length; col++){
                if (board[row][col] != 0){
                    g.drawImage(blocks.getSubimage((board[row][col] - 1) * blockSize, 0, blockSize, blockSize), col * blockSize, row * blockSize, null);
                }
            }
        }

        for (int i = 0; i < bHeight; i++){
            g.drawLine(0, i* blockSize, bWidth*blockSize, i*blockSize);
        }

        for (int i = 0; i < bWidth; i++){
            g.drawLine(i * blockSize, 0, i*blockSize, bHeight*blockSize);
        }


    }

    public int getBlockSize(){
        return this.blockSize;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int x, int y, int value){
        this.board[x][y] = value;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            nextShape.deltaX(-1);
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            nextShape.deltaX(1);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            nextShape.setDownSpeed();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z){
            nextShape.rotate();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            nextShape.resetSpeed();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
