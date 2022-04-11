package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

public class AdjacentSumConstraint implements SudokuConstraint {

    private final int forbiddenSum;
    private final Position position1;
    private final Position position2;


    public AdjacentSumConstraint(int forbiddenSum, Position position1, Position position2) {
        this.forbiddenSum = forbiddenSum;
        this.position1 = position1;
        this.position2 = position2;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

        IntVar variable1 = boardVariables.get(position1);
        IntVar variable2 = boardVariables.get(position2);

        LinearExpr sum = LinearExpr.newBuilder().add(variable1).add(variable2).build();
        model.addEquality(sum, forbiddenSum);
    }

    public int getForbiddenSum() {
        return forbiddenSum;
    }

    public Position getPosition1() {
        return position1;
    }

    public Position getPosition2() {
        return position2;
    }
}
