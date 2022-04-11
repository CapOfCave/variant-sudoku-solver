package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.NegativeConstraint;
import me.kecker.sudokusolver.constraints.base.AdjacentPairSumConstraint;
import me.kecker.sudokusolver.constraints.negative.NegativeXVSumsConstraint;
import me.kecker.sudokusolver.dtos.Pair;

import java.util.Collection;
import java.util.List;

public class VSumConstraint extends AdjacentPairSumConstraint {
    public VSumConstraint(Pair affectedCells) {
        super(5, affectedCells);
    }

    @Override
    public Collection<Class<? extends NegativeConstraint>> getAffectedNegativeConstraints() {
        return List.of(NegativeXVSumsConstraint.class);
    }
}
