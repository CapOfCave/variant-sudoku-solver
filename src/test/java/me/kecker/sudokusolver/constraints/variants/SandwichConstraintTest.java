package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.SudokuSolveSolution;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.normal.RowsUniqueConstraint;
import me.kecker.sudokusolver.utils.SudokuDirection;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
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

    /**
     * Sudoku taken from <a href="https://www.youtube.com/watch?v=BnsD0-nBNlk">here</a>
     */
    @Test
    void testSandwichSudoku() {
        int[] rowSandwichClues = {5, 13, 20, 9, 12, 0, 4, 14, 5};
        int[] columnsSandwichClues = {19, 7, 15, 19, 4, 0, 6, 9, 35};

        List<SandwichConstraint> rowConstraints = IntStream.range(0, rowSandwichClues.length)
                .mapToObj(rowIdx -> SandwichConstraint.forRow(rowIdx + 1, rowSandwichClues[rowIdx]))
                .toList();
        List<SandwichConstraint> columnConstraints = IntStream.range(0, columnsSandwichClues.length)
                .mapToObj(columnIdx -> SandwichConstraint.forColumn(columnIdx + 1, columnsSandwichClues[columnIdx]))
                .toList();

        SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraints(rowConstraints)
                .withConstraints(columnConstraints)
                .withGivenDigit(1, 9, 1)
                .withGivenDigit(5, 5, 1)
                .solve();
        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {4, 8, 5, 6, 7, 9, 3, 2, 1},
                {3, 2, 9, 5, 8, 1, 6, 4, 7},
                {7, 1, 6, 2, 3, 4, 5, 9, 8},
                {1, 7, 2, 9, 5, 3, 8, 6, 4},
                {6, 9, 4, 8, 1, 2, 7, 3, 5},
                {8, 5, 3, 7, 4, 6, 9, 1, 2},
                {5, 3, 1, 4, 9, 7, 2, 8, 6},
                {9, 6, 8, 1, 2, 5, 4, 7, 3},
                {2, 4, 7, 3, 6, 8, 1, 5, 9}
        };
        assertSolved(solve, solution);
    }


}