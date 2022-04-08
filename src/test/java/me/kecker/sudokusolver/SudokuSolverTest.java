package me.kecker.sudokusolver;

import com.google.ortools.sat.CpSolverStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuSolverTest {

    /**
     * Sudoku taken from <a href="https://www.researchgate.net/figure/A-Sudoku-with-17-clues-and-its-unique-solution_fig1_311250094">here</a>
     */
    @Test
    void testNormalSudokuRules() {
        SudokuSolver sudokuSolver = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigit(1, 4, 8)
                .withGivenDigit(1, 6, 1)
                .withGivenDigit(2, 8, 4)
                .withGivenDigit(2, 9, 3)
                .withGivenDigit(3, 1, 5)
                .withGivenDigit(4, 5, 7)
                .withGivenDigit(4, 7, 8)
                .withGivenDigit(5, 7, 1)
                .withGivenDigit(6, 2, 2)
                .withGivenDigit(6, 5, 3)
                .withGivenDigit(7, 1, 6)
                .withGivenDigit(7, 8, 7)
                .withGivenDigit(7, 9, 5)
                .withGivenDigit(8, 3, 3)
                .withGivenDigit(8, 4, 4)
                .withGivenDigit(9, 4, 2)
                .withGivenDigit(9, 7, 6);

        SudokuSolveSolution solve = sudokuSolver.solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        assertEquals(8, solve.value(2, 2));
        assertEquals(6, solve.value(2, 3));


    }
}