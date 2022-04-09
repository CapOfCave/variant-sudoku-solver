package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.utils.SudokuCollectionUtils;
import me.kecker.sudokusolver.utils.SudokuPosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromeConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.funwithpuzzles.com/2015/02/palindrome-sudoku-daily-sudoku-league-92.html">here</a>
     * with solution taken from <a href="https://www.funwithpuzzles.com/2010/05/hindu-newspapers-sudoku-of-day-google.html">here</a>
     */
    @Test
    void testPalindromeSudoku() {

        List<List<SudokuPosition>> palindromes = List.of(
                List.of(
                        new SudokuPosition(1, 2),
                        new SudokuPosition(2, 2),
                        new SudokuPosition(2, 3),
                        new SudokuPosition(3, 3),
                        new SudokuPosition(4, 3),
                        new SudokuPosition(4, 2),
                        new SudokuPosition(5, 2),
                        new SudokuPosition(5, 1),
                        new SudokuPosition(6, 1)
                ),
                List.of(
                        new SudokuPosition(2, 5),
                        new SudokuPosition(2, 6),
                        new SudokuPosition(1, 6),
                        new SudokuPosition(1, 7),
                        new SudokuPosition(1, 8)
                ),
                List.of(
                        new SudokuPosition(7, 4),
                        new SudokuPosition(8, 4),
                        new SudokuPosition(9, 4),
                        new SudokuPosition(9, 3),
                        new SudokuPosition(9, 2)
                ),
                List.of(
                        new SudokuPosition(6, 6),
                        new SudokuPosition(6, 7),
                        new SudokuPosition(7, 7),
                        new SudokuPosition(7, 8),
                        new SudokuPosition(8, 8)
                        )
                );


        int[][] board = {
                {8, 0, 0, 0, 7, 0, 0, 0, 3},
                {0, 0, 0, 2, 0, 0, 0, 8, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {9, 0, 0, 0, 5, 0, 7, 0, 0},
                {0, 0, 5, 4, 6, 7, 9, 0, 0},
                {0, 0, 6, 0, 8, 0, 0, 0, 4},
                {0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 8, 0, 0, 0},
                {3, 0, 0, 0, 2, 0, 0, 0, 6},
        };
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraints(palindromes.stream().map(SudokuCollectionUtils::startingAtOne).map(PalindromeConstraint::new).toList())
                .withGivenDigitsFromIntArray(board)
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        int[][] solution = {
                {8, 2, 9, 6, 7, 1, 5, 4, 3},
                {7, 1, 3, 2, 4, 5, 6, 8, 9},
                {6, 5, 4, 8, 9, 3, 1, 7, 2},
                {9, 4, 8, 3, 5, 2, 7, 6, 1},
                {1, 3, 5, 4, 6, 7, 9, 2, 8},
                {2, 7, 6, 1, 8, 9, 3, 5, 4},
                {5, 8, 2, 9, 1, 6, 4, 3, 7},
                {4, 6, 1, 7, 3, 8, 2, 9, 5},
                {3, 9, 7, 5, 2, 4, 8, 1, 6}
        };
        assertSolved(solve, solution);

    }
}