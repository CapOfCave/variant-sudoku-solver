package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.result.SolutionSet;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.dtos.Offset;
import me.kecker.sudokusolver.dtos.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.*;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.startingAtOne;

class CloneConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.funwithpuzzles.com/2015/06/clone-sudoku.html">here</a>
     * with solution taken from <a href="https://www.funwithpuzzles.com/2015/05/tens-sudoku.html">here</a>
     */
    @Test
    void testCloneSudoku() {
        int[][] board = {
                {0, 0, 0, 0, 7, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 6, 0, 2},
                {0, 0, 0, 0, 1, 6, 0, 0, 0},
                {0, 0, 0, 0, 8, 0, 0, 9, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 2, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 8, 3, 0, 0, 0, 0},
                {4, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 4, 0, 0, 0, 0},
        };
        SolutionSet solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(board)
                .withConstraint(new CloneConstraint(startingAtOne(List.of(
                        new Position(1, 1),
                        new Position(1, 2),
                        new Position(1, 3),
                        new Position(1, 4),
                        new Position(2, 1),
                        new Position(3, 1),
                        new Position(3, 2),
                        new Position(3, 3),
                        new Position(4, 1),
                        new Position(5, 1),
                        new Position(5, 2),
                        new Position(5, 3),
                        new Position(5, 4)
                )), new Offset(4, 5)))
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {3, 8, 6, 4, 7, 2, 5, 1, 9},
                {9, 1, 7, 3, 5, 8, 6, 4, 2},
                {5, 4, 2, 9, 1, 6, 7, 3, 8},
                {7, 6, 3, 1, 8, 4, 2, 9, 5},
                {1, 9, 5, 7, 2, 3, 8, 6, 4},
                {8, 2, 4, 5, 6, 9, 1, 7, 3},
                {6, 7, 9, 8, 3, 5, 4, 2, 1},
                {4, 5, 1, 2, 9, 7, 3, 8, 6},
                {2, 3, 8, 6, 4, 1, 9, 5, 7},
        };
        assertSolved(solve, solution);
    }

}