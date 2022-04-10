package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.constraints.normal.RowsUniqueConstraint;
import me.kecker.sudokusolver.dtos.Position;
import me.kecker.sudokusolver.dtos.Rect;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.startingAtOne;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SandwichConstraintTest {

    @Test
    void testOneSimpleRowSandwich() {
        Solution solve = SudokuSolver.fromBoard(new Board(4, 1, 1, 1, 1, 4))
                .withConstraint(new RowsUniqueConstraint())
                .withConstraint(new SandwichConstraint(SandwichConstraint.SandwichDirection.ROW, 0, 1, 4, 2))
                .withGivenDigit(1, 2, 1)
                .solve();
        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {3, 1, 2, 4},
        };
        assertSolved(solve, solution);

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

        Solution solve = SudokuSolver.normalSudokuRulesApply()
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

    /**
     * Sudoku taken from <a href="https://cracking-the-cryptic.web.app/sudoku/nrTRpjpgTF">here</a>
     * with solution seen in <a href="https://www.youtube.com/watch?v=ZzH2_tjQAHE">this video</a>
     */
    @Test
    void test4To6SandwichSudoku() {
        int[] rowSandwichClues = {35, 16, 10, 14, 6, 2, 1, 25, 7};
        int[] columnsSandwichClues = {26, 22, 11, 3, 2, 8, 18, 6, 12};

        List<SandwichConstraint> rowConstraints = IntStream.range(0, rowSandwichClues.length)
                .mapToObj(rowIdx -> new SandwichConstraint(SandwichConstraint.SandwichDirection.ROW, rowIdx, 4, 6, rowSandwichClues[rowIdx]))
                .toList();

        List<SandwichConstraint> columnConstraints = IntStream.range(0, columnsSandwichClues.length)
                .mapToObj(columnIdx -> new SandwichConstraint(SandwichConstraint.SandwichDirection.COLUMN, columnIdx, 4, 6, columnsSandwichClues[columnIdx]))
                .toList();

        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraints(rowConstraints)
                .withConstraints(columnConstraints)
                .withGivenDigit(8, 4, 1)
                .withGivenDigit(9, 6, 4)
                .solve();
        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {4, 1, 3, 5, 8, 2, 9, 7, 6},
                {2, 5, 8, 6, 9, 7, 4, 3, 1},
                {9, 6, 7, 3, 4, 1, 5, 8, 2},
                {8, 3, 1, 4, 2, 5, 7, 6, 9},
                {7, 9, 5, 8, 6, 3, 2, 1, 4},
                {6, 2, 4, 7, 1, 9, 3, 5, 8},
                {5, 8, 2, 9, 3, 6, 1, 4, 7},
                {3, 4, 9, 1, 7, 8, 6, 2, 5},
                {1, 7, 6, 2, 5, 4, 8, 9, 3}
        };
        assertSolved(solve, solution);
    }

    /**
     * Sudoku taken from <a https://app.crackingthecryptic.com/sudoku/P2MgjGj6JF">here</a>
     */
    @Test
    void testSandwichKillerSudoku() {

        var killer15 = new KillerConstraint(startingAtOne(List.of(
                new Position(4, 8),
                new Position(5, 8),
                new Position(5, 7)
        )), 15);

        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withConstraint(SandwichConstraint.forRow(1, 0))
                .withConstraint(SandwichConstraint.forRow(4, 8))
                .withConstraint(SandwichConstraint.forRow(5, 17))
                .withConstraint(SandwichConstraint.forColumn(1, 33))
                .withConstraint(SandwichConstraint.forColumn(2, 32))
                .withConstraint(SandwichConstraint.forColumn(4, 6))
                .withConstraint(SandwichConstraint.forColumn(5, 21))
                .withConstraint(SandwichConstraint.forColumn(6, 8))
                .withConstraint(SandwichConstraint.forColumn(8, 32))
                .withConstraint(SandwichConstraint.forColumn(9, 33))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(3, 1, 4, 1), 11))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(7, 2, 8, 2), 9))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(1, 4, 2, 4), 7))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(2, 6, 2, 8), 14))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(8, 6, 9, 6), 6))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(3, 7, 3, 9), 21))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(6, 8, 6, 9), 13))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(8, 8, 9, 8), 10))
                .withConstraint(KillerConstraint.rectangularCage(new Rect(8, 9, 9, 9), 10))
                .withConstraint(killer15)
                .peek(SudokuSolver::printKillers)
                .solve();

        assertEquals(CpSolverStatus.OPTIMAL, solve.getStatus());

        int[][] solution = {
                {1, 9, 4, 7, 5, 8, 6, 3, 2},
                {5, 7, 2, 6, 3, 4, 8, 1, 9},
                {8, 6, 3, 9, 1, 2, 4, 5, 7},
                {3, 4, 1, 2, 6, 9, 7, 8, 5},
                {7, 2, 9, 4, 8, 5, 1, 6, 3},
                {6, 8, 5, 1, 7, 3, 9, 2, 4},
                {4, 5, 6, 3, 9, 1, 2, 7, 8},
                {9, 1, 8, 5, 2, 7, 3, 4, 6},
                {2, 3, 7, 8, 4, 6, 5, 9, 1}
        };
        assertSolved(solve, solution);
    }
}