package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.exceptions.InvalidConstraintException;
import me.kecker.sudokusolver.dtos.Offset;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;

public class CloneConstraint implements SudokuConstraint {

    private final List<Position> originalCells;
    private final Offset cloneOffset;

    public CloneConstraint(List<Position> originalCells, Offset cloneOffset) {
        this.originalCells = originalCells;
        this.cloneOffset = cloneOffset;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

        for (Position originalPosition : originalCells) {
            Position clonePosition = originalPosition.add(cloneOffset);

            if (!boardVariables.isInBounds(originalPosition)) {
                throw new InvalidConstraintException(String.format("Original cloned cell (r%dc%d) is outside board bounds.", originalPosition.rowIdx(), originalPosition.columnIdx()));
            }

            if (!boardVariables.isInBounds(clonePosition)) {
                throw new InvalidConstraintException(String.format("Target cloned cell (r%dc%d) is outside board bounds.", clonePosition.rowIdx(), clonePosition.columnIdx()));
            }

            IntVar original = boardVariables.get(originalPosition);
            IntVar clone = boardVariables.get(clonePosition);
            model.addEquality(original, clone);
        }

    }
}
