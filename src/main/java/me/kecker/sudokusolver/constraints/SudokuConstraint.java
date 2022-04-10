package me.kecker.sudokusolver.constraints;

import com.google.ortools.sat.CpModel;
import me.kecker.sudokusolver.BoardVariables;

public interface SudokuConstraint {

    void apply(CpModel model, BoardVariables boardVariables);
}
