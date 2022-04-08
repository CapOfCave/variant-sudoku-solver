package me.kecker.sudokusolver.utils;

public class SudokuSolverUtils {

    public static boolean isSquare(int value) {
        int squared = (int) Math.sqrt(value);
        return value == squared * squared;
    }
}
