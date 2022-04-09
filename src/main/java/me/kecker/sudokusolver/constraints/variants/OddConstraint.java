package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.OddEvenConstraint;
import me.kecker.sudokusolver.utils.SudokuPosition;

public class OddConstraint extends OddEvenConstraint {

    public OddConstraint(SudokuPosition position) {
        super(position, Parity.ODD);
    }
}
