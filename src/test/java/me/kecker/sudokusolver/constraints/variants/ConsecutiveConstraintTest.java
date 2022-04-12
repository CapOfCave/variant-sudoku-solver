package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.dtos.Pair;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;
import static org.junit.jupiter.api.Assertions.*;

class ConsecutiveConstraintTest {

    @Test
    void testKropkiXVSudoku() {
        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(1, 9), rc(2, 9))))
                .withConstraint(new RatioConstraint(Pair.of(rc(2, 9), rc(2, 8))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(2, 8), rc(2, 7))))
                .withConstraint(new XSumConstraint(Pair.of(rc(2, 7), rc(2, 6))))
                .withConstraint(new VSumConstraint(Pair.of(rc(2, 6), rc(2, 5))))
                .withConstraint(new XSumConstraint(Pair.of(rc(2, 5), rc(1, 5))))
                .withConstraint(new RatioConstraint(Pair.of(rc(1, 4), rc(1, 5))))
                .withConstraint(new VSumConstraint(Pair.of(rc(1, 3), rc(1, 4))))
                .withConstraint(new RatioConstraint(Pair.of(rc(1, 2), rc(1, 3))))
                .withConstraint(new VSumConstraint(Pair.of(rc(1, 1), rc(1, 2))))
                .withConstraint(new RatioConstraint(Pair.of(rc(1, 1), rc(2, 1))))
                .withConstraint(new XSumConstraint(Pair.of(rc(2, 1), rc(3, 1))))
                .withConstraint(new RatioConstraint(Pair.of(rc(3, 1), rc(4, 1))))
                .withConstraint(new XSumConstraint(Pair.of(rc(4, 1), rc(5, 1))))
                .withConstraint(new RatioConstraint(Pair.of(rc(5, 1), rc(5, 2))))
                .withConstraint(new XSumConstraint(Pair.of(rc(5, 2), rc(6, 2))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(6, 2), rc(7, 2))))
                .withConstraint(new RatioConstraint(Pair.of(rc(7, 2), rc(8, 2))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(8, 2), rc(9, 2))))
                .withConstraint(new XSumConstraint(Pair.of(rc(9, 2), rc(9, 1))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(9, 3), rc(9, 4))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(9, 4), rc(9, 5))))
                .withConstraint(new XSumConstraint(Pair.of(rc(9, 5), rc(8, 5))))
                .withConstraint(new RatioConstraint(Pair.of(rc(8, 5), rc(7, 5))))
                .withConstraint(new XSumConstraint(Pair.of(rc(7, 5), rc(6, 5))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(6, 5), rc(6, 6))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(6, 6), rc(5, 6))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(5, 6), rc(5, 7))))
                .withConstraint(new XSumConstraint(Pair.of(rc(5, 7), rc(6, 7))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(5, 7), rc(5, 8))))
                .withConstraint(new XSumConstraint(Pair.of(rc(5, 8), rc(5, 9))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(5, 9), rc(4, 9))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(4, 9), rc(3, 9))))
                .solve()
                .withExactlyOneSolution();

        int[][] expectedBoard = {
                {3, 2, 1, 4, 8, 7, 9, 6, 5},
                {6, 5, 9, 1, 2, 3, 7, 8, 4},
                {4, 7, 8, 6, 5, 9, 2, 3, 1},
                {8, 6, 7, 3, 1, 4, 5, 9, 2},
                {2, 1, 4, 8, 9, 5, 6, 7, 3},
                {5, 9, 3, 2, 7, 6, 4, 1, 8},
                {9, 8, 5, 7, 3, 2, 1, 4, 6},
                {1, 4, 2, 9, 6, 8, 3, 5, 7},
                {7, 3, 6, 5, 4, 1, 8, 2, 9}
        };
        assertSolved(solution, expectedBoard);
    }

}