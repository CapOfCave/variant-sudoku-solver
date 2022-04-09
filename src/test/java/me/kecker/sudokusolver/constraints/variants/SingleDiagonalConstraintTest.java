package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleDiagonalConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.youtube.com/watch?v=i1E7MUhT5C4">here</a>
     */
    @Test
    void testDiagonalSudoku() {
        int[][] board = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 7, 9, 6, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 3, 0},
                {3, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 4, 0, 0, 0, 0, 0, 2, 0},
                {0, 5, 0, 0, 0, 0, 0, 0, 7},
                {0, 6, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 7, 8, 9, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withNonRepeatingMainDiagonals()
                .withGivenDigitsFromIntArray(board)
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {7, 8, 6, 3, 5, 1, 2, 4, 9},
                {1, 3, 4, 2, 7, 9, 6, 5, 8},
                {2, 9, 5, 4, 6, 8, 7, 3, 1},
                {3, 7, 2, 9, 8, 4, 5, 1, 6},
                {6, 4, 8, 7, 1, 5, 9, 2, 3},
                {9, 5, 1, 6, 3, 2, 4, 8, 7},
                {5, 6, 3, 1, 4, 7, 8, 9, 2},
                {4, 2, 7, 8, 9, 3, 1, 6, 5},
                {8, 1, 9, 5, 2, 6, 3, 7, 4},
        };
        assertSolved(solve, solution);

    }
}