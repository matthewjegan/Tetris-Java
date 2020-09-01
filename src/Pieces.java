import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Pieces {
    private Shape[] pieces;
    private BufferedImage blocks;
    private Board board;

    public Pieces(Board b, BufferedImage blocks){
        this.board = b;
        int blockSize = board.getBlockSize();

        pieces = new Shape[7];

        // Tetrominoes
        pieces[0] = new Shape(blocks.getSubimage(0,0,blockSize, blockSize), new int[][] {
                {1,1,0},
                {0,1,1}
        }, board, 1); // Z-Shape (Red)

        pieces[1] = new Shape(blocks.getSubimage(blockSize,0,blockSize, blockSize), new int[][] {
                {0,0,1},
                {1,1,1}
        }, board, 2); // L-Shape (Orange)

        pieces[2] = new Shape(blocks.getSubimage(2*blockSize,0,blockSize, blockSize), new int[][] {
                {1,1},
                {1,1}
        }, board, 3); // Square (Yellow)

        pieces[3] = new Shape(blocks.getSubimage(3*blockSize,0,blockSize, blockSize), new int[][] {
                {0,1,1},
                {1,1,0}
        }, board, 4); // S-Shape (Green)

        pieces[4] = new Shape(blocks.getSubimage(4*blockSize,0,blockSize, blockSize), new int[][] {
                {1,1,1,1}
        }, board, 5); // I-Shape (Light Blue)

        pieces[5] = new Shape(blocks.getSubimage(5*blockSize,0,blockSize, blockSize), new int[][] {
                {0,1,0},
                {1,1,1}
        }, board, 6); // T-Shape (Purple)

        pieces[6] = new Shape(blocks.getSubimage(6*blockSize,0,blockSize, blockSize), new int[][] {
                {1,0,0},
                {1,1,1}
        }, board, 7); // Reverse L-Shape (Indigo)
    }

    public Shape randomPiece(){
        Random random = new Random();
        int index = random.nextInt(7);
        return new Shape(pieces[index].getBlock(), pieces[index].getMatrix(), this.board, index + 1);
    }

    public Shape get(int i){
        return this.pieces[i];
    }
}
