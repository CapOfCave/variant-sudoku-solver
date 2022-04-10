package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.Collection;

public class UniqueConstraint implements SudokuConstraint {

    private final Collection<Position> affectedCells;

    public UniqueConstraint(Collection<Position> affectedCells) {
        this.affectedCells = affectedCells;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        IntVar[] variables = affectedCells.stream().map(boardVariables::get).toArray(IntVar[]::new);
        model.addAllDifferent(variables);
    }
}
