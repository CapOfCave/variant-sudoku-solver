package me.kecker.sudokusolver.dtos;

import java.util.stream.Stream;

public record Pair(Position position1, Position position2) {
    public static Pair of(Position position1, Position position2) {
        return new Pair(position1, position2);
    }

    public Stream<Position> stream() {
        return Stream.of(position1, position2);
    }
}
