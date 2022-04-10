package me.kecker.sudokusolver.utils;

import java.util.Collection;
import java.util.List;

public class SudokuCollectionUtils {

    public static List<SudokuPosition> startingAtOne(Collection<SudokuPosition> positions) {
        return positions.stream().map(SudokuCollectionUtils::startingAtOne).toList();
    }

    public static SudokuPosition startingAtOne(SudokuPosition position) {
        return new SudokuPosition(position.rowIdx() - 1, position.columnIdx() - 1 );
    }
}
