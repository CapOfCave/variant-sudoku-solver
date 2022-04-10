package me.kecker.sudokusolver.constraints.normal;

import com.google.ortools.sat.CpModel;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;

public class RowsUniqueConstraint implements SudokuConstraint {

    public void apply(CpModel model, BoardVariables boardVariables) {
        for (int rowIdx = 0; rowIdx < boardVariables.getRowCount(); rowIdx++) {
            model.addAllDifferent(boardVariables.getRow(rowIdx));
        }
    }
}
