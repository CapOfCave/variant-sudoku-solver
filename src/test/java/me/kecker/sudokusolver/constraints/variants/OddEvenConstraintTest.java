package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OddEvenConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.gmpuzzles.com/images/puzzles2022/220219-EvenOddSudoku-Percentage.pdf">here</a>
     * with solution taken from <a href="https://www.gmpuzzles.com/images/puzzles2022/220219-EvenOddSudoku-Percentage-soln.pdf">here</a>
     */
    @Test
    void testOddEvenConstraint() {

        int[][] board = {
                {0, 0, 0, 0, 0, 3, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 8, 0},
                {0, 0, 1, 0, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 0, 6, 0, 0, 0},
                {9, 0, 0, 0, 5, 0, 0, 0, 8},
                {0, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 6, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 7, 0, 0, 0, 0, 0}
        };

        char[][] evenOdd = {
                {'.', '.', 'o', '.', '.', '.', '.', '.', '.'},
                {'.', 'o', '.', 'o', '.', '.', '.', '.', '.'},
                {'o', '.', '.', '.', 'o', '.', '.', '.', '.'},
                {'.', 'o', '.', 'o', '.', '.', '.', '.', '.'},
                {'.', '.', 'o', '.', '.', '.', 'e', '.', '.'},
                {'.', '.', '.', '.', '.', 'e', '.', 'e', '.'},
                {'.', '.', '.', '.', 'e', '.', '.', '.', 'e'},
                {'.', '.', '.', '.', '.', 'e', '.', 'e', '.'},
                {'.', '.', '.', '.', '.', '.', 'e', '.', '.'}

        };

        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(board)
                .withEvenOddConstraintsFromCharArray(evenOdd)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {4, 7, 5, 8, 6, 3, 2, 1, 9},
                {6, 9, 2, 5, 1, 7, 3, 8, 4},
                {3, 8, 1, 2, 9, 4, 7, 5, 6},
                {2, 1, 4, 9, 8, 6, 5, 3, 7},
                {9, 6, 7, 3, 5, 1, 4, 2, 8},
                {5, 3, 8, 4, 7, 2, 9, 6, 1},
                {8, 5, 3, 1, 4, 9, 6, 7, 2},
                {7, 2, 9, 6, 3, 8, 1, 4, 5},
                {1, 4, 6, 7, 2, 5, 8, 9, 3}
        };
        assertSolved(solve, solution);
    }
}
