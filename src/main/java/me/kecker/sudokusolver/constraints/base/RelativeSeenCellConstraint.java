package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.utils.Offset;

import java.util.Collection;

public class RelativeSeenCellConstraint implements SudokuConstraint {

    private final Collection<Offset> offsets;

    public RelativeSeenCellConstraint(Collection<Offset> offsets) {
        this.offsets = offsets;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        for (int rowIdx = 0; rowIdx < boardVariables.getRowCount(); rowIdx++) {
            for (int columnIdx = 0; columnIdx < boardVariables.getColumnCount(); columnIdx++) {
                applyToCell(model, boardVariables, rowIdx, columnIdx);
            }
        }
    }

    public void applyToCell(CpModel model, BoardVariables boardVariables, int rowIdx, int columnIdx) {
        IntVar selectedCell = boardVariables.get(rowIdx, columnIdx);
        for (Offset offset : offsets) {
            int rowIdxOther = rowIdx + offset.rowDif();
            int columnIdxOther = columnIdx + offset.columnDif();
            if (!boardVariables.isInBounds(rowIdxOther, columnIdxOther)) {
                continue;
            }
            IntVar other = boardVariables.get(rowIdxOther, columnIdxOther);
            model.addDifferent(selectedCell, other);
        }
    }
}
