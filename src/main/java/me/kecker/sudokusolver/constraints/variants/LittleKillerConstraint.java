package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.exceptions.InvalidConstraintException;
import me.kecker.sudokusolver.dtos.Offset;
import me.kecker.sudokusolver.dtos.Position;

import java.util.ArrayList;
import java.util.List;

public class LittleKillerConstraint implements SudokuConstraint {

    private final Position start;
    private final LittleKillerDirection direction;
    private final int sum;

    public LittleKillerConstraint(Position start, LittleKillerDirection direction, int sum) {
        this.start = start;
        this.direction = direction;
        this.sum = sum;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        validate(boardVariables);

        IntVar[] affectedCellsArray = generateAffectedCells(boardVariables);
        model.addEquality(LinearExpr.sum(affectedCellsArray), sum);
    }

    private void validate(BoardVariables boardVariables) {
        // throw exception if out of bounds
        if (!boardVariables.isInBounds(start)) {
            throw new InvalidConstraintException(String.format("Constraint must be inside the board, but was not for start %s and direction %s [sum=%d]", start, direction, sum));
        }
        // ... or if not on the edge
        Position previous = start.subtract(direction.offset);
        if (boardVariables.isInBounds(previous)) {
            throw new InvalidConstraintException(String.format("Constraint must be on the edge, but was not for start %s and direction %s [sum=%d]", start, direction, sum));
        }
    }

    private IntVar[] generateAffectedCells(BoardVariables boardVariables) {
        List<IntVar> affectedCells = new ArrayList<>();
        Position current = start;
        while(boardVariables.isInBounds(current)) {
            affectedCells.add(boardVariables.get(current));
            current = current.add(direction.offset);
        }
        return affectedCells.toArray(IntVar[]::new);
    }


    public enum LittleKillerDirection {
        UP_LEFT(new Offset(-1, -1)),
        DOWN_LEFT(new Offset(1, -1)),
        UP_RIGHT(new Offset(-1, 1)),
        DOWN_RIGHT(new Offset(1, 1));

        private final Offset offset;

        LittleKillerDirection(Offset offset) {
            this.offset = offset;
        }

    }
}
