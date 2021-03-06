package me.kecker.sudokusolver;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.IntegerVariableProto;
import me.kecker.sudokusolver.exceptions.InvalidBoardException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {


    @BeforeAll
    static void beforeAll() {
        Loader.loadNativeLibraries();
    }

    @Test
    void testNByNOfSizeNotSquareThrowsException() {
        assertThrows(InvalidBoardException.class, () -> Board.nByNOfSize(6));
    }

    @Test
    void testNByNOfSizeSquareDoesNotThrow() {
        assertDoesNotThrow(() -> Board.nByNOfSize(9));
    }

    @Test
    void test9By9CreatesCorrectVariables() {
        Board board = Board.nByNOfSize(9);
        assertEquals(9, board.getRowCount());
        assertEquals(9, board.getColumnCount());

        CpModel model = new CpModel();
        BoardVariables variables = board.createVariables(model);
        assertThat(variables.getColumnCount()).isEqualTo(9);
        assertThat(variables.getRowCount()).isEqualTo(9);
        assertEquals(81, model.model().getVariablesCount());
        assertThat(model.model().getVariablesList())
                .allSatisfy(variable -> assertThat(variable.getName()).matches(Pattern.compile("r[1-9]c[1-9]")));
    }

    @Test
    void testNotSquareCreatesCorrectVariables() {

        Board board = new Board(2, 3, 5, 4, 1, 9);
        assertEquals(12, board.getRowCount());
        assertEquals(10, board.getColumnCount());
        CpModel model = new CpModel();
        BoardVariables variables = board.createVariables(model);
        assertThat(variables.getColumnCount()).isEqualTo(10);
        assertThat(variables.getRowCount()).isEqualTo(12);

        assertEquals(120, model.model().getVariablesCount());
        assertThat(model.model().getVariablesList())
                .map(IntegerVariableProto::getName)
                .allSatisfy(variable -> assertThat(variable).matches(Pattern.compile("r[1-2]?[0-9]c1?[0-9]")));
    }
}
