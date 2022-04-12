package me.kecker.sudokusolver.additionaltests;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.NegativeConstraint;
import me.kecker.sudokusolver.constraints.base.AdjacentPairSumConstraint;
import me.kecker.sudokusolver.constraints.negative.NegativeAdjacentPairSumConstraint;
import me.kecker.sudokusolver.constraints.variants.ConsecutiveConstraint;
import me.kecker.sudokusolver.constraints.variants.PalindromeConstraint;
import me.kecker.sudokusolver.constraints.variants.ThermoConstraint;
import me.kecker.sudokusolver.dtos.Pair;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;

public class Happy49thSudoku {


    /**
     * Sudoku taken from <a href="https://www.youtube.com/watch?v=lknRYxjlRe4">this video</a>,
     */
    @Test
    void testHappy49thSudoku() {

        class NegativeKillerConstraint extends NegativeAdjacentPairSumConstraint {
            public NegativeKillerConstraint() {
                super(7);
            }
        }

        class Killer7Constraint extends AdjacentPairSumConstraint {

            public Killer7Constraint(Pair affectedCells) {
                super(7, affectedCells);
            }

            @Override
            public Collection<Class<? extends NegativeConstraint>> getAffectedNegativeConstraints() {
                return Collections.singleton(NegativeKillerConstraint.class);
            }
        }

        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new ThermoConstraint(List.of(rc(8, 3), rc(7, 3), rc(6, 3), rc(5, 3), rc(4, 3), rc(3, 3))))
                .withConstraint(new ThermoConstraint(List.of(rc(8, 3), rc(7, 3), rc(6, 3), rc(6, 2), rc(6, 1), rc(5, 1), rc(4, 1))))
                .withConstraint(new NegativeKillerConstraint())
                .withConstraint(new Killer7Constraint(Pair.of(rc(2, 5), rc(2, 6))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(2, 7), rc(3, 7))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(2, 8), rc(3, 8))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(4, 4), rc(5, 4))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(4, 6), rc(5, 6))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(4, 7), rc(5, 7))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(6, 2), rc(6, 3))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(8, 3), rc(9, 3))))
                .withConstraint(new Killer7Constraint(Pair.of(rc(9, 5), rc(9, 6))))
                .withConstraint(new ConsecutiveConstraint(Pair.of(rc(8, 2), rc(9, 2))))
                .withConstraint(new PalindromeConstraint(List.of(rc(8, 6), rc(7, 7), rc(6, 8), rc(5, 8), rc(4, 8), rc(3, 7), rc(3, 6), rc(4, 5), rc(5, 6), rc(5, 7))))
                .withGivenDigit(rc(1, 5), 1)
                .withGivenDigit(rc(1, 6), 9)
                .withGivenDigit(rc(1, 7), 7)
                .withGivenDigit(rc(1, 8), 3)
                .solve()
                .withExactlyOneSolution();

        int[][] expectedBoard = {
                {2, 6, 4, 5, 1, 9, 7, 3, 8},
                {7, 5, 8, 6, 4, 3, 2, 1, 9},
                {1, 3, 9, 8, 2, 7, 5, 6, 4},
                {9, 2, 7, 4, 8, 1, 3, 5, 6},
                {8, 1, 5, 3, 9, 6, 4, 7, 2},
                {6, 4, 3, 2, 7, 5, 9, 8, 1},
                {4, 7, 2, 1, 3, 8, 6, 9, 5},
                {5, 9, 1, 7, 6, 4, 8, 2, 3},
                {3, 8, 6, 9, 5, 2, 1, 4, 7},
        };
        assertSolved(solution, expectedBoard);


    }
}
