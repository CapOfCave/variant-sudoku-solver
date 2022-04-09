package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.*;

class NonconsecutiveConstraintTest {

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
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new NonconsecutiveConstraint())
                .withGivenDigitsFromIntArray(board)
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
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

    @Test
    void testMiracleSudoku() {

        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new NonconsecutiveConstraint())
                .withConstraint(new KingSudokuConstraint())
                .withConstraint(new KnightSudokuConstraint())
                .withGivenDigit(5, 3, 1 )
                .withGivenDigit(6, 7, 2 )
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
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