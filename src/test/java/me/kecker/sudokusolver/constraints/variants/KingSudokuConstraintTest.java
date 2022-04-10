package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KingSudokuConstraintTest {

    /**
     * Sudoku taken from <a href="https://cracking-the-cryptic.web.app/sudoku/3dqHL3pjfr">here</a>
     * with solution seen in <a href="https://www.youtube.com/watch?v=5DlMEgPnxRs">this video</a>
     */
    @Test
    void testKingSudoku() {
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new KingSudokuConstraint())
                .withGivenDigit(1, 1, 9)
                .withGivenDigit(1, 9, 5)
                .withGivenDigit(2, 3, 7)
                .withGivenDigit(2, 4, 9)
                .withGivenDigit(2, 6, 1)
                .withGivenDigit(2, 7, 4)
                .withGivenDigit(2, 8, 2)
                .withGivenDigit(3, 2, 8)
                .withGivenDigit(3, 8, 1)
                .withGivenDigit(4, 2, 2)
                .withGivenDigit(4, 5, 7)
                .withGivenDigit(4, 8, 3)
                .withGivenDigit(5, 4, 6)
                .withGivenDigit(5, 6, 4)
                .withGivenDigit(6, 2, 3)
                .withGivenDigit(6, 5, 2)
                .withGivenDigit(6, 8, 7)
                .withGivenDigit(7, 2, 5)
                .withGivenDigit(7, 8, 9)
                .withGivenDigit(8, 3, 9)
                .withGivenDigit(8, 4, 5)
                .withGivenDigit(8, 6, 6)
                .withGivenDigit(8, 7, 2)
                .withGivenDigit(9, 1, 1)
                .withGivenDigit(9, 9, 6)
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        int[][] solution = {
                {9, 1, 2, 4, 8, 7, 3, 6, 5},
                {3, 6, 7, 9, 5, 1, 4, 2, 8},
                {5, 8, 4, 3, 6, 2, 9, 1, 7},
                {4, 2, 1, 8, 7, 5, 6, 3, 9},
                {7, 9, 5, 6, 3, 4, 1, 8, 2},
                {6, 3, 8, 1, 2, 9, 5, 7, 4},
                {2, 5, 6, 7, 4, 3, 8, 9, 1},
                {8, 7, 9, 5, 1, 6, 2, 4, 3},
                {1, 4, 3, 2, 9, 8, 7, 5, 6},
        };
        assertSolved(solve, solution);

    }
}
