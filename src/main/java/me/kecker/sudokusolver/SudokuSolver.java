package me.kecker.sudokusolver;


import me.kecker.sudokusolver.constraints.AdjacentPairConstraint;
import me.kecker.sudokusolver.constraints.NegativeConstraint;
import me.kecker.sudokusolver.constraints.SudokuConstraint;
import me.kecker.sudokusolver.constraints.base.AdjacentPairSumConstraint;
import me.kecker.sudokusolver.constraints.normal.BoxesUniqueConstraint;
import me.kecker.sudokusolver.constraints.normal.ColumnsUniqueConstraint;
import me.kecker.sudokusolver.constraints.normal.GivenDigit;
import me.kecker.sudokusolver.constraints.normal.RowsUniqueConstraint;
import me.kecker.sudokusolver.constraints.variants.ArrowConstraint;
import me.kecker.sudokusolver.constraints.variants.EvenConstraint;
import me.kecker.sudokusolver.constraints.variants.KillerConstraint;
import me.kecker.sudokusolver.constraints.variants.LittleKillerConstraint;
import me.kecker.sudokusolver.constraints.variants.OddConstraint;
import me.kecker.sudokusolver.constraints.variants.RatioConstraint;
import me.kecker.sudokusolver.constraints.variants.SingleDiagonalConstraint;
import me.kecker.sudokusolver.constraints.variants.ThermoConstraint;
import me.kecker.sudokusolver.constraints.variants.VSumConstraint;
import me.kecker.sudokusolver.constraints.variants.XSumConstraint;
import me.kecker.sudokusolver.dtos.Position;
import me.kecker.sudokusolver.exceptions.InvalidConstraintException;
import me.kecker.sudokusolver.internal.SolveExecutor;
import me.kecker.sudokusolver.result.SolutionSet;
import me.kecker.sudokusolver.utils.SudokuSolverUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SudokuSolver {

    private final Board board;
    private final Collection<SudokuConstraint> constraints = new ArrayList<>();

    /**
     * Negative constraints are **also** stored separately, so that finding them when new exceptions are added is easier
     * <p>
     * Negative constraints are implicit singletons, so the Mapping Class -> instance is 1 -> 0..1
     */
    private final Map<Class<?>, NegativeConstraint> negativeConstraints = new HashMap<>();


    public SudokuSolver(Board board) {
        this.board = board;
    }

    public static SudokuSolver normalSudokuRulesApply() {
        return new SudokuSolver(Board.nByNOfSize(9))
                .withNormalSudokuRulesConstraints();
    }

    public static SudokuSolver fromBoard(Board board) {
        return new SudokuSolver(board);
    }


    public SudokuSolver withConstraint(SudokuConstraint constraint) {
        if (constraint instanceof NegativeConstraint negativeConstraint) {
            registerNewNegativeConstraint(negativeConstraint);
        }
        if (constraint instanceof AdjacentPairConstraint adjacentPairConstraint) {
            registerAdjacentPairConstraint(adjacentPairConstraint);
        }
        this.constraints.add(constraint);
        return this;
    }

    private void registerAdjacentPairConstraint(AdjacentPairConstraint adjacentPairConstraint) {
        this.negativeConstraints.values().forEach(negativeConstraint -> {
            if (adjacentPairConstraint.getAffectedNegativeConstraints().contains(negativeConstraint.getClass())) {
                negativeConstraint.addException(adjacentPairConstraint.getAffectedCells());
            }
        });
    }

    private void registerNewNegativeConstraint(NegativeConstraint negativeConstraint) {
        if (negativeConstraints.containsKey(negativeConstraint.getClass())) {
            throw new InvalidConstraintException("Negative constraint " + negativeConstraint.getClass() + " cannot be added again as it was already added before.");
        }
        this.constraints.forEach(constraint -> {
            if (constraint instanceof AdjacentPairConstraint adjacentPairConstraint
                    && adjacentPairConstraint.getAffectedNegativeConstraints().contains(negativeConstraint.getClass())) {
                negativeConstraint.addException(adjacentPairConstraint.getAffectedCells());
            }
        });
        negativeConstraints.put(negativeConstraint.getClass(), negativeConstraint);

    }

    public SudokuSolver withConstraints(Collection<? extends SudokuConstraint> constraints) {
        constraints.forEach(this::withConstraint);
        return this;
    }

    public SudokuSolver withNormalSudokuRulesConstraints() {
        return this.withConstraint(new RowsUniqueConstraint())
                .withConstraint(new ColumnsUniqueConstraint())
                .withConstraint(new BoxesUniqueConstraint());
    }

    public SudokuSolver withGivenDigit(int rowIdxStartingAtOne, int columnIdxStartingAtOne, int value) {
        int rowIdx = rowIdxStartingAtOne - 1;
        int columnIdx = columnIdxStartingAtOne - 1;
        var givenDigit = new GivenDigit(rowIdx, columnIdx, value);
        return this.withConstraint(givenDigit);
    }

    public SudokuSolver withGivenDigit(Position position, int value) {
        var givenDigit = new GivenDigit(position.rowIdx(), position.columnIdx(), value);
        return this.withConstraint(givenDigit);
    }


    public SudokuSolver withGivenDigitsFromIntArray(int[][] intBoard) {
        for (int rowIdx = 0; rowIdx < intBoard.length; rowIdx++) {
            for (int columnIdx = 0; columnIdx < intBoard.length; columnIdx++) {
                if (intBoard[rowIdx][columnIdx] <= 0) continue;
                this.withGivenDigit(rowIdx + 1, columnIdx + 1, intBoard[rowIdx][columnIdx]);
            }
        }
        return this;
    }

    public SudokuSolver withGivenDigitsFromIntegerArray(Integer[][] integerBoard) {
        for (int rowIdx = 0; rowIdx < integerBoard.length; rowIdx++) {
            for (int columnIdx = 0; columnIdx < integerBoard.length; columnIdx++) {
                if (integerBoard[rowIdx][columnIdx] == null) continue;
                this.withGivenDigit(rowIdx + 1, columnIdx + 1, integerBoard[rowIdx][columnIdx]);
            }
        }
        return this;
    }

    public SudokuSolver withGivenDigitsFromCharArray(char[][] charBoard) {
        for (int rowIdx = 0; rowIdx < charBoard.length; rowIdx++) {
            for (int columnIdx = 0; columnIdx < charBoard.length; columnIdx++) {
                if (!Character.isDigit(charBoard[rowIdx][columnIdx])) continue;
                this.withGivenDigit(rowIdx + 1, columnIdx + 1, Character.getNumericValue(charBoard[rowIdx][columnIdx]));
            }
        }
        return this;
    }

    /**
     * 'E' or 'e': Even
     * 'O' or 'o': Odd
     * all other chars: unknown
     */
    public SudokuSolver withEvenOddConstraintsFromCharArray(char[][] charBoard) {
        for (int rowIdx = 0; rowIdx < charBoard.length; rowIdx++) {
            for (int columnIdx = 0; columnIdx < charBoard.length; columnIdx++) {
                switch (charBoard[rowIdx][columnIdx]) {
                    case 'e', 'E' -> this.withConstraint(new EvenConstraint(new Position(rowIdx, columnIdx)));
                    case 'o', 'O' -> this.withConstraint(new OddConstraint(new Position(rowIdx, columnIdx)));
                }
            }
        }
        return this;
    }

    public SudokuSolver withNonRepeatingMainDiagonals() {
        return withConstraint(new SingleDiagonalConstraint(SingleDiagonalConstraint.DiagonalDirection.POSITIVE))
                .withConstraint(new SingleDiagonalConstraint(SingleDiagonalConstraint.DiagonalDirection.NEGATIVE));
    }

    public SudokuSolver withLittleKillerConstraint(int rowIdxStartingAt1, int columnIdxStartingAt1, LittleKillerConstraint.LittleKillerDirection direction, int sum) {
        return withConstraint(new LittleKillerConstraint(new Position(rowIdxStartingAt1 - 1, columnIdxStartingAt1 - 1), direction, sum));
    }

    public SolutionSet solve() {
        SolveExecutor solveExecutor = new SolveExecutor();
        return solveExecutor.solve(this.board, this.constraints);
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


    }

    public void printThermos() {
        List<ThermoConstraint> thermoConstraints = constraints.stream()
                .filter(sudokuConstraint -> sudokuConstraint instanceof ThermoConstraint)
                .map(sudokuConstraint -> (ThermoConstraint) sudokuConstraint)
                .toList();

        // efficient enough (for now)
        SudokuSolverUtils.ValueSupplier valuesByPosition = (int rowIdx, int columnIdx) ->
                thermoConstraints.stream()
                        .flatMap(thermoConstraint -> thermoConstraint.getThermoCells().stream())
                        .filter(position -> position.rowIdx() == rowIdx && position.columnIdx() == columnIdx)
                        .map(x -> "X")
                        .findFirst()
                        .orElse(".");

        SudokuSolverUtils.printBoard(valuesByPosition, board);
    }


    public void printArrows() {
        List<ArrowConstraint> arrowConstraints = constraints.stream()
                .filter(sudokuConstraint -> sudokuConstraint instanceof ArrowConstraint)
                .map(sudokuConstraint -> (ArrowConstraint) sudokuConstraint)
                .toList();

        // efficient enough (for now)
        SudokuSolverUtils.ValueSupplier valuesByPosition = (int rowIdx, int columnIdx) ->
                arrowConstraints.stream()
                        .map(ArrowConstraint::getBulbPosition)
                        .filter(position -> position.rowIdx() == rowIdx && position.columnIdx() == columnIdx)
                        .map(x -> "X")
                        .findFirst()
                        .or(() -> arrowConstraints.stream()
                                .flatMap(arrowConstraint -> arrowConstraint.getShaftPositions().stream())
                                .filter(position -> position.rowIdx() == rowIdx && position.columnIdx() == columnIdx)
                                .map(x -> "O")
                                .findFirst())
                        .orElse(".");

        SudokuSolverUtils.printBoard(valuesByPosition, board);
    }

    public SudokuSolver peek(Consumer<SudokuSolver> consumer) {
        consumer.accept(this);
        return this;
    }


    public void printXVs() {
        List<AdjacentPairSumConstraint> xvConstraints = constraints.stream()
                .filter(sudokuConstraint -> sudokuConstraint instanceof XSumConstraint || sudokuConstraint instanceof VSumConstraint)
                .map(sudokuConstraint -> (AdjacentPairSumConstraint) sudokuConstraint)
                .toList();

        // efficient enough (for now)
        SudokuSolverUtils.ValueSupplier valuesByPosition = (int rowIdx, int columnIdx) ->
                xvConstraints.stream()
                        .flatMap(xvConstraint -> xvConstraint.getAffectedCells().stream())
                        .filter(position -> position.rowIdx() == rowIdx && position.columnIdx() == columnIdx)
                        .map(x -> "X")
                        .findFirst()
                        .orElse(".");

        SudokuSolverUtils.printBoard(valuesByPosition, board);

    }

    public void printRatios() {
        List<RatioConstraint> xvConstraints = constraints.stream()
                .filter(sudokuConstraint -> sudokuConstraint instanceof RatioConstraint)
                .map(sudokuConstraint -> (RatioConstraint) sudokuConstraint)
                .toList();

        // efficient enough (for now)
        SudokuSolverUtils.ValueSupplier valuesByPosition = (int rowIdx, int columnIdx) ->
                xvConstraints.stream()
                        .flatMap(xvConstraint -> xvConstraint.getAffectedCells().stream())
                        .filter(position -> position.rowIdx() == rowIdx && position.columnIdx() == columnIdx)
                        .map(x -> "X")
                        .findFirst()
                        .orElse(".");

        SudokuSolverUtils.printBoard(valuesByPosition, board);
    }
}
