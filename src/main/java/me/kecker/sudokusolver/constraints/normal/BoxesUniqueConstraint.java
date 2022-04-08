package me.kecker.sudokusolver.constraints.normal;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;

public class BoxesUniqueConstraint implements SudokuConstraint {

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

        for (int boxId = 0; boxId < boardVariables.getBoxCount(); boxId++) {
            IntVar[] boxFields = boardVariables.getBox(boxId);
            model.addAllDifferent(boxFields);

        }
    }
}
