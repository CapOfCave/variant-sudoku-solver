package me.kecker.sudokusolver.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSolverUtilsTest {

    @Test
    void testIsSquareReturnsTrueForSquare() {
        assertTrue(SudokuSolverUtils.isSquare(144));
    }

    @Test
    void testIsSquareReturnsFalseForNonSquare() {
        assertFalse(SudokuSolverUtils.isSquare(143));
    }

    @Test
    void testIsSquareReturnsFalseForNegativeNonSquare() {
        assertFalse(SudokuSolverUtils.isSquare(-10));
    }

    @Test
    void testIsSquareReturnsFalseForNegativeSquare() {
        assertFalse(SudokuSolverUtils.isSquare(-36));
    }

    @Test
    void testIsSquareReturnsTrueForZero() {
        assertTrue(SudokuSolverUtils.isSquare(0));
    }
}