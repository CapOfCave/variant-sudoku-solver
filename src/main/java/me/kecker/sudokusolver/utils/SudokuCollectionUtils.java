package me.kecker.sudokusolver.utils;

import me.kecker.sudokusolver.dtos.Position;

import java.util.Collection;
import java.util.List;

public class SudokuCollectionUtils {

    public static List<Position> startingAtOne(Collection<Position> positions) {
        return positions.stream().map(SudokuCollectionUtils::startingAtOne).toList();
    }

    public static Position startingAtOne(Position position) {
        return new Position(position.rowIdx() - 1, position.columnIdx() - 1 );
    }
}
