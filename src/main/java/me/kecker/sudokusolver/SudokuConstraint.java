package me.kecker.sudokusolver;

import com.google.ortools.sat.CpModel;

public interface SudokuConstraint {

    void apply(CpModel model, BoardVariables boardVariables);
}
