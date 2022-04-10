package me.kecker.sudokusolver;

import me.kecker.sudokusolver.result.Solution;
import org.junit.jupiter.api.Test;

import static me.kecker.sudokusolver.test.SolvedAssertion.assertSolved;

class SudokuSolverTest {

    /**
     * Sudoku taken from <a href="https://www.researchgate.net/figure/A-Sudoku-with-17-clues-and-its-unique-solution_fig1_311250094">here</a>
     */
    @Test
    void testNormalSudokuRules() {
        Solution solve = SudokuSolver.normalSudokuRulesApply()
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
                .withGivenDigit(9, 7, 6)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {2, 3, 7, 8, 4, 1, 5, 6, 9},
                {1, 8, 6, 7, 9, 5, 2, 4, 3},
                {5, 9, 4, 3, 2, 6, 7, 1, 8},
                {3, 1, 5, 6, 7, 4, 8, 9, 2},
                {4, 6, 9, 5, 8, 2, 1, 3, 7},
                {7, 2, 8, 1, 3, 9, 4, 5, 6},
                {6, 4, 2, 9, 1, 8, 3, 7, 5},
                {8, 5, 3, 4, 6, 7, 9, 2, 1},
                {9, 7, 1, 2, 5, 3, 6, 8, 4},
        };
        assertSolved(solve, solution);
    }

    /**
     * Sudoku taken from <a href="https://www.researchgate.net/figure/Sudoku-puzzles-and-its-solution_fig1_228566840">here</a>
     */
    @Test
    void testNormalSudokuRulesFromIntBoard() {
        int[][] initialState = {
                {0, 0, 6, 1, 0, 2, 5, 0, 0},
                {0, 3, 9, 0, 0, 0, 1, 4, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0},
                {9, 0, 2, 0, 3, 0, 4, 0, 1},
                {0, 8, 0, 0, 0, 0, 0, 7, 0},
                {1, 0, 3, 0, 6, 0, 8, 0, 9},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 5, 4, 0, 0, 0, 9, 1, 0},
                {0, 0, 7, 5, 0, 3, 2, 0, 0},
        };
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntArray(initialState)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {8, 4, 6, 1, 7, 2, 5, 9, 3},
                {7, 3, 9, 6, 5, 8, 1, 4, 2},
                {5, 2, 1, 3, 4, 9, 7, 6, 8},
                {9, 6, 2, 8, 3, 7, 4, 5, 1},
                {4, 8, 5, 9, 2, 1, 3, 7, 6},
                {1, 7, 3, 4, 6, 5, 8, 2, 9},
                {2, 9, 8, 7, 1, 4, 6, 3, 5},
                {3, 5, 4, 2, 8, 6, 9, 1, 7},
                {6, 1, 7, 5, 9, 3, 2, 8, 4},
        };
        assertSolved(solve, solution);
    }

    /**
     * Sudoku taken from <a href="https://www.researchgate.net/figure/Sudoku-puzzles-and-its-solution_fig1_228566840">here</a>
     */
    @Test
    void testNormalSudokuRulesFromIntegerBoard() {
        Integer[][] initialState = {
//@formatter:off
                {null, null,    6,    1, null,    2,    5, null, null},
                {null,    3,    9, null, null, null,    1,    4, null},
                {null, null, null, null,    4, null, null, null, null},
                {   9, null,    2, null,    3, null,    4, null,    1},
                {null,     8, null, null, null, null, null,    7, null},
                {   1, null,    3, null,    6, null,    8, null,    9},
                {null, null, null, null,    1, null, null, null, null},
                {null,    5,    4, null, null, null,    9,    1, null},
                {null, null,    7,    5, null,    3,    2, null, null},
//@formatter:off
        };
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromIntegerArray(initialState)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {8, 4, 6, 1, 7, 2, 5, 9, 3},
                {7, 3, 9, 6, 5, 8, 1, 4, 2},
                {5, 2, 1, 3, 4, 9, 7, 6, 8},
                {9, 6, 2, 8, 3, 7, 4, 5, 1},
                {4, 8, 5, 9, 2, 1, 3, 7, 6},
                {1, 7, 3, 4, 6, 5, 8, 2, 9},
                {2, 9, 8, 7, 1, 4, 6, 3, 5},
                {3, 5, 4, 2, 8, 6, 9, 1, 7},
                {6, 1, 7, 5, 9, 3, 2, 8, 4},
        };
        assertSolved(solve, solution);
    }

    /**
     * Sudoku taken from <a href="https://www.researchgate.net/figure/Sudoku-puzzles-and-its-solution_fig1_228566840">here</a>
     */
    @Test
    void testNormalSudokuRulesFromCharBoard() {
        char[][] initialState = {
                {'?', '?', '6', '1', '?', '2', '5', '?', '?'},
                {'?', '3', '9', '?', '?', '?', '1', '4', '?'},
                {'?', '?', '?', '?', '4', '?', '?', '?', '?'},
                {'9', '?', '2', '?', '3', '?', '4', '?', '1'},
                {'?', '8', '?', '?', '?', '?', '?', '7', '?'},
                {'1', '?', '3', '?', '6', '?', '8', '?', '9'},
                {'?', '?', '?', '?', '1', '?', '?', '?', '?'},
                {'?', '5', '4', '?', '?', '?', '9', '1', '?'},
                {'?', '?', '7', '5', '?', '3', '2', '?', '?'},
        };
        Solution solve = SudokuSolver.normalSudokuRulesApply()
                .withGivenDigitsFromCharArray(initialState)
                .peek(SudokuSolver::printBoard)
                .solve()
                .withExactlyOneSolution();

        int[][] solution = {
                {8, 4, 6, 1, 7, 2, 5, 9, 3},
                {7, 3, 9, 6, 5, 8, 1, 4, 2},
                {5, 2, 1, 3, 4, 9, 7, 6, 8},
                {9, 6, 2, 8, 3, 7, 4, 5, 1},
                {4, 8, 5, 9, 2, 1, 3, 7, 6},
                {1, 7, 3, 4, 6, 5, 8, 2, 9},
                {2, 9, 8, 7, 1, 4, 6, 3, 5},
                {3, 5, 4, 2, 8, 6, 9, 1, 7},
                {6, 1, 7, 5, 9, 3, 2, 8, 4},
        };
        assertSolved(solve, solution);
    }

}