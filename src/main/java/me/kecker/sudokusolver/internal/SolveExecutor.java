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

    public SolutionSet solve(Board board, Collection<SudokuConstraint> constraints) {
        // won't do anything when executed a second time
        Loader.loadNativeLibraries();

        CpModel model = new CpModel();
        BoardVariables variables = board.createVariables(model);
        constraints.forEach(constraint -> constraint.apply(model, variables));

        CpSolver solver = new CpSolver();

        SolutionCatcher solutionCatcher = new SolutionCatcher(variables, 2, board);

        solver.getParameters().setMaxTimeInSeconds(20.0);
        solver.getParameters().setEnumerateAllSolutions(true);
        CpSolverStatus status = solver.solve(model, solutionCatcher);

        return new SolutionSet(variables, solver, status, solutionCatcher.getSolutions());
    }

}
