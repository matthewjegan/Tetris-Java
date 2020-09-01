import java.awt.*;
import java.awt.image.BufferedImage;

public class Shape {
    private BufferedImage block;
    private int blockSize;
    private int colour;
    private int[][] matrix;
    private MatrixOperations matOps;

    private Board board;

    private int x, y;
    private int dx = 0, dy = 0;
    private boolean collided = false;
    private boolean xObstructed = false;

    private int normalSpeed = 600, downSpeed = 40, currentSpeed;
    private long time, prevTime;



    public Shape(BufferedImage block, int[][] matrix, Board board, int colour){
        this.matrix = matrix;
        this.matOps = new MatrixOperations();
        this.block = block;
        this.colour = colour;
        this.board = board;
        this.blockSize = this.board.getBlockSize();
        this.currentSpeed = this.normalSpeed;

        time = 0;
        prevTime = System.currentTimeMillis();

        x = 4;
        y = 0;
    }

    public void refresh(){
        time += System.currentTimeMillis() - prevTime;
        prevTime = System.currentTimeMillis();

        if (collided){
            for (int row = 0; row < this.matrix.length; row++){
                for (int col = 0; col < this.matrix[row].length; col++){
                    if (this.matrix[row][col] != 0){
                        board.setBoard(y + row,x + col, this.colour);
                    }
                }
            }

            board.setNextShape();
        }

        if (!(x + dx < 0) && !(x + this.matrix[0].length + dx > 10)){

            for (int row = 0; row < this.matrix.length; row++){
                for (int col = 0; col < this.matrix[row].length; col++){
                    if (this.matrix[row][col] != 0){
                        if (board.getBoard()[y + row][x + dx + col] != 0){
                            xObstructed = true;
                        }
                    }
                }
            }

            if (!(xObstructed)){
                x += dx;
            }
        }

        if (y + this.matrix.length + 1 <= 20){

            for (int row = 0; row < this.matrix.length; row++){
                for (int col = 0; col < this.matrix[row].length; col++){
                    if (this.matrix[row][col] != 0){
                        if (board.getBoard()[y + row + 1][col + x] != 0){
                            collided = true;
                        }
                    }
                }
            }


            if (time > currentSpeed) {
                y++;
                time = 0;
            }
        } else { collided = true; }

        clearLine();
        dx = 0;
        xObstructed = false;
    }

    private void clearLine(){
        int height = board.getBoard().length - 1;
        int width = board.getBoard()[0].length;

        for(int i = height; i > 0; i--){
            boolean lineFilled = true;

            for(int j = 0; j < width; j++){

                if(board.getBoard()[i][j] == 0){
                    lineFilled = false;
                }

                board.getBoard()[height][j] = board.getBoard()[i][j];

            }
            if(!(lineFilled))
                height --;


        }

    }

    public void draw(Graphics g){

        for (int i = 0; i < this.matrix.length; i++){
            for (int j = 0; j < this.matrix[i].length; j++){
                if (this.matrix[i][j] != 0){
                    g.drawImage(block, j*blockSize + x*blockSize, i*blockSize + y*blockSize, null);
                }
            }
        }
    }

    public void deltaX(int dx){
        this.dx = dx;
    }

    public void rotate(){
        if (collided){
            return;
        }

        int[][] m = matOps.rotateLeft(this.matrix);

        if (x + m[0].length > 10 || y + m.length > 20){
            return;
        }

        for (int row = 0; row < m.length; row++){
            for(int col = 0; col < m[0].length; col++){
                if (board.getBoard()[y + row][x + col] != 0){
                    return;
                }
            }
        }

        this.matrix = m;
    }

    public void resetSpeed(){
        currentSpeed = normalSpeed;
    }

    public void setDownSpeed(){
        currentSpeed = downSpeed;
    }

    public BufferedImage getBlock() {
        return block;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getColour() {
        return colour;
    }
}
