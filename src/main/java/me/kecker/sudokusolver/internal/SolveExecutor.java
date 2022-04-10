package me.kecker.sudokusolver.internal;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.Board;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.result.SolutionSet;

import java.util.Collection;

public class SolveExecutor {

    private final CpSolver solver;

    static {
        Loader.loadNativeLibraries();
    }

    public SolveExecutor() {
        solver = new CpSolver();
        solver.getParameters().setMaxTimeInSeconds(20.0);
        solver.getParameters().setEnumerateAllSolutions(true);
    }

    public SolutionSet solve(Board board, Collection<SudokuConstraint> constraints) {
        CpModel model = new CpModel();
        BoardVariables variables = board.createVariables(model);
        constraints.forEach(constraint -> constraint.apply(model, variables));

        SolutionCatcher solutionCatcher = new SolutionCatcher(variables, 2, board);

        CpSolverStatus status = solver.solve(model, solutionCatcher);

        return new SolutionSet(status, solutionCatcher.getSolutions());
    }

}
