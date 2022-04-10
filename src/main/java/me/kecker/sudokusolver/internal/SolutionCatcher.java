package me.kecker.sudokusolver.internal;

import com.google.ortools.sat.CpSolverSolutionCallback;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.result.SingleSolution;

import java.util.ArrayList;
import java.util.List;

public class SolutionCatcher extends CpSolverSolutionCallback {

    private final BoardVariables variables;
    private final int solutionLimit;

    private final List<SingleSolution> solutions;
    private final Board board;

    public SolutionCatcher(BoardVariables variables, int solutionLimit, Board board) {
        this.variables = variables;
        this.solutionLimit = solutionLimit;
        this.solutions = new ArrayList<>(solutionLimit);
        this.board = board;
    }

    @Override
    public void onSolutionCallback() {
        int[][] values = new int[variables.getRowCount()][variables.getColumnCount()];
        variables.forEach((int rowIdx, int columnIdx, IntVar variable) -> {
            values[rowIdx][columnIdx] = (int) value(variable);
        });
        solutions.add(new SingleSolution(board, values));

        if (solutions.size() >= solutionLimit) {
            System.out.printf("Stop search after %d solutions%n", solutionLimit);
            stopSearch();
        }
    }

    public List<SingleSolution> getSolutions() {
        return solutions;
    }
}
