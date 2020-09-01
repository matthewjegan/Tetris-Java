public class MatrixOperations {

    public MatrixOperations(){
    }

    public int[][] rotateLeft(int[][] matrix){
        return this.transpose(this.reverse(matrix));
    }

    public int[][] rotateRight(int[][] matrix){
        return this.reverse(this.transpose(matrix));
    }

    public int[][] transpose(int[][] matrix){
        int[][] m = new int[matrix[0].length][matrix.length];
        for (int col = 0; col < matrix[0].length; col++){
            for (int row = 0; row < matrix.length; row++){
                m[col][row] = matrix[row][col];
            }
        }

        return m;
    }

    public int[][] reverse(int[][] matrix){
        int[][] m = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++){
            for (int col = 0; col < matrix[row].length; col++){
                m[row][col] = matrix[row][matrix[0].length - (1 + col)];
            }
        }

        return m;
    }
}
