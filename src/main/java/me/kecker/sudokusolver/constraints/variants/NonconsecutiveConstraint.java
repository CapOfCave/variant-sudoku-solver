package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Offset;

import java.util.Collection;
import java.util.List;

public class NonconsecutiveConstraint implements SudokuConstraint {

    private static final Collection<Offset> offsets = List.of(
            // only include each link once
            new Offset(0, 1),
            new Offset(1, 0)
    );

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        boardVariables.forEach((int rowIdx, int columnIdx, IntVar variable) -> {

            offsets.forEach(offset -> {
                int otherRowIdx = rowIdx + offset.rowDif();
                int otherColumnIdx = columnIdx + offset.columnDif();
                if (!boardVariables.isInBounds(otherRowIdx, otherColumnIdx)) return;

                IntVar other = boardVariables.get(otherRowIdx, otherColumnIdx);

                String differenceName = boardVariables.generateUniqueHelperVarName(String.format("nonconsecutive-difference-r%dc%d-r%dc%d", rowIdx, columnIdx, otherRowIdx, otherColumnIdx));
                IntVar difference = model.newIntVar(-boardVariables.getMaxValue(), boardVariables.getMaxValue(), differenceName);
                // difference = other - variable <=> variable + difference = other
                model.addEquality(LinearExpr.newBuilder().add(variable).add(difference).build(), other);

                String absoluteName = boardVariables.generateUniqueHelperVarName(String.format("nonconsecutive-abs-difference-r%dc%d-r%dc%d", rowIdx, columnIdx, otherRowIdx, otherColumnIdx));
                IntVar absolute = model.newIntVar(0, boardVariables.getMaxValue(), absoluteName);
                model.addAbsEquality(absolute, difference);

                // nonconsecutive: for all pairs of consecutive numbers, the abs difference must not be 1
                model.addDifferent(absolute, 1);
            });

        });
    }
}
