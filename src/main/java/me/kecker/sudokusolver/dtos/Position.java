package me.kecker.sudokusolver.dtos;

public record Position(int rowIdx, int columnIdx) {
    public Position add(Offset offset) {
        return new Position(rowIdx + offset.rowDif(), columnIdx + offset.columnDif());
    }

    public Position subtract(Offset offset) {
        return new Position(rowIdx - offset.rowDif(), columnIdx - offset.columnDif());

    }
}
