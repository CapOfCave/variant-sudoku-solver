package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.result.Solution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.utils.SudokuCollectionUtils;
import me.kecker.sudokusolver.dtos.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static org.junit.jupiter.api.Assertions.*;

class ThermoConstraintTest {

    /**
     * Sudoku taken from <a href="https://cracking-the-cryptic.web.app/sudoku/pH83rGHQRR">here</a>
     * with solution seen in <a href="https://www.youtube.com/watch?v=KTth49YrQVU">this video</a>
     */
    @Test
    void testThermoSudoku() {
        List<List<Position>> thermos = List.of(
                List.of(
                        new Position(4, 4),
                        new Position(4, 3),
                        new Position(4, 2),
                        new Position(4, 1),
                        new Position(3, 1),
                        new Position(2, 1),
                        new Position(1, 1)
                ),
                List.of(
                        new Position(4, 4),
                        new Position(3, 4),
                        new Position(2, 4),
                        new Position(1, 4),
                        new Position(1, 3),
                        new Position(1, 2)
                ),
                List.of(
                        new Position(1, 6),
                        new Position(2, 6)
                ),
                List.of(
                        new Position(1, 7),
                        new Position(1, 8),
                        new Position(2, 8),
                        new Position(3, 8),
                        new Position(3, 7),
                        new Position(3, 6)
                ),
                List.of(
                        new Position(8, 1),
                        new Position(7, 1),
                        new Position(6, 1),
                        new Position(6, 2)
                ),
                List.of(
                        new Position(7, 3),
                        new Position(6, 3)
                ),
                List.of(
                        new Position(8, 2),
                        new Position(8, 3)
                ),
                List.of(
                        new Position(5, 5),
                        new Position(5, 6),
                        new Position(5, 7),
                        new Position(5, 8),
                        new Position(5, 9)
                ),
                List.of(
                        new Position(5, 5),
                        new Position(6, 5),
                        new Position(7, 5),
                        new Position(8, 5)
                ),
                List.of(
                        new Position(8, 9),
                        new Position(7, 9),
                        new Position(6, 9)
                ),
                List.of(
                        new Position(8, 9),
                        new Position(9, 9),
                        new Position(9, 8),
                        new Position(9, 7),
                        new Position(9, 6),
                        new Position(9, 5)
                )
        );
        Solution solve = SudokuSolver.normalSudokuRulesApply()
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