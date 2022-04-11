package me.kecker.sudokusolver.constraints;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;

public class NegativeConstraint implements SudokuConstraint {

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

    }
}
