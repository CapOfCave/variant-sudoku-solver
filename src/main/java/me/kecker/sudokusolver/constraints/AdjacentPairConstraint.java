package me.kecker.sudokusolver.constraints;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.dtos.Pair;

import java.util.Collection;
import java.util.Collections;

/**
 * A constraint affecting an adjacent pair of cells (and which may in turn affect a negative constraint)
 */
public abstract class AdjacentPairConstraint implements SudokuConstraint {

    private final Pair affectedCells;

    protected AdjacentPairConstraint(Pair affectedCells) {
        this.affectedCells = affectedCells;
    }

    @Override
    public final void apply(CpModel model, BoardVariables boardVariables) {
        IntVar variable1 = boardVariables.get(affectedCells.position1());
        IntVar variable2 = boardVariables.get(affectedCells.position2());

        apply(model, boardVariables, variable1, variable2);
    }

    public abstract void apply(CpModel model, BoardVariables boardVariables, IntVar variable1, IntVar variable2);

    public Collection<Class<? extends NegativeConstraint>> getAffectedNegativeConstraints() {
        return Collections.emptyList();
    }

    public Pair getAffectedCells() {
        return affectedCells;
    }
}
