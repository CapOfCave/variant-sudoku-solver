package me.kecker.sudokusolver.utils;

public record Rect(
        int topLeftColumnIdx,
        int topLeftRowIdx,
        int bottomRightColumnIdx,
        int bottomRightRowIdx) {

}
