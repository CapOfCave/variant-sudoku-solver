package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.CompositeConstraint;
import me.kecker.sudokusolver.constraints.base.FixedSumConstraint;
import me.kecker.sudokusolver.constraints.base.UniqueConstraint;
import me.kecker.sudokusolver.utils.SudokuPosition;
import me.kecker.sudokusolver.utils.SudokuRect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KillerConstraint extends CompositeConstraint {

    public KillerConstraint(Collection<SudokuPosition> affectedCells, int total) {
        super(List.of(
                new FixedSumConstraint(affectedCells, total),
                new UniqueConstraint(affectedCells)));
    }

    public static KillerConstraint rectangularCage(SudokuRect boundsIdxStartingAtOne, int total) {
        int topLeftColumnIdx = boundsIdxStartingAtOne.topLeftColumnIdx() - 1;
        int topLeftRowIdx = boundsIdxStartingAtOne.topLeftRowIdx() - 1;
        int bottomRightColumnIdx = boundsIdxStartingAtOne.bottomRightColumnIdx() - 1;
        int bottomRightRowIdx = boundsIdxStartingAtOne.bottomRightRowIdx() - 1;

        Collection<SudokuPosition> affectedCells = new ArrayList<>();
        for (int columnIdx = topLeftColumnIdx; columnIdx <= bottomRightColumnIdx; columnIdx++) {
            for (int rowIdx = topLeftRowIdx; rowIdx <= bottomRightRowIdx; rowIdx++) {
                affectedCells.add(new SudokuPosition(rowIdx, columnIdx));
            }
        }
        return new KillerConstraint(affectedCells, total);

    }
}
