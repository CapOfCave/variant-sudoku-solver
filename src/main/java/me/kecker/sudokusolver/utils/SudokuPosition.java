package me.kecker.sudokusolver.utils;

public record SudokuPosition(int rowIdx, int columnIdx) {
    public SudokuPosition add(Offset offset) {
        return new SudokuPosition(rowIdx + offset.rowDif(), columnIdx + offset.columnDif());
    }

    public SudokuPosition subtract(Offset offset) {
        return new SudokuPosition(rowIdx - offset.rowDif(), columnIdx - offset.columnDif());

    }
}
