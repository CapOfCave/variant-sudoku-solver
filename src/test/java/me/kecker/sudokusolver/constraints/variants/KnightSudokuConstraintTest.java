package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;

class KnightSudokuConstraintTest {

    /**
     * Sudoku taken from <a href="https://cracking-the-cryptic.web.app/sudoku/3dqHL3pjfr">here</a>
     * with solution seen in <a href="https://www.youtube.com/watch?v=5DlMEgPnxRs">this video</a>
     */
    @Test
    void testKnightSudoku() {
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new KnightSudokuConstraint())
                .withGivenDigit(1, 1, 7)
                .withGivenDigit(1, 4, 2)
                .withGivenDigit(2, 7, 6)
                .withGivenDigit(4, 2, 3)
                .withGivenDigit(4, 8, 8)
                .withGivenDigit(6, 1, 9)
                .withGivenDigit(6, 2, 5)
                .withGivenDigit(6, 8, 4)
                .withGivenDigit(6, 9, 3)
                .withGivenDigit(7, 1, 3)
                .withGivenDigit(7, 8, 9)
                .withGivenDigit(7, 9, 8)
                .withGivenDigit(8, 3, 1)
                .withGivenDigit(8, 7, 2)
                .withGivenDigit(9, 1, 5)
                .withGivenDigit(9, 4, 7)
                .withGivenDigit(9, 6, 8)
                .withGivenDigit(9, 9, 4)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {7, 1, 5, 2, 8, 6, 4, 3, 9},
                {2, 8, 9, 3, 7, 4, 6, 5, 1},
                {4, 6, 3, 9, 5, 1, 8, 2, 7},
                {6, 3, 7, 4, 1, 5, 9, 8, 2},
                {1, 2, 4, 8, 3, 9, 7, 6, 5},
                {9, 5, 8, 6, 2, 7, 1, 4, 3},
                {3, 7, 6, 1, 4, 2, 5, 9, 8},
                {8, 4, 1, 5, 9, 3, 2, 7, 6},
                {5, 9, 2, 7, 6, 8, 3, 1, 4},
        };
        assertSolved(solve, solution);

    }
}
