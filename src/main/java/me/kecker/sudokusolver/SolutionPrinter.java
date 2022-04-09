package me.kecker.sudokusolver;

import com.google.ortools.sat.CpSolverSolutionCallback;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearArgument;

import java.util.Collection;

public class SolutionPrinter extends CpSolverSolutionCallback {
    private int solutionCount;
    private final IntVar[] variableArray;
    public SolutionPrinter(Collection<IntVar> variables) {
        variableArray = variables.toArray(IntVar[]::new);
    }

    @Override
    public void onSolutionCallback() {
        System.out.printf("Solution #%d: time = %.02f s%n", solutionCount, wallTime());
        for (IntVar v : variableArray) {
            System.out.printf("  %s = %d%n", v.getName(), value(v));
        }
        solutionCount++;
    }

    public int getSolutionCount() {
        return solutionCount;
    }


}
