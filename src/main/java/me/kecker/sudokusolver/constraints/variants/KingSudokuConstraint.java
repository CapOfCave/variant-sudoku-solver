package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.RelativeSeenCellConstraint;
import me.kecker.sudokusolver.utils.Offset;

import java.util.Collection;
import java.util.stream.IntStream;

/**
 * Applies the King Constraint to all fields
 * | | | | | |
 * | |O|O|O| |
 * | |O|X|O| |
 * | |O|O|O| |
 * | | | | | |
 */
public class KingSudokuConstraint extends RelativeSeenCellConstraint {

    private static final Collection<Offset> offsets = IntStream.range(0, 8)
            .filter(i -> i != 4) // don't include the cell itself
            .mapToObj(i -> new Offset(i / 3 - 1, i % 3 - 1))
            .toList();

    public KingSudokuConstraint() {
        super(offsets);
    }

}
