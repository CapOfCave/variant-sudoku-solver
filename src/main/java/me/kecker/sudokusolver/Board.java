package me.kecker.sudokusolver;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.exceptions.InvalidBoardException;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

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
        if (!SudokuSolverUtils.isSquare(size)) {
            throw new InvalidBoardException("Input must be a perfect square");
        }
        int sizeSquared = (int) Math.sqrt(size);

        return new Board(sizeSquared, sizeSquared, sizeSquared, sizeSquared, 1, size);
    }

    public BoardVariables createVariables(CpModel model) {
        int rowCount = this.getRowCount();
        int columnCount = this.getColumnCount();


        IntVar[][] fields = new IntVar[rowCount][columnCount];
        for (int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
            for (int columnIdx = 0; columnIdx < columnCount; columnIdx++) {
                String name = getNameByPosition(rowIdx, columnIdx);
                fields[rowIdx][columnIdx] = model.newIntVar(minValue, maxValue, name);
            }
        }
        return new BoardVariables(this, fields);
    }

    public int getBoxSizeX() {
        return boxSizeX;
    }

    public int getBoxSizeY() {
        return boxSizeY;
    }

    public int getBoxCountX() {
        return boxCountX;
    }

    public int getBoxCountY() {
        return boxCountY;
    }

    public int getRowCount() {
        return boxSizeY * boxCountY;
    }

    public int getColumnCount() {
        return boxSizeX * boxCountX;
    }

    public int getBoxCount() {
        return boxCountX * boxCountY;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    private String getNameByPosition(int rowIdx, int columnIdx) {
        return String.format("r%dc%d", rowIdx + 1, columnIdx + 1);
    }

}
