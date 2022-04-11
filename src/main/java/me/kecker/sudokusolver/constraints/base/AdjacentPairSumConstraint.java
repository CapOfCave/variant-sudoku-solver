package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.AdjacentPairConstraint;
import me.kecker.sudokusolver.dtos.Pair;

public class AdjacentPairSumConstraint extends AdjacentPairConstraint {

    private final int forbiddenSum;

    public AdjacentPairSumConstraint(int forbiddenSum, Pair affectedCells) {
        super(affectedCells);
        this.forbiddenSum = forbiddenSum;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables, IntVar variable1, IntVar variable2) {
        LinearExpr sum = LinearExpr.newBuilder().add(variable1).add(variable2).build();
        model.addEquality(sum, forbiddenSum);
    }

    public int getForbiddenSum() {
        return forbiddenSum;
    }
}
