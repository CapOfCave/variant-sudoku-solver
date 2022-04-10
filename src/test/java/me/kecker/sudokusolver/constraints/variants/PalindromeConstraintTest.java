package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.dtos.Position;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.utils.SudokuCollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;

class PalindromeConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.funwithpuzzles.com/2015/02/palindrome-sudoku-daily-sudoku-league-92.html">here</a>
     * with solution taken from <a href="https://www.funwithpuzzles.com/2010/05/hindu-newspapers-sudoku-of-day-google.html">here</a>
     */
    @Test
    void testPalindromeSudoku() {

        List<List<Position>> palindromes = List.of(
                List.of(
                        new Position(1, 2),
                        new Position(2, 2),
                        new Position(2, 3),
                        new Position(3, 3),
                        new Position(4, 3),
                        new Position(4, 2),
                        new Position(5, 2),
                        new Position(5, 1),
                        new Position(6, 1)
                ),
                List.of(
                        new Position(2, 5),
                        new Position(2, 6),
                        new Position(1, 6),
                        new Position(1, 7),
                        new Position(1, 8)
                ),
                List.of(
                        new Position(7, 4),
                        new Position(8, 4),
                        new Position(9, 4),
                        new Position(9, 3),
                        new Position(9, 2)
                ),
                List.of(
                        new Position(6, 6),
                        new Position(6, 7),
                        new Position(7, 7),
                        new Position(7, 8),
                        new Position(8, 8)
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
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraints(palindromes.stream().map(SudokuCollectionUtils::startingAtOne).map(PalindromeConstraint::new).toList())
                .withGivenDigitsFromIntArray(board)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

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