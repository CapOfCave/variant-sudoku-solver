package me.kecker.sudokusolver.constraints.normal;

import com.google.ortools.sat.CpModel;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;

public class ColumnsUniqueConstraint implements SudokuConstraint {

    public void apply(CpModel model, BoardVariables boardVariables) {
        for (int columnIdx = 0; columnIdx < boardVariables.getColumnCount(); columnIdx++) {
            model.addAllDifferent(boardVariables.getColumn(columnIdx));
        }
    }
}
