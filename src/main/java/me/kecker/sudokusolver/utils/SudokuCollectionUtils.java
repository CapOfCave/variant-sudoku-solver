package me.kecker.sudokusolver.utils;

import java.util.List;

public class SudokuCollectionUtils {

    public static List<SudokuPosition> startingAtOne(List<SudokuPosition> values) {
        return values.stream().map(position -> new SudokuPosition(position.rowIdx() - 1, position.columnIdx() - 1 )).toList();
    }
}
