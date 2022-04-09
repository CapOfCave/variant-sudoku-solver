package me.kecker.sudokusolver;


import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import me.kecker.sudokusolver.constraints.normal.BoxesUniqueConstraint;
import me.kecker.sudokusolver.constraints.normal.ColumnsUniqueConstraint;
import me.kecker.sudokusolver.constraints.normal.GivenDigit;
import me.kecker.sudokusolver.constraints.normal.RowsUniqueConstraint;
import me.kecker.sudokusolver.constraints.variants.KillerConstraint;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class SudokuSolver {

    private final Board board;
    private final Collection<SudokuConstraint> constraints = new ArrayList<>();


    public SudokuSolver(Board board) {
        this.board = board;
    }

    public static SudokuSolver normalSudokuRulesApply() {
        return new SudokuSolver(Board.nByNOfSize(9))
                .withConstraint(new RowsUniqueConstraint())
                .withConstraint(new ColumnsUniqueConstraint())
                .withConstraint(new BoxesUniqueConstraint());
    }

    public static SudokuSolver fromBoard(Board board) {
        return new SudokuSolver(board);
    }

    public SudokuSolver withConstraints(Collection<? extends SudokuConstraint> constraints) {
        this.constraints.addAll(constraints);
        return this;
    }

    public SudokuSolver withConstraint(SudokuConstraint constraint) {
        this.constraints.add(constraint);
        return this;
    }

    public SudokuSolver withGivenDigit(int rowIdxStartingAtOne, int columnIdxStartingAtOne, int value) {
        int rowIdx = rowIdxStartingAtOne - 1;
        int columnIdx = columnIdxStartingAtOne - 1;
        var givenDigit = new GivenDigit(rowIdx, columnIdx, value);
        return this.withConstraint(givenDigit);
    }

    public SudokuSolveSolution solve() {
        // won't do anything when executed a second time
        Loader.loadNativeLibraries();

        CpModel model = new CpModel();
        BoardVariables variables = this.board.createVariables(model);
        this.constraints.forEach(constraints -> constraints.apply(model, variables));

        CpSolver solver = new CpSolver();

        SolutionPrinter cb = new SolutionPrinter(variables, DebugVarsHelper.get());
        solver.getParameters().setEnumerateAllSolutions(true);
        CpSolverStatus status = solver.solve(model, cb);

        return new SudokuSolveSolution(variables, solver, status);
    }

    public void printBoard() {
        List<GivenDigit> givenDigits = constraints.stream()
                .filter(sudokuConstraint -> sudokuConstraint instanceof GivenDigit)
                .map(sudokuConstraint -> (GivenDigit) sudokuConstraint)
                .toList();

        // efficient enough (for now)
        SudokuSolverUtils.ValueSupplier valuesByPosition = (int rowIdx, int columnIdx) ->
                givenDigits.stream()
                        .filter(givenDigit -> givenDigit.getRowIdx() == rowIdx && givenDigit.getColumnIdx() == columnIdx)
                        .map(GivenDigit::getValue)
                        .map(String::valueOf)
                        .findFirst()
                        .orElse(".");

        SudokuSolverUtils.printBoard(valuesByPosition, board);

    }

    public void printKillers() {
        List<KillerConstraint> killerConstraints = constraints.stream()
                .filter(sudokuConstraint -> sudokuConstraint instanceof KillerConstraint)
                .map(sudokuConstraint -> (KillerConstraint) sudokuConstraint)
                .toList();

        // efficient enough (for now)
        SudokuSolverUtils.ValueSupplier valuesByPosition = (int rowIdx, int columnIdx) ->
                killerConstraints.stream()
                        .flatMap(killerConstraint -> killerConstraint.getAffectedCells().stream())
                        .filter(position -> position.rowIdx() == rowIdx && position.columnIdx() == columnIdx)
                        .map(x -> "X")
                        .findFirst()
                        .orElse(".");

        SudokuSolverUtils.printBoard(valuesByPosition, board);
        System.out.println();

    }

    public SudokuSolver peek(Consumer<SudokuSolver> consumer) {
        consumer.accept(this);
        return this;
    }

}
