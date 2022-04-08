package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.CompositeConstraint;
import me.kecker.sudokusolver.constraints.base.FixedSumConstraint;
import me.kecker.sudokusolver.constraints.base.UniqueConstraint;
import me.kecker.sudokusolver.utils.Position;
import me.kecker.sudokusolver.utils.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KillerConstraint extends CompositeConstraint {

    public KillerConstraint(Collection<Position> affectedCells, int total) {
        super(List.of(
                new FixedSumConstraint(affectedCells, total),
                new UniqueConstraint(affectedCells)));
    }

    public static KillerConstraint rectangularCage(Rect boundsIdxStartingAtOne, int total) {
        int topLeftColumnIdx = boundsIdxStartingAtOne.topLeftColumnIdx() - 1;
        int topLeftRowIdx = boundsIdxStartingAtOne.topLeftRowIdx() - 1;
        int bottomRightColumnIdx = boundsIdxStartingAtOne.bottomRightColumnIdx() - 1;
        int bottomRightRowIdx = boundsIdxStartingAtOne.bottomRightRowIdx() - 1;

        Collection<Position> affectedCells = new ArrayList<>();
        for (int columnIdx = topLeftColumnIdx; columnIdx <= bottomRightColumnIdx; columnIdx++) {
            for (int rowIdx = topLeftRowIdx; rowIdx <= bottomRightRowIdx; rowIdx++) {
                affectedCells.add(new Position(rowIdx, columnIdx));
            }
        }
        return new KillerConstraint(affectedCells, total);

    }
}
