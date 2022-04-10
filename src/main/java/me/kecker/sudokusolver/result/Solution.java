package me.kecker.sudokusolver.result;

import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.exceptions.SolutionException;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

import java.util.Collection;
import java.util.List;

public class Solution {
    private final BoardVariables variables;
    private final CpSolver solver;
    private final CpSolverStatus status;

    private final List<SingleSolution> singleSolutions;

    public Solution(BoardVariables variables, CpSolver solver, CpSolverStatus status, List<SingleSolution> singleSolutions) {
        this.variables = variables;
        this.solver = solver;
        this.status = status;
        this.singleSolutions = singleSolutions;
    }

    @Deprecated
    public void printBoard() {
        SudokuSolverUtils.ValueSupplier valuesByPosition = (rowIdx, columnIdx) -> Long.toString(solver.value(variables.get(rowIdx, columnIdx)));
        SudokuSolverUtils.printBoard(valuesByPosition, variables.getBoard());
    }

    @Deprecated
    public CpSolverStatus getStatus() {
        return status;
    }

    @Deprecated
    public int value(int rowIdxStartingAtOne, int columnIdxStartingAtOne) {
        int rowIdx = rowIdxStartingAtOne - 1;
        int columnIdx = columnIdxStartingAtOne - 1;
        return (int) solver.value(variables.get(rowIdx, columnIdx));
    }

    public int getSolutionCount() {
        return singleSolutions.size();
    }

    public SingleSolution withExactlyOneSolution() {
        if (singleSolutions.size() != 1) {
            throw new SolutionException(String.format("Expected exactly 1 solution, but %d solutions were found.", singleSolutions.size()));
        }
        return singleSolutions.get(0);
    }
}
