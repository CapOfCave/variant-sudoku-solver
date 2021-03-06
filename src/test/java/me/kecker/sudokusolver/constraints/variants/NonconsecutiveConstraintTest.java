package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;

class NonconsecutiveConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.puzzlemix.com/playgrid.php?id=19693&type=consecutive&ptype=all">here</a>
     * with solution seen in <a href="https://www.youtube.com/watch?v=5DlMEgPnxRs">this video</a>
     */
    @Test
    void testNonConsecutiveSudoku() {
        int[][] board = {
                {5, 0, 0, 0, 0, 0, 0, 0, 4},
                {0, 0, 6, 0, 1, 0, 5, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 8, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 0, 7, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 0, 9, 0},
                {0, 0, 7, 0, 9, 0, 8, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 7},
        };
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new NonconsecutiveConstraint())
                .withGivenDigitsFromIntArray(board)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {5, 3, 9, 6, 8, 2, 7, 1, 4},
                {2, 8, 6, 4, 1, 7, 5, 3, 9},
                {7, 1, 4, 9, 3, 5, 2, 8, 6},
                {4, 7, 1, 3, 5, 9, 6, 2, 8},
                {9, 5, 3, 8, 2, 6, 4, 7, 1},
                {6, 2, 8, 1, 7, 4, 9, 5, 3},
                {8, 6, 2, 7, 4, 1, 3, 9, 5},
                {1, 4, 7, 5, 9, 3, 8, 6, 2},
                {3, 9, 5, 2, 6, 8, 1, 4, 7},
        };
        assertSolved(solve, solution);
    }

    /**
     * Sudoku taken from <a href="https://cracking-the-cryptic.web.app/sudoku/tjN9LtrrTL">here</a>
     * with solution seen in <a href="https://www.youtube.com/watch?v=yKf9aUIxdb4">this video</a>
     */
    @Test
    void testMiracleSudoku() {

        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new NonconsecutiveConstraint())
                .withConstraint(new KingSudokuConstraint())
                .withConstraint(new KnightSudokuConstraint())
                .withGivenDigit(5, 3, 1)
                .withGivenDigit(6, 7, 2)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {4, 8, 3, 7, 2, 6, 1, 5, 9},
                {7, 2, 6, 1, 5, 9, 4, 8, 3},
                {1, 5, 9, 4, 8, 3, 7, 2, 6},
                {8, 3, 7, 2, 6, 1, 5, 9, 4},
                {2, 6, 1, 5, 9, 4, 8, 3, 7},
                {5, 9, 4, 8, 3, 7, 2, 6, 1},
                {3, 7, 2, 6, 1, 5, 9, 4, 8},
                {6, 1, 5, 9, 4, 8, 3, 7, 2},
                {9, 4, 8, 3, 7, 2, 6, 1, 5},
        };
        assertSolved(solve, solution);
    }


}