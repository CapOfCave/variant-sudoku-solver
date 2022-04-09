package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.RelativeSeenCellConstraint;
import me.kecker.sudokusolver.utils.Offset;

import java.util.Collection;
import java.util.stream.IntStream;

/**
 * Applies the Knight Constraint to all fields
 * | |O| |O| |
 * |O| | | |O|
 * | | |X| | |
 * |O| | | |O|
 * | |O| |O| |
 */
public class KnightSudokuConstraint extends RelativeSeenCellConstraint {

    // a chess knight can move to all fields with a distance of exactly 5 units
    private static final Collection<Offset> offsets = IntStream.range(0, 25)
            .mapToObj(i -> new Offset(i / 5 - 2, i % 5 - 2))
            .filter(offset -> offset.columnDif() * offset.columnDif() + offset.rowDif() * offset.rowDif() == 5)
            .toList();

    public KnightSudokuConstraint() {
        super(offsets);
    }

}