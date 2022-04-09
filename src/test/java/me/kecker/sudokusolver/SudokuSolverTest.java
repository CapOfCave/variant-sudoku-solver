package me.kecker.sudokusolver;

import com.google.ortools.sat.CpSolverStatus;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuSolverTest {

    /**
     * Sudoku taken from <a href="https://www.researchgate.net/figure/A-Sudoku-with-17-clues-and-its-unique-solution_fig1_311250094">here</a>
     */
    @Test
    void testNormalSudokuRules() {
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigit(1, 4, 8)
                .withGivenDigit(1, 6, 1)
                .withGivenDigit(2, 8, 4)
                .withGivenDigit(2, 9, 3)
                .withGivenDigit(3, 1, 5)
                .withGivenDigit(4, 5, 7)
                .withGivenDigit(4, 7, 8)
                .withGivenDigit(5, 7, 1)
                .withGivenDigit(6, 2, 2)
                .withGivenDigit(6, 5, 3)
                .withGivenDigit(7, 1, 6)
                .withGivenDigit(7, 8, 7)
                .withGivenDigit(7, 9, 5)
                .withGivenDigit(8, 3, 3)
                .withGivenDigit(8, 4, 4)
                .withGivenDigit(9, 4, 2)
                .withGivenDigit(9, 7, 6)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        int[][] solution = {
                {2, 3, 7, 8, 4, 1, 5, 6, 9},
                {1, 8, 6, 7, 9, 5, 2, 4, 3},
                {5, 9, 4, 3, 2, 6, 7, 1, 8},
                {3, 1, 5, 6, 7, 4, 8, 9, 2},
                {4, 6, 9, 5, 8, 2, 1, 3, 7},
                {7, 2, 8, 1, 3, 9, 4, 5, 6},
                {6, 4, 2, 9, 1, 8, 3, 7, 5},
                {8, 5, 3, 4, 6, 7, 9, 2, 1},
                {9, 7, 1, 2, 5, 3, 6, 8, 4},
        };
        assertSolved(solve, solution);
    }
}