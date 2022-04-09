package me.kecker.sudokusolver;

import com.google.ortools.sat.CpSolverSolutionCallback;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearArgument;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

import java.util.Collection;

public class SolutionPrinter extends CpSolverSolutionCallback {
    private int solutionCount;
    private final BoardVariables boardVariables;
    private final Collection<IntVar> additionalVariables;
    public SolutionPrinter(BoardVariables boardVariables, Collection<IntVar> additionalVariables) {
        this.boardVariables = boardVariables;
        this.additionalVariables = additionalVariables;
    }

    @Override
    public void onSolutionCallback() {
        System.out.printf("Solution #%d: time = %.02f s%n", solutionCount, wallTime());

        SudokuSolverUtils.ValueSupplier valuesByPosition = (rowIdx, columnIdx) -> Long.toString(value(boardVariables.get(rowIdx, columnIdx)));
        SudokuSolverUtils.printBoard(valuesByPosition, boardVariables.getBoard());

        for (IntVar v : additionalVariables) {
            System.out.printf("  %s = %d%n", v.getName(), value(v));
        }
        System.out.println();
        solutionCount++;
    }

    public int getSolutionCount() {
        return solutionCount;
    }


}
