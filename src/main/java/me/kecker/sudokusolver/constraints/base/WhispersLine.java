package me.kecker.sudokusolver.constraints.base;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;

public class WhispersLine implements SudokuConstraint {

    private final int minDifference;
    private final List<Position> line;

    public WhispersLine(int minDifference, List<Position> line) {
        this.minDifference = minDifference;
        this.line = line;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        for (int i = 1; i < line.size(); i++) {
            IntVar previous = boardVariables.get(line.get(i - 1));
            IntVar current = boardVariables.get(line.get(i));

            String differenceName = boardVariables.generateUniqueHelperVarName("whisperLine-difference");
            IntVar difference = model.newIntVar(-boardVariables.getMaxValue(), boardVariables.getMaxValue(), differenceName);
            model.addEquality(LinearExpr.newBuilder().add(previous).add(difference).build(), current);

            String absoluteName = boardVariables.generateUniqueHelperVarName("whisperLine-abs-difference");
            IntVar absolute = model.newIntVar(0, boardVariables.getMaxValue(), absoluteName);
            model.addAbsEquality(absolute, difference);

            model.addGreaterOrEqual(absolute, minDifference);

        }

    }
}
