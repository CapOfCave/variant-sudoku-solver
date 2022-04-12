package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.base.WhispersLine;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;

class GermanWhispersLineTest {

    /**
     * Sudoku taken from <a href="https://www.youtube.com/watch?v=nH3vat8z9uM">this video</a>
     */
    @Test
    void testGermanWhisperSudoku() {
        int[][] board = {
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 3, 0, 0, 0, 0},
        };
        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(board)
                .withConstraint(new GermanWhispersLine(List.of(
                        rc(6, 3), rc(5, 2), rc(4, 3), rc(3, 4), rc(2, 5), rc(1, 6), rc(1, 7), rc(2, 8),
                        rc(3, 8), rc(4, 7), rc(5, 6), rc(6, 6), rc(7, 6), rc(8, 5), rc(7, 6))))
                .withConstraint(new GermanWhispersLine(List.of(rc(4, 5), rc(4, 6), rc(3, 7))))
                .withConstraint(new GermanWhispersLine(List.of(rc(8, 1), rc(7, 1), rc(7, 2), rc(8, 3), rc(9, 3), rc(9, 2))))
                .withConstraint(new GermanWhispersLine(List.of(rc(9, 6), rc(8, 7), rc(7, 7), rc(7, 8), rc(6, 9), rc(5, 8))))
                .solve()
                .withExactlyOneSolution();

        int[][] solvedBoard = {
                {7, 9, 6, 4, 1, 3, 8, 5, 2},
                {3, 5, 2, 6, 8, 9, 4, 1, 7},
                {1, 8, 4, 2, 7, 5, 6, 9, 3},
                {2, 4, 7, 5, 9, 1, 3, 8, 6},
                {6, 1, 5, 3, 4, 8, 2, 7, 9},
                {8, 3, 9, 7, 6, 2, 5, 4, 1},
                {9, 2, 3, 8, 5, 7, 1, 6, 4},
                {4, 7, 8, 1, 2, 6, 9, 3, 5},
                {5, 6, 1, 9, 3, 4, 7, 2, 8},
        };
        assertSolved(solution, solvedBoard);

    }

    /**
     * Sudoku taken from <a href="https://www.youtube.com/watch?v=6pAQYHf42Ik">this video</a>
     */
    @Test
    void testDutchWhisperSudoku() {

        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigit(rc(7, 1), 5)
                .withGivenDigit(rc(7, 2), 1)
                .withGivenDigit(rc(7, 3), 9)
                .withConstraint(new WhispersLine(4, List.of(
                        rc(9, 1), rc(8, 2), rc(7, 3), rc(6, 4), rc(5, 5), rc(4, 6), rc(3, 7), rc(2, 8), rc(1, 9),
                        rc(1, 8), rc(1, 7), rc(1, 6), rc(1, 5), rc(1, 4), rc(1, 3), rc(1, 2), rc(1, 1),
                        rc(2, 2), rc(3, 3), rc(4, 4), rc(5, 5), rc(6, 6), rc(7, 7), rc(8, 8), rc(9, 9)
                )))
                .withConstraint(new KingSudokuConstraint())
                .withNonRepeatingMainDiagonals()
                .solve()
                .withExactlyOneSolution();

        int[][] solvedBoard = {
                {4, 8, 1, 6, 2, 7, 3, 9, 5},
                {6, 9, 7, 3, 4, 5, 2, 1, 8},
                {3, 2, 5, 8, 9, 1, 6, 4, 7},
                {9, 6, 4, 1, 7, 2, 8, 5, 3},
                {1, 5, 3, 9, 8, 6, 4, 7, 2},
                {2, 7, 8, 4, 5, 3, 9, 6, 1},
                {5, 1, 9, 2, 6, 8, 7, 3, 4},
                {8, 3, 6, 7, 1, 4, 5, 2, 9},
                {7, 4, 2, 5, 3, 9, 1, 8, 6},
        };
        assertSolved(solution, solvedBoard);

    }
}