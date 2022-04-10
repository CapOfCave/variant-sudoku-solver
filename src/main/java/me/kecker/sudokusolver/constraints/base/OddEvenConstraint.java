package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearArgument;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

public class OddEvenConstraint implements SudokuConstraint {

    private final Position position;
    private final Parity parity;

    public OddEvenConstraint(Position position, Parity parity) {
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