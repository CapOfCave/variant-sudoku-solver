package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearArgument;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.utils.SudokuPosition;

public class OddEvenConstraint implements SudokuConstraint {

    private final SudokuPosition position;
    private final Parity parity;

    public OddEvenConstraint(SudokuPosition position, Parity parity) {
        this.position = position;
        this.parity = parity;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        IntVar intVar = boardVariables.get(position);
        LinearArgument target = switch (parity) {
            case ODD -> model.trueLiteral();
            case EVEN -> model.falseLiteral();
        };
        model.addModuloEquality(target, intVar, 2);
    }

    public enum Parity {
        EVEN, ODD
    }
}