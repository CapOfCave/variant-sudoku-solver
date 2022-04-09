package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.OddEvenConstraint;
import me.kecker.sudokusolver.utils.SudokuPosition;

public class EvenConstraint extends OddEvenConstraint {

    public EvenConstraint(SudokuPosition position) {
        super(position, Parity.EVEN);
    }
}
