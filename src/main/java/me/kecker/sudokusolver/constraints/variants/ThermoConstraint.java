package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;

public class ThermoConstraint implements SudokuConstraint {

    /**
     * Thermo cells in strictly increasing order
     */
    private final List<Position> thermoCells;

    public ThermoConstraint(List<Position> thermoCells) {
        this.thermoCells = thermoCells;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

        for (int i = 1; i < thermoCells.size(); i++) {
            IntVar previousVariable = boardVariables.get(thermoCells.get(i - 1));
            IntVar currentVariable = boardVariables.get(thermoCells.get(i));

            model.addGreaterThan(currentVariable, previousVariable);

        }
    }

    public List<Position> getThermoCells() {
        return thermoCells;
    }
}
