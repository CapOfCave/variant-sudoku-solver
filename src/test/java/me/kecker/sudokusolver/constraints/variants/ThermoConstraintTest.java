package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.utils.SudokuCollectionUtils;
import me.kecker.sudokusolver.utils.SudokuPosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.*;

class ThermoConstraintTest {

    @Test
    void testThermoSudoku() {
        List<List<SudokuPosition>> thermos = List.of(
                List.of(
                        new SudokuPosition(4, 4),
                        new SudokuPosition(4, 3),
                        new SudokuPosition(4, 2),
                        new SudokuPosition(4, 1),
                        new SudokuPosition(3, 1),
                        new SudokuPosition(2, 1),
                        new SudokuPosition(1, 1)
                ),
                List.of(
                        new SudokuPosition(4, 4),
                        new SudokuPosition(3, 4),
                        new SudokuPosition(2, 4),
                        new SudokuPosition(1, 4),
                        new SudokuPosition(1, 3),
                        new SudokuPosition(1, 2)
                ),
                List.of(
                        new SudokuPosition(1, 6),
                        new SudokuPosition(2, 6)
                ),
                List.of(
                        new SudokuPosition(1, 7),
                        new SudokuPosition(1, 8),
                        new SudokuPosition(2, 8),
                        new SudokuPosition(3, 8),
                        new SudokuPosition(3, 7),
                        new SudokuPosition(3, 6)
                ),
                List.of(
                        new SudokuPosition(8, 1),
                        new SudokuPosition(7, 1),
                        new SudokuPosition(6, 1),
                        new SudokuPosition(6, 2)
                ),
                List.of(
                        new SudokuPosition(7, 3),
                        new SudokuPosition(6, 3)
                ),
                List.of(
                        new SudokuPosition(8, 2),
                        new SudokuPosition(8, 3)
                ),
                List.of(
                        new SudokuPosition(5, 5),
                        new SudokuPosition(5, 6),
                        new SudokuPosition(5, 7),
                        new SudokuPosition(5, 8),
                        new SudokuPosition(5, 9)
                ),
                List.of(
                        new SudokuPosition(5, 5),
                        new SudokuPosition(6, 5),
                        new SudokuPosition(7, 5),
                        new SudokuPosition(8, 5)
                ),
                List.of(
                        new SudokuPosition(8, 9),
                        new SudokuPosition(7, 9),
                        new SudokuPosition(6, 9)
                ),
                List.of(
                        new SudokuPosition(8, 9),
                        new SudokuPosition(9, 9),
                        new SudokuPosition(9, 8),
                        new SudokuPosition(9, 7),
                        new SudokuPosition(9, 6),
                        new SudokuPosition(9, 5)
                )
        );
        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraints(thermos.stream().map(SudokuCollectionUtils::startingAtOne).map(ThermoConstraint::new).toList())
                .peek(SudokuSolver::printThermos)
                .solve();


        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        int[][] solution = {
                {9, 8, 7, 6, 3, 4, 1, 2, 5},
                {6, 2, 1, 5, 7, 8, 3, 4, 9},
                {5, 4, 3, 2, 1, 9, 8, 7, 6},
                {4, 3, 2, 1, 8, 6, 5, 9, 7},
                {1, 7, 5, 9, 2, 3, 4, 6, 8},
                {8, 9, 6, 7, 4, 5, 2, 1, 3},
                {7, 6, 4, 8, 5, 1, 9, 3, 2},
                {3, 5, 9, 4, 6, 2, 7, 8, 1},
                {2, 1, 8, 3, 9, 7, 6, 5, 4}
        };
        assertSolved(solve, solution);
    }
}