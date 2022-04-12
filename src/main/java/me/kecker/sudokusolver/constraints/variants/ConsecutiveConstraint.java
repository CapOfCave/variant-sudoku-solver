package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.FixedDifferenceConstraint;
import me.kecker.sudokusolver.dtos.Pair;

public class ConsecutiveConstraint extends FixedDifferenceConstraint {

    protected ConsecutiveConstraint(Pair affectedCells) {
        super(1, affectedCells);
    }

}
