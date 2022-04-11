package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.result.SolutionSet;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;

class XVSumConstraintTest {

    /**
     * Sudoku taken from <a href=https://www.puzzlemix.com/playgrid.php?id=91437&type=xv&share=1>here</a>
     */
    @Test
    void testXVSudoku() {
        Solution solve = SudokuSolver.fromBoard(new Board(2, 3, 3, 2, 1, 6))
                .withNormalSudokuRulesConstraints()
                .withGivenDigit(rc(3, 3), 5)
                .withGivenDigit(rc(3, 4), 3)
                .withGivenDigit(rc(4, 3), 3)
                .withGivenDigit(rc(4, 4), 4)
                .withConstraint(new XSumConstraint(rc(1, 1), rc(1, 2)))
                .withConstraint(new XSumConstraint(rc(2, 3), rc(2, 4)))
                .withConstraint(new XSumConstraint(rc(3, 5), rc(3, 6)))
                .withConstraint(new XSumConstraint(rc(5, 5), rc(5, 6)))
                .withConstraint(new XSumConstraint(rc(6, 2), rc(6, 3)))
                .withConstraint(new VSumConstraint(rc(1, 5), rc(2, 5)))
                .withConstraint(new VSumConstraint(rc(2, 6), rc(3, 6)))
                .withConstraint(new VSumConstraint(rc(4, 4), rc(4, 5)))
                .withConstraint(new VSumConstraint(rc(4, 5), rc(5, 5)))
                .withConstraint(new VSumConstraint(rc(5, 1), rc(5, 2)))
                .withConstraint(new VSumConstraint(rc(6, 1), rc(6, 2)))
                .solve()
                .withExactlyOneSolution();

        int[][] board = {
                {4, 6, 2, 1, 3, 5},
                {5, 3, 4, 6, 2, 1},
                {2, 1, 5, 3, 6, 4},
                {6, 5, 3, 4, 1, 2},
                {3, 2, 1, 5, 4, 6},
                {1, 4, 6, 2, 5, 3}
        };
        assertSolved(solve, board);
    }
}