package me.kecker.sudokusolver.constraints.negative;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.NegativeConstraint;

import java.util.Collection;
import java.util.Collections;

public class NegativeAdjacentPairSumConstraint extends NegativeConstraint {

    private final Collection<Integer> forbiddenSums;

    public NegativeAdjacentPairSumConstraint(Collection<Integer> forbiddenSums) {
        this.forbiddenSums = forbiddenSums;
    }

    public NegativeAdjacentPairSumConstraint(int forbiddenSum) {
        this.forbiddenSums = Collections.singleton(forbiddenSum);
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables, IntVar variable1, IntVar variable2) {
        LinearExpr sum = LinearExpr.newBuilder().add(variable1).add(variable2).build();
        forbiddenSums.forEach(forbiddenSum -> model.addDifferent(sum, forbiddenSum));
    }
}
