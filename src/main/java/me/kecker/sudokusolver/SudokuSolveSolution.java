package me.kecker.sudokusolver;

import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

public class SudokuSolveSolution {
    private final BoardVariables variables;
    private final CpSolver solver;
    private final CpSolverStatus status;

    public SudokuSolveSolution(BoardVariables variables, CpSolver solver, CpSolverStatus status) {
        this.variables = variables;
        this.solver = solver;
        this.status = status;
    }

    public void printBoard() {
        SudokuSolverUtils.ValueSupplier valuesByPosition = (rowIdx, columnIdx) -> Long.toString(solver.value(variables.get(rowIdx, columnIdx)));
        SudokuSolverUtils.printBoard(valuesByPosition, variables.getBoard());
    }

    public CpSolverStatus getStatus() {
        return status;
    }

    public int value(int rowIdxStartingAtOne, int columnIdxStartingAtOne) {
        int rowIdx = rowIdxStartingAtOne - 1;
        int columnIdx = columnIdxStartingAtOne - 1;
        return (int) solver.value(variables.get(rowIdx, columnIdx));
    }
}
