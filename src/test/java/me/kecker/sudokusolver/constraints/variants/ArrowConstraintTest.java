package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.SudokuSolver;
import me.kecker.sudokusolver.dtos.Position;
import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;
import static me.kecker.sudokusolver.utils.SudokuCollectionUtils.startingAtOne;

class ArrowConstraintTest {

    /**
     * Sudoku taken from <a href="https://www.funwithpuzzles.com/2015/06/clone-sudoku.html">here</a>
     * with solution taken from <a href="https://www.funwithpuzzles.com/2015/05/tens-sudoku.html">here</a>
     */
    @Test
    void testArrowSudoku() {
        int[][] board = {
                {0, 0, 0, 0, 8, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 9, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 7, 0},
                {4, 1, 0, 0, 0, 0, 0, 0, 9},
                {0, 2, 7, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 1, 0, 0, 0, 9, 7},
                {0, 0, 0, 7, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 6, 2, 0, 0, 0},
        };
        record Arrow(Position bulb, Collection<Position> shaft) {
        }

        List<Arrow> arrows = List.of(
                new Arrow(new Position(1, 1), List.of(
                        new Position(2, 1), new Position(3, 1)
                )),
                new Arrow(new Position(1, 2), List.of(
                        new Position(1, 3), new Position(1, 4)
                )),
                new Arrow(new Position(4, 2), List.of(
                        new Position(3, 2), new Position(2, 2)
                )),
                new Arrow(new Position(2, 5), List.of(
                        new Position(2, 4), new Position(2, 3)
                )),
                new Arrow(new Position(3, 3), List.of(
                        new Position(4, 3), new Position(5, 3)
                )),
                new Arrow(new Position(3, 4), List.of(
                        new Position(3, 5), new Position(3, 6)
                )),
                new Arrow(new Position(6, 4), List.of(
                        new Position(5, 4), new Position(4, 4)
                )),
                new Arrow(new Position(4, 7), List.of(
                        new Position(4, 6), new Position(4, 5)
                )),
                new Arrow(new Position(5, 5), List.of(
                        new Position(6, 5), new Position(7, 5)
                )),
                new Arrow(new Position(5, 6), List.of(
                        new Position(5, 7), new Position(5, 8)
                )),
                new Arrow(new Position(8, 6), List.of(
                        new Position(7, 6), new Position(6, 6)
                )),
                new Arrow(new Position(6, 9), List.of(
                        new Position(6, 8), new Position(6, 7)
                )),
                new Arrow(new Position(7, 7), List.of(
                        new Position(8, 7), new Position(9, 7)
                ))
        );
        List<ArrowConstraint> arrowConstraints = arrows.stream()
                .map(arrow -> new ArrowConstraint(startingAtOne(arrow.bulb()), startingAtOne(arrow.shaft())))
                .toList();

        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(board)
                .withConstraints(arrowConstraints)
                .peek(SudokuSolver::printBoard)
                .peek(SudokuSolver::printArrows)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {9, 3, 1, 2, 8, 4, 7, 5, 6},
                {2, 5, 6, 3, 9, 7, 8, 4, 1},
                {7, 4, 8, 6, 1, 5, 9, 2, 3},
                {6, 9, 5, 4, 2, 1, 3, 7, 8},
                {4, 1, 3, 5, 7, 8, 2, 6, 9},
                {8, 2, 7, 9, 3, 6, 4, 1, 5},
                {5, 8, 2, 1, 4, 3, 6, 9, 7},
                {3, 6, 4, 7, 5, 9, 1, 8, 2},
                {1, 7, 9, 8, 6, 2, 5, 3, 4},
        };
        assertSolved(solve, solution);
    }

}