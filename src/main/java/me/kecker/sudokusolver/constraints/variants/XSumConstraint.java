package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.AdjacentPairSumConstraint;
import me.kecker.sudokusolver.dtos.Pair;

public class XSumConstraint extends AdjacentPairSumConstraint {

    public XSumConstraint(Pair affectedCells) {
        super(10, affectedCells);
    }
}
