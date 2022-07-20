package birdsnail.example.uitl;

import java.util.Arrays;

public class CopyUtil {


    public static int[][] copyMatrix(int[][] source) {
        int row = source.length;
        if (row == 0) {
            return new int[row][0];
        }
        int col = source[0].length;
        if (col == 0) {
            return new int[row][0];
        }

        int[][] target = new int[row][col];
        for (int i = 0; i < row; i++) {
            target[i] = Arrays.copyOf(source[i], col);
        }

        return target;
    }

}
