package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.dtos.Pair;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;

class RatioConstraintTest {

    /**
     * Sudoku taken from <a href="https://nrich.maths.org/4827">here</a> and manually verified.
     */
    @Test
    void testRatioSudoku() {
        var solution = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new RatioConstraint(2, 5, Pair.of(rc(1, 2), rc(1, 3))))
                .withConstraint(new RatioConstraint(Pair.of(rc(1, 6), rc(1, 7))))
                .withConstraint(new RatioConstraint(3, 2, Pair.of(rc(1, 7), rc(1, 8))))
                .withConstraint(new RatioConstraint(Pair.of(rc(2, 1), rc(2, 2))))
                .withConstraint(new RatioConstraint(2, 3, Pair.of(rc(2, 2), rc(2, 3))))
                .withConstraint(new RatioConstraint(4, Pair.of(rc(2, 3), rc(2, 4))))
                .withConstraint(new RatioConstraint(5, 8, Pair.of(rc(2, 6), rc(2, 7))))
                .withConstraint(new RatioConstraint(2, Pair.of(rc(3, 5), rc(3, 6))))
                .withConstraint(new RatioConstraint(9, 5, Pair.of(rc(4, 2), rc(4, 3))))
                .withConstraint(new RatioConstraint(Pair.of(rc(4, 7), rc(4, 8))))
                .withConstraint(new RatioConstraint(2, 3, Pair.of(rc(6, 1), rc(6, 2))))
                .withConstraint(new RatioConstraint(2, 3, Pair.of(rc(6, 4), rc(6, 5))))
                .withConstraint(new RatioConstraint(1, 9, Pair.of(rc(6, 6), rc(6, 7))))
                .withConstraint(new RatioConstraint(1, 8, Pair.of(rc(6, 7), rc(6, 8))))
                .withConstraint(new RatioConstraint(2, 3, Pair.of(rc(7, 1), rc(7, 2))))
                .withConstraint(new RatioConstraint(1, 4, Pair.of(rc(7, 2), rc(7, 3))))
                .withConstraint(new RatioConstraint(1, 2, Pair.of(rc(7, 3), rc(7, 4))))
                .withConstraint(new RatioConstraint(1, 3, Pair.of(rc(8, 3), rc(8, 4))))
                .withConstraint(new RatioConstraint(1, 2, Pair.of(rc(8, 4), rc(8, 5))))
                .withConstraint(new RatioConstraint(1, 6, Pair.of(rc(8, 5), rc(8, 6))))
                .withConstraint(new RatioConstraint(1, 2, Pair.of(rc(9, 5), rc(9, 6))))
                .withConstraint(new RatioConstraint(2, 3, Pair.of(rc(9, 7), rc(9, 8))))
                .solve()
                .withExactlyOneSolution();

        int[][] board = {
                {1, 5, 2, 9, 7, 8, 4, 6, 3},
                {3, 6, 4, 1, 2, 5, 8, 7, 9},
                {9, 7, 8, 4, 3, 6, 5, 1, 2},
                {8, 9, 5, 7, 1, 3, 2, 4, 6},
                {4, 1, 6, 8, 5, 2, 9, 3, 7},
                {2, 3, 7, 6, 4, 9, 1, 8, 5},
                {6, 4, 1, 2, 9, 7, 3, 5, 8},
                {5, 8, 9, 3, 6, 1, 7, 2, 4},
                {7, 2, 3, 5, 8, 4, 6, 9, 1}
        };
        assertSolved(solution, board);
    }
}