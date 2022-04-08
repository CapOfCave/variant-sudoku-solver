package me.kecker.sudokusolver.constraints.normal;

import com.google.ortools.sat.CpModel;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;

public class GivenDigit implements SudokuConstraint {

    private final int rowIdx;
    private final int columnIdx;
    private final int value;

    public GivenDigit(int rowIdx, int columnIdx, int value) {

        this.rowIdx = rowIdx;
        this.columnIdx = columnIdx;
        this.value = value;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        model.addEquality(boardVariables.get(rowIdx, columnIdx), value);

    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColumnIdx() {
        return columnIdx;
    }

    public int getValue() {
        return value;
    }
}
