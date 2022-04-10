package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DisjointGroupsConstraintsTest {

    /**
     * Sudoku taken from <a href="https://www.funwithpuzzles.com/2016/02/mini-disjoint-groups-sudoku.html">here</a>
     * with solution taken from <a href="https://www.funwithpuzzles.com/2013/01/classic-sudoku-4-online-google-gadget.html">here</a>
     */
    @Test
    void testDisjointGroupsSudoku6by6() {
        int[][] board = {
                {1, 0, 0, 0, 0, 6},
                {0, 3, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 4},
                {2, 0, 0, 0, 0, 0},
                {0, 0, 5, 0, 6, 0},
                {6, 0, 0, 0, 0, 1}
        };
        Solution solve = SudokuSolver.fromBoard(new Board(3, 2, 2, 3, 1, 6))
                .withNormalSudokuRulesConstraints()
                .withGivenDigitsFromIntArray(board)
                .withConstraint(new DisjointGroupsConstraints())
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        int[][] solution = {
                {1, 4, 2, 5, 3, 6},
                {5, 3, 6, 1, 4, 2},
                {3, 5, 1, 6, 2, 4},
                {2, 6, 4, 3, 1, 5},
                {4, 1, 5, 2, 6, 3},
                {6, 2, 3, 4, 5, 1},
        };
        assertSolved(solve, solution);

    }

    /**
     * Sudoku taken from <a href="https://www.funwithpuzzles.com/2017/02/disjoint-groups-sudoku-puzzle.html">here</a>
     * with solution taken from <a href="https://www.funwithpuzzles.com/2016/02/mini-disjoint-groups-sudoku.html">here</a>
     */
    @Test
    void testDisjointGroupsSudoku9by9() {
        int[][] board = {
                {1, 0, 0, 0, 7, 0, 3, 0, 4},
                {0, 5, 0, 0, 6, 0, 0, 9, 0},
                {0, 0, 4, 0, 5, 0, 7, 0, 0},
                {0, 0, 0, 8, 0, 6, 0, 0, 0},
                {6, 1, 8, 0, 0, 0, 4, 2, 9},
                {0, 0, 0, 4, 0, 2, 0, 0, 0},
                {0, 0, 3, 0, 2, 0, 9, 0, 0},
                {0, 4, 0, 0, 8, 0, 0, 7, 0},
                {9, 0, 6, 0, 4, 0, 0, 0, 3},
        };
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(board)
                .withConstraint(new DisjointGroupsConstraints())
                .peek(SudokuSolver::printBoard)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        int[][] solution = {
                {1, 6, 9, 2, 7, 8, 3, 5, 4},
                {8, 5, 7, 3, 6, 4, 1, 9, 2},
                {2, 3, 4, 1, 5, 9, 7, 6, 8},
                {4, 9, 2, 8, 1, 6, 5, 3, 7},
                {6, 1, 8, 7, 3, 5, 4, 2, 9},
                {3, 7, 5, 4, 9, 2, 6, 8, 1},
                {7, 8, 3, 6, 2, 1, 9, 4, 5},
                {5, 4, 1, 9, 8, 3, 2, 7, 6},
                {9, 2, 6, 5, 4, 7, 8, 1, 3},
        };
        assertSolved(solve, solution);

    }
}