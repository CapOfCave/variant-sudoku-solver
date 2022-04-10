package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.Collection;

public class FixedSumConstraint implements SudokuConstraint {

    private final Collection<Position> affectedCells;
    private final int total;

    public FixedSumConstraint(Collection<Position> affectedCells, int total) {
        this.affectedCells = affectedCells;
        this.total = total;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        IntVar[] intVars = affectedCells.stream().map(boardVariables::get).toArray(IntVar[]::new);
        model.addEquality(LinearExpr.sum(intVars), total);
    }
}
