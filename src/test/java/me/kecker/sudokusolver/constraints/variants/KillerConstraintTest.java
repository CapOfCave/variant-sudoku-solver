package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.utils.Position;
import me.kecker.sudokusolver.utils.Rect;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.startingAtOne;
import static org.junit.jupiter.api.Assertions.*;

class KillerConstraintTest {


    /**
     * Sudoku taken from <a href="https://app.crackingthecryptic.com/sudoku/T6BLmj2GQ9">here</a>
     */
    @Test
    void testKillerSudoku() {

        var killer23 = new KillerConstraint(startingAtOne(List.of(
                new Position(3, 7),
                new Position(4, 7),
                new Position(4, 6)
        )), 23);
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(KillerConstraint.rectangularCage(new Rect(1, 1, 1, 2), 14))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(1, 3, 1, 4), 14))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(2, 1, 5, 1), 28))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(3, 3, 3, 7), 33))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(4, 7, 4, 8), 15))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(5, 3, 5, 7), 17))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(4, 9, 6, 9), 8))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(6, 6, 7, 6), 6))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(8, 6, 9, 6), 6))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(7, 8, 7, 9), 6))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(9, 2, 9, 5), 12))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(8, 9, 9, 9), 15))
                .withConstraint(killer23)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        assertEquals(6, solve.value(1, 1));
        assertEquals(8, solve.value(2, 1));
        assertEquals(8, solve.value(3, 7));
        assertEquals(6, solve.value(4, 7));
        assertEquals(9, solve.value(4, 6));

        solve.printBoard();

    }
}