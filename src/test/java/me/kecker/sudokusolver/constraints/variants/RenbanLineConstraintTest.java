package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;
import static org.junit.jupiter.api.Assertions.*;

class RenbanLineConstraintTest {

    @Test
    void testRenbanSudoku() {
        int[][] initialBoard = {
                {3, 0, 0, 0, 0, 7, 0, 0, 0},
                {0, 7, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 9, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 9},
                {0, 2, 0, 0, 0, 0, 0, 8, 0},
                {9, 0, 0, 0, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 3, 0, 0, 7, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 6}
        };
        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(initialBoard)
                .withConstraint(new RenbanLineConstraint(List.of(rc(7, 1), rc(7, 2), rc(8, 2), rc(9, 2), rc(9, 3))))
                .withConstraint(new RenbanLineConstraint(List.of(rc(6, 2), rc(6, 3), rc(7, 3), rc(8, 3), rc(8, 4))))
                .withConstraint(new RenbanLineConstraint(List.of(rc(5, 3), rc(5, 4), rc(6, 4), rc(7, 4), rc(7, 5))))
                .withConstraint(new RenbanLineConstraint(List.of(rc(4, 4), rc(4, 5), rc(5, 5), rc(6, 5), rc(6, 6))))
                .withConstraint(new RenbanLineConstraint(List.of(rc(3, 5), rc(3, 6), rc(4, 6), rc(5, 6), rc(5, 7))))
                .withConstraint(new RenbanLineConstraint(List.of(rc(2, 6), rc(2, 7), rc(3, 7), rc(4, 7), rc(4, 8))))
                .withConstraint(new RenbanLineConstraint(List.of(rc(1, 7), rc(1, 8), rc(2, 8), rc(3, 8), rc(3, 9))))
                .solve()
                .withExactlyOneSolution();

        int[][] solvedBoard = {
                {3, 4, 9, 6, 2, 7, 8, 5, 1},
                {2, 7, 6, 8, 1, 5, 3, 9, 4},
                {5, 1, 8, 9, 4, 3, 2, 6, 7},
                {8, 3, 1, 7, 5, 2, 6, 4, 9},
                {6, 2, 7, 4, 9, 1, 5, 8, 3},
                {9, 5, 4, 3, 8, 6, 7, 1, 2},
                {7, 9, 3, 5, 6, 4, 1, 2, 8},
                {4, 6, 2, 1, 3, 8, 9, 7, 5},
                {1, 8, 5, 2, 7, 9, 4, 3, 6},
        };
        assertSolved(solution, solvedBoard);
    }
}