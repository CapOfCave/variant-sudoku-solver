package me.kecker.sudokusolver.dtos;

public record Rect(
        int topLeftColumnIdx,
        int topLeftRowIdx,
        int bottomRightColumnIdx,
        int bottomRightRowIdx) {
}
