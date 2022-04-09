package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;

import java.util.stream.IntStream;

/**
 * Cells that appear in the same position relative to their default regions must not contain the same number.
 */
public class DisjointGroupsConstraints implements SudokuConstraint {

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        int boxSize = boardVariables.getBoard().getBoxSizeX() * boardVariables.getBoard().getBoxSizeY();
        int boxCount = boardVariables.getBoard().getBoxCount();

        IntVar[][] boxes = IntStream.range(0, boxCount).mapToObj(boardVariables::getBox).toArray(IntVar[][]::new);
        for (int disjointPosition = 0; disjointPosition < boxSize; disjointPosition++) {
            IntVar[] disjointVars = new IntVar[boxes.length];
            for (int boxIdx = 0; boxIdx < boxes.length; boxIdx++) {
                disjointVars[boxIdx] = boxes[boxIdx][disjointPosition];
            }
            model.addAllDifferent(disjointVars);
        }

    }
}
