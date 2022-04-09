package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.utils.SudokuPosition;
import me.kecker.sudokusolver.utils.SudokuRect;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.startingAtOne;
import static org.junit.jupiter.api.Assertions.*;

class KillerConstraintTest {


    /**
     * Sudoku taken from <a href="https://app.crackingthecryptic.com/sudoku/T6BLmj2GQ9">here</a>
     */
    @Test
    void testKillerSudoku() {

        var killer23 = new KillerConstraint(startingAtOne(List.of(
                new SudokuPosition(3, 7),
                new SudokuPosition(4, 7),
                new SudokuPosition(4, 6)
        )), 23);
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(1, 1, 1, 2), 14))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(1, 3, 1, 4), 14))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(2, 1, 5, 1), 28))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(3, 3, 3, 7), 33))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(4, 7, 4, 8), 15))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(5, 3, 5, 7), 17))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(4, 9, 6, 9), 8))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(6, 6, 7, 6), 6))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(8, 6, 9, 6), 6))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(7, 8, 7, 9), 6))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(9, 2, 9, 5), 12))
                .withConstraint(KillerConstraint.rectangularCage(new SudokuRect(8, 9, 9, 9), 15))
                .withConstraint(killer23)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {6, 7, 4, 8, 9, 2, 3, 1, 5},
                {8, 5, 1, 3, 7, 6, 9, 4, 2},
                {9, 2, 3, 5, 1, 4, 8, 7, 6},
                {5, 4, 7, 2, 3, 9, 6, 8, 1},
                {2, 1, 6, 4, 5, 8, 7, 9, 3},
                {3, 9, 8, 7, 6, 1, 5, 2, 4},
                {4, 3, 9, 6, 2, 7, 1, 5, 8},
                {1, 6, 2, 9, 8, 5, 4, 3, 7},
                {7, 8, 5, 1, 4, 3, 2, 6, 9},
        };
        assertSolved(solve, solution);
    }
}