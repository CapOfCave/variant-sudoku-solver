package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;

public class PalindromeConstraint implements SudokuConstraint {


    private final List<Position> palindromeCells;

    public PalindromeConstraint(List<Position> palindromeCells) {
        this.palindromeCells = palindromeCells;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

        // what should be the max value of i?
        // if i is even:
        //   i < palindromeCells.size() / 2
        //   example: size = 4  =>  i in {0, 1}  =>  i < 4 / 2
        // if i is odd:
        //   i < (palindromeCells.size() - 1) / 2
        //   example: size = 5  =>  i in {0, 1}  =>  i < (5 - 1) / 2
        // due to integer division, (size - 1) / 2 = (int)(size / 2)
        for (int i = 0; i < palindromeCells.size() / 2; i++) {
            Position positionFromStart = palindromeCells.get(i);
            Position positionFromEnd = palindromeCells.get(palindromeCells.size() - 1 - i);
            IntVar fromStart = boardVariables.get(positionFromStart);
            IntVar fromEnd = boardVariables.get(positionFromEnd);
            model.addEquality(fromStart, fromEnd);
        }

    }
}
