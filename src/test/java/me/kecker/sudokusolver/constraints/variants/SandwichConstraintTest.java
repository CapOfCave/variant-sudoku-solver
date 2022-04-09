package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.normal.RowsUniqueConstraint;
import me.kecker.sudokusolver.utils.SudokuDirection;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SandwichConstraintTest {

    @Test
    void testOneSimpleRowSandwichAllIncluded() {
        SudokuSolveSolution solve = SudokuSolver.fromBoard(new Board(4, 1, 1, 1, 1, 4))
                .withConstraint(new RowsUniqueConstraint())
                .withConstraint(new SandwichConstraint(SudokuDirection.ROW, 0, 1, 4, 2))
                .withGivenDigit(1, 2, 1)
                .solve();
        System.out.println(solve.getStatus());

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());
        assertThat(solve.value(1, 1)).isEqualTo(3);
        assertThat(solve.value(1, 2)).isEqualTo(1);
        assertThat(solve.value(1, 3)).isEqualTo(2);
        assertThat(solve.value(1, 4)).isEqualTo(4);

        solve.printBoard();
        System.out.println(solve.getSolver().getSolutionInfo());


    }

}