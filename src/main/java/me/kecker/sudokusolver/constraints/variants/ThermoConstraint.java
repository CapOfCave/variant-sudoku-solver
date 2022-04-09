package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.utils.SudokuPosition;

import java.util.List;

public class ThermoConstraint implements SudokuConstraint {

    /**
     * Thermo cells in strictly increasing order
     */
    private final List<SudokuPosition> thermoCells;

    public ThermoConstraint(List<SudokuPosition> thermoCells) {
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

    public List<SudokuPosition> getThermoCells() {
        return thermoCells;
    }
}
