package me.kecker.sudokusolver;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.exceptions.InvalidBoardException;

public class Board {

    private final int boxSizeX;
    private final int boxSizeY;
    private final int boxCountX;
    private final int boxCountY;
    private final int minValue;
    private final int maxValue;


    public Board(int boxSizeX, int boxSizeY, int boxCountX, int boxCountY, int minValue, int maxValue) {
        this.boxSizeX = boxSizeX;
        this.boxSizeY = boxSizeY;
        this.boxCountX = boxCountX;
        this.boxCountY = boxCountY;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static Board nByNOfSize(int size) {
        if (!isSquare(size)) {
            throw new InvalidBoardException("Input must be a perfect square");
        }
        int sizeSquared = (int) Math.sqrt(size);

        return new Board(sizeSquared, sizeSquared, sizeSquared, sizeSquared, 1, size);
    }


    private static boolean isSquare(int value) {
        int squared = (int) Math.sqrt(value);
        return value == squared * squared;
    }

    public IntVar[][] createVariables(CpModel model) {
        int rowCount = boxSizeY * boxCountY;
        int columnCount = boxSizeX * boxCountX;
        IntVar[][] fields = new IntVar[rowCount][columnCount];

        // define variables
        for (int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
            for (int columnIdx = 0; columnIdx < columnCount; columnIdx++) {
                fields[rowIdx][columnIdx] = model.newIntVar(minValue, maxValue, String.format("r%dc%d", rowIdx + 1, columnIdx + 1));
            }
        }
        return fields;
    }
}
