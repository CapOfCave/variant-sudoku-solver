package me.kecker.sudokusolver;

import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.utils.HelperVarManager;
import me.kecker.sudokusolver.utils.SudokuPosition;

public class BoardVariables {

    private final Board board;
    private final IntVar[][] variables;

    public BoardVariables(Board board, IntVar[][] variables) {
        this.board = board;
        this.variables = variables;
    }

    public int getRowCount() {
        return board.getRowCount();
    }

    public int getColumnCount() {
        return board.getColumnCount();
    }

    public IntVar[] getRow(int rowIdx) {
        return variables[rowIdx];
    }

    public IntVar[] getColumn(int columnIdx) {
        IntVar[] columnFields = new IntVar[this.getRowCount()];
        for (int rowIdx = 0; rowIdx < this.getRowCount(); rowIdx++) {
            columnFields[rowIdx] = get(rowIdx, columnIdx);
        }
        return columnFields;
    }

    public IntVar[] getBox(int boxIdx) {
        return this.getBox(boxIdx / board.getBoxCountX(), boxIdx % board.getBoxCountX());
    }

    public IntVar[] getBox(int boxRowIdx, int boxColumnIdx) {

        IntVar[] boxFields = new IntVar[board.getBoxSizeY() * board.getBoxSizeX()];

        for (int dy = 0; dy < board.getBoxSizeY(); dy++) {
            int rowIdx = dy + boxRowIdx * board.getBoxSizeY();
            for (int dx = 0; dx < board.getBoxSizeX(); dx++) {
                int columnIdx = dx + boxColumnIdx * board.getBoxSizeX();
                boxFields[dy * board.getBoxSizeX() + dx] = get(rowIdx, columnIdx);
            }
        }
        return boxFields;
    }

    public int getBoxCount() {
        return board.getBoxCount();
    }

    public IntVar get(int rowIdx, int columnIdx) {
        return variables[rowIdx][columnIdx];
    }

    public IntVar get(SudokuPosition position) {
        return get(position.rowIdx(), position.columnIdx());
    }

    public Board getBoard() {
        return board;
    }

    public String generateUniqueHelperVarName(String prefix) {
        return HelperVarManager.generateUniqueHelperVarName(prefix);
    }

    public int getMinValue() {
        return board.getMinValue();
    }

    public int getMaxValue() {
        return board.getMaxValue();
    }

    public boolean isInBounds(int rowIdx, int columnIdx) {
        return rowIdx >= 0 && rowIdx < getRowCount() && columnIdx >= 0 && columnIdx < getColumnCount();
    }
}
