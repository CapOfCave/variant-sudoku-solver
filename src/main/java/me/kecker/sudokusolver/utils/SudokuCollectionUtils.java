package me.kecker.sudokusolver.utils;

import java.util.List;

public class SudokuCollectionUtils {

    public static List<Position> startingAtOne(List<Position> values) {
        return values.stream().map(position -> new Position(position.rowIdx() - 1, position.columnIdx() - 1 )).toList();
    }
}
