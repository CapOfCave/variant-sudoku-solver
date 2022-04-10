package me.kecker.sudokusolver.result;

import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.exceptions.SolutionException;

import java.util.List;

public class SolutionSet {

    private final CpSolverStatus status;
    private final List<Solution> solutions;

    public SolutionSet(CpSolverStatus status, List<Solution> solutions) {
        this.status = status;
        this.solutions = solutions;
    }

    public boolean wasSuccessful() {
        return status == CpSolverStatus.OPTIMAL || status == CpSolverStatus.FEASIBLE;
    }

    public Solution withExactlyOneSolution() {
        if (!wasSuccessful()) {
            throw new SolutionException(String.format("Could not find a solution in time, the status is %s.", status));
        }
        if (solutions.size() != 1) {
            throw new SolutionException(String.format("Expected exactly 1 solution, but %d solutions were found.", solutions.size()));
        }
        return solutions.get(0);
    }
}
