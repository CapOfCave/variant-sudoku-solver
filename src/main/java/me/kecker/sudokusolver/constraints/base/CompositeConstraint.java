package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;

import java.util.Collection;

public class CompositeConstraint implements SudokuConstraint {

    private final Collection<SudokuConstraint> constraints;

    public CompositeConstraint(Collection<SudokuConstraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        this.constraints.forEach(sudokuConstraint -> sudokuConstraint.apply(model, boardVariables));
    }
}
