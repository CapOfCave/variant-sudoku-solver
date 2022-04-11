package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.AdjacentPairSumConstraint;
import me.kecker.sudokusolver.dtos.Pair;
import me.kecker.sudokusolver.dtos.Position;

public class VSumConstraint extends AdjacentPairSumConstraint {
    public VSumConstraint(Pair affectedCells) {
        super(5, affectedCells);
    }
}
