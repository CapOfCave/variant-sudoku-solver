package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.exceptions.InvalidConstraintException;

public class SingleDiagonalConstraint implements SudokuConstraint {

    private final DiagonalDirection direction;

    public SingleDiagonalConstraint(DiagonalDirection direction) {
        this.direction = direction;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        if (boardVariables.getColumnCount() != boardVariables.getRowCount()) {
            throw new InvalidConstraintException("SingleDiagonalConstraint only works on square boards");
        }
        int boardSize = boardVariables.getColumnCount(); // = boardVariables.getRowCount()
        IntVar[] diagonalCells = new IntVar[boardSize];
        for (int i = 0; i < boardSize; i++) {
            int columnIdx = switch (direction) {
                case NEGATIVE -> i;
                case POSITIVE -> boardSize - i - 1;
            };
            diagonalCells[i] = boardVariables.get(i, columnIdx);
        }
        model.addAllDifferent(diagonalCells);
    }

    public enum DiagonalDirection {
        /* Bottom Left to Top Right */ POSITIVE,
        /* Top Left to Bottom Right */ NEGATIVE
    }

}
