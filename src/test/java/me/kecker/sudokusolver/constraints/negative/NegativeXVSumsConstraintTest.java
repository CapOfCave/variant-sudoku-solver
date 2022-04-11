package me.kecker.sudokusolver.constraints.negative;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.variants.VSumConstraint;
import me.kecker.sudokusolver.constraints.variants.XSumConstraint;
import me.kecker.sudokusolver.dtos.Pair;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.rc;

class NegativeXVSumsConstraintTest {

    /**
     * Sudoku taken from <a href="https://logic-masters.de/Raetselportal/Raetsel/zeigen.php?id=0007QB">here</a>.
     * Solution verified on that site.
     */
    @Test
    void testNegativeXVSumsSudokuNegConstraintFirst() {
        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new NegativeXVSumsConstraint())
                .withConstraint(new XSumConstraint(Pair.of(rc(3, 4), rc(3, 5))))
                .withConstraint(new XSumConstraint(Pair.of(rc(4, 3), rc(5, 3))))
                .withConstraint(new VSumConstraint(Pair.of(rc(3, 5), rc(4, 5))))
                .withConstraint(new VSumConstraint(Pair.of(rc(5, 3), rc(5, 4))))
                .withConstraint(new VSumConstraint(Pair.of(rc(5, 6), rc(5, 7))))
                .withConstraint(new VSumConstraint(Pair.of(rc(6, 5), rc(7, 5))))
                .withGivenDigit(rc(3, 3), 9)
                .withGivenDigit(rc(5, 1), 8)
                .withGivenDigit(rc(8, 1), 1)
                .withGivenDigit(rc(9, 1), 5)
                .withGivenDigit(rc(9, 9), 1)
                .solve()
                .withExactlyOneSolution();

        solution.printBoard();
    }

    /**
     * Sudoku taken from <a href="https://logic-masters.de/Raetselportal/Raetsel/zeigen.php?id=0007QB">here</a>.
     * Solution verified on that site.
     */
    @Test
    void testNegativeXVSumsSudokuNegConstraintLast() {
        Solution solution = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(new XSumConstraint(Pair.of(rc(3, 4), rc(3, 5))))
                .withConstraint(new XSumConstraint(Pair.of(rc(4, 3), rc(5, 3))))
                .withConstraint(new VSumConstraint(Pair.of(rc(3, 5), rc(4, 5))))
                .withConstraint(new VSumConstraint(Pair.of(rc(5, 3), rc(5, 4))))
                .withConstraint(new VSumConstraint(Pair.of(rc(5, 6), rc(5, 7))))
                .withConstraint(new VSumConstraint(Pair.of(rc(6, 5), rc(7, 5))))
                .withGivenDigit(rc(3, 3), 9)
                .withGivenDigit(rc(5, 1), 8)
                .withGivenDigit(rc(8, 1), 1)
                .withGivenDigit(rc(9, 1), 5)
                .withGivenDigit(rc(9, 9), 1)
                .withConstraint(new NegativeXVSumsConstraint())
                .solve()
                .withExactlyOneSolution();

        solution.printBoard();


    }
}