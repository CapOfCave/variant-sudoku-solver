package me.kecker.sudokusolver.constraints;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.dtos.Offset;
import me.kecker.sudokusolver.dtos.Pair;
import me.kecker.sudokusolver.dtos.Position;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class NegativeConstraint implements SudokuConstraint {

    private final Set<Pair> exceptions = new HashSet<>();

    private static final Collection<Offset> offsets = List.of(
            // only include each link once
            new Offset(0, 1),
            new Offset(1, 0)
    );

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        boardVariables.forEach((int rowIdx, int columnIdx, IntVar variable) -> {
            Position position = new Position(rowIdx, columnIdx);
            offsets.forEach(offset -> {
                int otherRowIdx = rowIdx + offset.rowDif();
                int otherColumnIdx = columnIdx + offset.columnDif();
                if (!boardVariables.isInBounds(otherRowIdx, otherColumnIdx)) return;

                Position otherPosition = new Position(otherRowIdx, otherColumnIdx);
                Pair pair = new Pair(position, otherPosition);

                // ignore all pairs where the positive constraint is enforced
                if (exceptions.contains(pair)) return;

                IntVar current = boardVariables.get(position);
                IntVar other = boardVariables.get(otherPosition);

                this.apply(model, boardVariables, current, other);
            });

        });
    }

    public abstract void apply(CpModel model, BoardVariables boardVariables, IntVar variable1, IntVar variable2);


    public void addException(Pair pair) {
        exceptions.add(pair);
    }
}
