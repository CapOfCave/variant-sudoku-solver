package me.kecker.sudokusolver;


import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class SudokuSolverTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Loader.loadNativeLibraries();

        CpModel model = new CpModel();

        int size = 9;
        IntVar[][] fields = new IntVar[size][size];

        // define variables
        for (int rowIdx = 0; rowIdx < size; rowIdx++) {
            for (int columnIdx = 0; columnIdx < size; columnIdx++) {
                fields[rowIdx][columnIdx] = model.newIntVar(1, size, String.format("(%d|%d)", rowIdx, columnIdx));
            }
        }

        // horizontal
        for (int rowIdx = 0; rowIdx < size; rowIdx++) {
            model.addAllDifferent(fields[rowIdx]);
        }

        // vertical
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {

            IntVar[] columnFields = new IntVar[size];
            for (int rowIdx = 0; rowIdx < size; rowIdx++) {
                columnFields[rowIdx] = fields[rowIdx][columnIndex];
            }
            model.addAllDifferent(columnFields);
        }

        // box
        for (int boxId = 0; boxId < size; boxId++) {
            int boxX = boxId % 3;
            int boxY = boxId / 3;
            IntVar[] boxFields = new IntVar[size];

            for (int i = 0; i < 3; i++) {
                int rowIdx = i + boxY * 3;
                for (int j = 0; j < 3; j++) {
                    int columnIdx = j + boxX * 3;
                    boxFields[i * 3 + j] = fields[rowIdx][columnIdx];
                }
            }
            model.addAllDifferent(boxFields);

        }

        // givens
        model.addEquality(fields[0][3], 8);
        model.addEquality(fields[0][5], 1);
        model.addEquality(fields[1][7], 4);
        model.addEquality(fields[1][8], 3);
        model.addEquality(fields[2][0], 5);
        model.addEquality(fields[3][4], 7);
        model.addEquality(fields[3][6], 8);
        model.addEquality(fields[4][6], 1);
        model.addEquality(fields[5][1], 2);
        model.addEquality(fields[5][4], 3);
        model.addEquality(fields[6][0], 6);
        model.addEquality(fields[6][7], 7);
        model.addEquality(fields[6][8], 5);
        model.addEquality(fields[7][2], 3);
        model.addEquality(fields[7][3], 4);
        model.addEquality(fields[8][3], 2);
        model.addEquality(fields[8][6], 6);


        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);

        assertEquals(CpSolverStatus.OPTIMAL, status);

        assertEquals(8, solver.value(fields[1][1]));
        assertEquals(6, solver.value(fields[1][2]));
    }
}
