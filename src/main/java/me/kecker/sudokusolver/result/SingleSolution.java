package me.kecker.sudokusolver.result;

import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

public class SingleSolution {

    private final Board board;
    private final int[][] values;

    public SingleSolution(Board board, int[][] values) {
        this.board = board;
        this.values = values;
    }

    public void printBoard() {
        SudokuSolverUtils.ValueSupplier valuesByPosition = (rowIdx, columnIdx) -> Integer.toString(values[rowIdx][columnIdx]);
        SudokuSolverUtils.printBoard(valuesByPosition, board);
    }

    public int value(int rowIdxStartingAtOne, int columnIdxStartingAtOne) {
        int rowIdx = rowIdxStartingAtOne - 1;
        int columnIdx = columnIdxStartingAtOne - 1;
        return values[rowIdx][columnIdx];
    }
}
