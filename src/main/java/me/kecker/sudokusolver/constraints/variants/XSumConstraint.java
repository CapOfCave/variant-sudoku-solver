package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.AdjacentSumConstraint;
import me.kecker.sudokusolver.dtos.Position;

public class XSumConstraint extends AdjacentSumConstraint {

    public XSumConstraint(Position position1, Position position2) {
        super(10, position1, position2);
    }
}
