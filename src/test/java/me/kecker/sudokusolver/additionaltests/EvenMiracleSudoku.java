package me.kecker.sudokusolver.additionaltests;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.variants.EvenConstraint;
import me.kecker.sudokusolver.constraints.variants.NonconsecutiveConstraint;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;

public class EvenMiracleSudoku {

    /**
     * Sudoku taken from <a href="https://www.youtube.com/watch?v=uOXcgAMEJ14">this video</a>.
     */
    @Test
    void testEvenMiracleSudoku() {
        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new NonconsecutiveConstraint())
                .withGivenDigit(rc(2, 3), 5)
                .withGivenDigit(rc(2, 4), 2)
                .withGivenDigit(rc(2, 7), 3)
                .withGivenDigit(rc(8, 1), 4)
                .withConstraint(new EvenConstraint(rc(2, 2)))
                .withConstraint(new EvenConstraint(rc(2, 5)))
                .withConstraint(new EvenConstraint(rc(2, 8)))
                .withConstraint(new EvenConstraint(rc(5, 1)))
                .withConstraint(new EvenConstraint(rc(5, 9)))
                .withConstraint(new EvenConstraint(rc(8, 2)))
                .withConstraint(new EvenConstraint(rc(8, 5)))
                .withConstraint(new EvenConstraint(rc(8, 8)))
                .solve()
                .withExactlyOneSolution();

        int[][] expectedSolution = {
                {7, 4, 2, 6, 9, 3, 8, 1, 5},
                {1, 8, 5, 2, 4, 7, 3, 6, 9},
                {3, 6, 9, 5, 8, 1, 7, 2, 4},
                {9, 3, 6, 1, 5, 8, 4, 7, 2},
                {2, 7, 4, 9, 3, 6, 1, 5, 8},
                {5, 1, 8, 4, 7, 2, 6, 9, 3},
                {8, 5, 1, 7, 2, 4, 9, 3, 6},
                {4, 2, 7, 3, 6, 9, 5, 8, 1},
                {6, 9, 3, 8, 1, 5, 2, 4, 7},
        };
        assertSolved(solution, expectedSolution);

    }
}
