package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.OddEvenConstraint;
import me.kecker.sudokusolver.dtos.Position;

public class OddConstraint extends OddEvenConstraint {

    public OddConstraint(Position position) {
        super(position, Parity.ODD);
    }
}
