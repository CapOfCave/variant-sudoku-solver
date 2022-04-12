package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;
import java.util.stream.IntStream;

/**
 * The numbers on a Renban Line must form a sequence without gaps, but not necessarily in the natural order (e.g. 2-3-4-5 or 3-5-2-4).
 */
public class RenbanLineConstraint implements SudokuConstraint {

    private final List<Position> line;

    public RenbanLineConstraint(List<Position> line) {
        this.line = line;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        // list of all numbers on this line in consecutive order
        IntVar[] allNumbers = IntStream.range(0, line.size())
                .mapToObj(i -> model.newIntVar(
                        boardVariables.getMinValue(),
                        boardVariables.getMaxValue(),
                        boardVariables.generateUniqueHelperVarName(String.format("renban-allValues-i%d", i)))
                ).toArray(IntVar[]::new);
        for (int i = 1; i < allNumbers.length; i++) {
            IntVar previous = allNumbers[i - 1];
            IntVar current = allNumbers[i];

            // current = previous + 1

            LinearExpr sum = LinearExpr.newBuilder()
                    .add(previous)
                    .add(1)
                    .build();
            model.addEquality(current, sum);
        }
        // index of the elements of line in allNumbers
        IntVar[] indices = IntStream.range(0, line.size())
                .mapToObj(i -> model.newIntVar(
                        0,
                        line.size(),
                        boardVariables.generateUniqueHelperVarName(String.format("renban-indices-i%d", i)))
                ).toArray(IntVar[]::new);

        for (int i = 0; i < line.size(); i++) {
            IntVar current = boardVariables.get(line.get(i));
            model.addElement(indices[i], allNumbers, current);
        }
        // each element of line maps to one element in allNumbers
        model.addAllDifferent(indices);

    }
}
