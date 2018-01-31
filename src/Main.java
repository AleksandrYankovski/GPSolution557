import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        int kolMatrixs = in.nextInt();
        int matrixSize = in.nextInt();
        int numberRowForFinishMatrix = in.nextInt();
        int numberColumnForFinishMatrix = in.nextInt();
        int p = in.nextInt();

        List<SquareMatrix> squareMatrixArrayList = new ArrayList<>();

        for (int iterator = 0; iterator < kolMatrixs; iterator++) {
            SquareMatrix tempSquareMatrix = new SquareMatrix(matrixSize);
            MatrixCreator.fillFromConsole(tempSquareMatrix, in);
            squareMatrixArrayList.add(tempSquareMatrix);
        }

        int[] tempRow = squareMatrixArrayList.get(0).getRow(numberRowForFinishMatrix - 1);

        for (int iterator = 0; iterator < kolMatrixs - 2; iterator++) {
            tempRow = Multiplicator
                    .multiplyRowToMatrix(tempRow, squareMatrixArrayList.get(iterator + 1), p);
        }

        int[] finalColumn = squareMatrixArrayList.get(kolMatrixs - 1)
                .getColumn(numberColumnForFinishMatrix - 1);

        if (squareMatrixArrayList.size() == 1) {
            System.out.println(finalColumn[numberRowForFinishMatrix - 1]);
        } else {
            System.out.println(Multiplicator.multiplyRowToColumn(tempRow, finalColumn, p));
        }
    }
}

class SquareMatrix {
    private int[][] matrix;
    private int size;

    public SquareMatrix(int size) {
        this.size = size;

        matrix = new int[size][size];
    }

    public int[] getRow(int numberRow) {
        return matrix[numberRow];
    }

    public int[] getColumn(int numberColumn) {
        int[] column = new int[size];

        for (int iterator = 0; iterator < size; iterator++) {
            column[iterator] = matrix[iterator][numberColumn];
        }
        return column;
    }

    public int getSize() {
        return size;
    }

    public void setElement(int rowColumn, int columnIndex, int value) {
        matrix[rowColumn][columnIndex] = value;
    }


}

class MatrixCreator {
    public static void fillFromConsole(SquareMatrix matrix, InputReader inputReader) {
        for (int tempIndexRow = 0; tempIndexRow < matrix.getSize();
             tempIndexRow++) {
            for (int tempIndexColumn = 0; tempIndexColumn < matrix.getSize();
                 tempIndexColumn++) {
                matrix.setElement(tempIndexRow, tempIndexColumn, inputReader.nextInt());
            }
        }

    }
}

class Multiplicator {
    public static int[] multiplyRowToMatrix(int[] row, SquareMatrix squareMatrixSecond, int module) {
        int[] newRow = new int[squareMatrixSecond.getSize()];

        for (int iterator = 0; iterator < squareMatrixSecond.getSize(); iterator++) {
            newRow[iterator] = multiplyRowToColumn(row, squareMatrixSecond.getColumn(iterator), module);
        }
        return newRow;
    }

    public static int multiplyRowToColumn(int[] row, int[] column, int module) {
        int sum = 0;

        for (int iterator = 0; iterator < row.length; iterator++) {
            sum += row[iterator] * column[iterator];
        }
        return sum % module;
    }
}

class InputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream));
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
}