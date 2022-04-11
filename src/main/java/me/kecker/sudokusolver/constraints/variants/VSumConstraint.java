package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.AdjacentSumConstraint;
import me.kecker.sudokusolver.dtos.Position;

public class VSumConstraint extends AdjacentSumConstraint {
    public VSumConstraint(Position position1, Position position2) {
        super(5, position1, position2);
    }
}
