package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.AdjacentPairConstraint;
import me.kecker.sudokusolver.dtos.Pair;

public class RatioConstraint extends AdjacentPairConstraint {

    private final int numerator;
    private final int denominator;

    public RatioConstraint(int numerator, int denominator, Pair affectedCells) {
        super(affectedCells);
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public RatioConstraint(int ratio, Pair affectedCells) {
        this(ratio, 1, affectedCells);
    }

    public RatioConstraint(Pair affectedCells) {
        this(2, affectedCells);
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables, IntVar variable1, IntVar variable2) {
        BoolVar helper = model.newBoolVar(boardVariables.generateUniqueHelperVarName("ratio"));
        // one of the following equations must be valid (since 'helper' is either true or false)
        model.addEquality(LinearExpr.term(variable1, this.denominator), LinearExpr.term(variable2, this.numerator)).onlyEnforceIf(helper.not());
        model.addEquality(LinearExpr.term(variable1, this.numerator), LinearExpr.term(variable2, this.denominator)).onlyEnforceIf(helper);
    }
}
