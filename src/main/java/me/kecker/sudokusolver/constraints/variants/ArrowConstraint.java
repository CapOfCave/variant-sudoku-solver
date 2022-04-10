package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.Collection;

public class ArrowConstraint implements SudokuConstraint {

    private final Position bulbPosition;
    private final Collection<Position> shaftPositions;

    public ArrowConstraint(Position bulbPosition, Collection<Position> shaftPositions) {
        this.bulbPosition = bulbPosition;
        this.shaftPositions = shaftPositions;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        IntVar bulb = boardVariables.get(bulbPosition);
        IntVar[] shaft = shaftPositions.stream().map(boardVariables::get).toArray(IntVar[]::new);

        model.addEquality(LinearExpr.sum(shaft), bulb);
    }

    public Position getBulbPosition() {
        return bulbPosition;
    }

    public Collection<Position> getShaftPositions() {
        return shaftPositions;
    }
}
