package me.kecker.sudokusolver.result;

import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.exceptions.SolutionException;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

import java.util.List;

public class SolutionSet {
    @Deprecated
    private final BoardVariables variables;
    @Deprecated
    private final CpSolver solver;
    @Deprecated
    private final CpSolverStatus status;

    private final List<Solution> solutions;

    public SolutionSet(BoardVariables variables, CpSolver solver, CpSolverStatus status, List<Solution> solutions) {
        this.variables = variables;
        this.solver = solver;
        this.status = status;
        this.solutions = solutions;
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
        return solutions.size();
    }

    public Solution withExactlyOneSolution() {
        if (solutions.size() != 1) {
            throw new SolutionException(String.format("Expected exactly 1 solution, but %d solutions were found.", solutions.size()));
        }
        return solutions.get(0);
    }
}
