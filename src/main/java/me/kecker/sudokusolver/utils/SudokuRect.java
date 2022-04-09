package me.kecker.sudokusolver.utils;

public record SudokuRect(
        int topLeftColumnIdx,
        int topLeftRowIdx,
        int bottomRightColumnIdx,
        int bottomRightRowIdx) {

}
