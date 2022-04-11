package me.kecker.sudokusolver.dtos;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * An unordered Pair of two cells
 */
public record Pair(Position position1, Position position2) {
    public static Pair of(Position position1, Position position2) {
        return new Pair(position1, position2);
    }

    public Stream<Position> stream() {
        return Stream.of(position1, position2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        if (Objects.equals(position1, pair.position1) && Objects.equals(position2, pair.position2)) return true;
        return Objects.equals(position1, pair.position2) && Objects.equals(position2, pair.position1);
    }

    @Override
    public int hashCode() {
        // use addition as a commutative operator
        return position1.hashCode() + position2.hashCode();
    }
}
