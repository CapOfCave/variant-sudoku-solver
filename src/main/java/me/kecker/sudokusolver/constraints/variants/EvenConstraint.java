package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.OddEvenConstraint;
import me.kecker.sudokusolver.dtos.Position;

public class EvenConstraint extends OddEvenConstraint {

    public EvenConstraint(Position position) {
        super(position, Parity.EVEN);
    }
}
