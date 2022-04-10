package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.utils.SudokuPosition;

import java.util.Collection;

public class ArrowConstraint implements SudokuConstraint {

    private final SudokuPosition bulbPosition;
    private final Collection<SudokuPosition> shaftPositions;

    public ArrowConstraint(SudokuPosition bulbPosition, Collection<SudokuPosition> shaftPositions) {
        this.bulbPosition = bulbPosition;
        this.shaftPositions = shaftPositions;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        IntVar bulb = boardVariables.get(bulbPosition);
        IntVar[] shaft = shaftPositions.stream().map(boardVariables::get).toArray(IntVar[]::new);

        model.addEquality(LinearExpr.sum(shaft), bulb);
    }

    public SudokuPosition getBulbPosition() {
        return bulbPosition;
    }

    public Collection<SudokuPosition> getShaftPositions() {
        return shaftPositions;
    }
}
