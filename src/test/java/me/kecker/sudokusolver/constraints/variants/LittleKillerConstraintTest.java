package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.constraints.variants.LittleKillerConstraint.LittleKillerDirection.DOWN_LEFT;
import static me.kecker.sudokusolver.constraints.variants.LittleKillerConstraint.LittleKillerDirection.DOWN_RIGHT;
import static me.kecker.sudokusolver.constraints.variants.LittleKillerConstraint.LittleKillerDirection.UP_LEFT;
import static me.kecker.sudokusolver.constraints.variants.LittleKillerConstraint.LittleKillerDirection.UP_RIGHT;
import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LittleKillerConstraintTest {

    /**
     * Sudoku taken from <a href="http://www.cross-plus-a.com/html/cros7lksd.htm">here</a>
     */
    @Test
    void testLittleKillerSudoku() {
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withLittleKillerConstraint(2, 1, DOWN_RIGHT, 41)
                .withLittleKillerConstraint(6, 1, DOWN_RIGHT, 19)
                .withLittleKillerConstraint(7, 1, DOWN_RIGHT, 19)
                .withLittleKillerConstraint(8, 1, DOWN_RIGHT, 12)
                .withLittleKillerConstraint(9, 1, DOWN_RIGHT, 8)
                .withLittleKillerConstraint(9, 2, UP_RIGHT, 32)
                .withLittleKillerConstraint(9, 3, UP_RIGHT, 33)
                .withLittleKillerConstraint(9, 7, UP_RIGHT, 9)
                .withLittleKillerConstraint(9, 8, UP_RIGHT, 13)
                .withLittleKillerConstraint(8, 9, UP_LEFT, 30)
                .withLittleKillerConstraint(4, 9, UP_LEFT, 25)
                .withLittleKillerConstraint(3, 9, UP_LEFT, 9)
                .withLittleKillerConstraint(2, 9, UP_LEFT, 10)
                .withLittleKillerConstraint(1, 9, UP_LEFT, 8)
                .withLittleKillerConstraint(1, 8, DOWN_LEFT, 51)
                .withLittleKillerConstraint(1, 7, DOWN_LEFT, 47)
                .withLittleKillerConstraint(1, 3, DOWN_LEFT, 12)
                .withLittleKillerConstraint(1, 2, DOWN_LEFT, 11)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {1, 6, 3, 2, 5, 7, 4, 9, 8},
                {5, 7, 8, 3, 4, 9, 6, 2, 1},
                {2, 4, 9, 1, 6, 8, 7, 5, 3},
                {3, 1, 5, 8, 2, 4, 9, 6, 7},
                {4, 2, 6, 9, 7, 3, 1, 8, 5},
                {9, 8, 7, 5, 1, 6, 3, 4, 2},
                {6, 3, 2, 7, 9, 5, 8, 1, 4},
                {7, 9, 1, 4, 8, 2, 5, 3, 6},
                {8, 5, 4, 6, 3, 1, 2, 7, 9}
        };
        assertSolved(solve, solution);
    }
}