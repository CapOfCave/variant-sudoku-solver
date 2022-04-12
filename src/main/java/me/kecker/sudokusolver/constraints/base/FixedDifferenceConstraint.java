package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.AdjacentPairConstraint;
import me.kecker.sudokusolver.dtos.Pair;

public class FixedDifferenceConstraint extends AdjacentPairConstraint {

    private final int difference;

    protected FixedDifferenceConstraint(int difference, Pair affectedCells) {
        super(affectedCells);
        this.difference = difference;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables, IntVar variable1, IntVar variable2) {
        BoolVar variable1Larger = model.newBoolVar(boardVariables.generateUniqueHelperVarName("consecutive-bool"));
        model.addEquality(LinearExpr.newBuilder().add(variable2).add(difference), variable1).onlyEnforceIf(variable1Larger);
        model.addEquality(LinearExpr.newBuilder().add(variable1).add(difference), variable2).onlyEnforceIf(variable1Larger.not());
    }
}