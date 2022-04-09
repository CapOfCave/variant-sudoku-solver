package me.kecker.sudokusolver.constraints.variants;

import com.google.ortools.sat.AutomatonConstraint;
import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;
import me.kecker.sudokusolver.BoardVariables;
import me.kecker.sudokusolver.SudokuConstraint;
import me.kecker.sudokusolver.utils.SudokuDirection;

import java.util.List;
import java.util.function.IntFunction;

public class SandwichConstraint implements SudokuConstraint {

    private final SudokuDirection direction;
    /**
     * colIdx if direction == COLUMN
     * rowIdx if direction == ROW
     */
    private final int rowOrColumnIdx;
    private final int crust1;
    private final int crust2;
    private final int value;

    private static final long FIELD_OUTSIDE_SANDWICH = 0;
    private static final long FIELD_IS_SANDWICH_CRUST = 1;
    private static final long FIELD_INSIDE_SANDWICH = 2;

    public SandwichConstraint(SudokuDirection direction, int rowOrColumnIdx, int crust1, int crust2, int value) {
        this.direction = direction;
        this.rowOrColumnIdx = rowOrColumnIdx;
        this.crust1 = crust1;
        this.crust2 = crust2;
        this.value = value;
    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {
        IntFunction<IntVar> getAtPosition = (int position) -> switch (direction) {
            case ROW -> boardVariables.get(rowOrColumnIdx, position);
            case COLUMN -> boardVariables.get(position, rowOrColumnIdx);
        };

        IntVar[] stateVars = createValidStateVars(model, boardVariables, getAtPosition);
        createSandwichSumConstraint(model, boardVariables, stateVars, getAtPosition);

    }

    /**
     * Make sure that all numbers inside the sandwich sum to the target value.
     *
     * @implNote All numbers inside the sandwich are given a weight of 1, all others get a weight of 0.
     * That way, the weighted summands can simply be added to get the sandwich's total.
     */
    private void createSandwichSumConstraint(CpModel model, BoardVariables boardVariables, IntVar[] stateVars, IntFunction<IntVar> getAtPosition) {

        BoolVar[] isInsideSandwich = createBooleanArrayForState(model, boardVariables, stateVars, FIELD_INSIDE_SANDWICH);
        // step one: create weighted summands
        IntVar[] weightedSummands = new IntVar[stateVars.length];
        for (int i = 0; i < stateVars.length; i++) {
            String name = boardVariables.generateUniqueHelperVarName(String.format("sandwich-weighted-summand-%s-%d-i%d", direction, rowOrColumnIdx, i));
            // since the weight will be either 1 or 0, the bounds are the same as the underlying cell's ones
            weightedSummands[i] = model.newIntVar(0, boardVariables.getMaxValue(), name);

            IntVar underlyingValue = getAtPosition.apply(i);
            // weightedSummands[i] = (stateVars[i] == 2) * underlyingValue[i]
            model.addMultiplicationEquality(weightedSummands[i], isInsideSandwich[i], underlyingValue);
        }

        // step two: add sum equality
        model.addEquality(LinearExpr.sum(weightedSummands), value);
    }

    /**
     * Map the stateVars to a boolean array b such that b[i] = (stateVars[i] == stateValue)
     */
    private BoolVar[] createBooleanArrayForState(CpModel model, BoardVariables boardVariables, IntVar[] stateVars, long stateValue) {
        BoolVar[] booleanArrayForState = new BoolVar[stateVars.length];
        for (int i = 0; i < stateVars.length; i++) {
            String name = boardVariables.generateUniqueHelperVarName(String.format("sandwich-bool-%s-%d-state%d-i%d", direction, rowOrColumnIdx, stateValue, i));
            booleanArrayForState[i] = model.newBoolVar(name);
            // (stateVars[i] == stateValue) == booleanArrayForState
            model.addEquality(stateVars[i], stateValue).onlyEnforceIf(booleanArrayForState[i]);
            model.addDifferent(stateVars[i], stateValue).onlyEnforceIf(booleanArrayForState[i].not());
        }
        return booleanArrayForState;
    }

    /**
     * Create valid state transition variables for this line.
     * <p>
     * give each cell along the line (row or column) a state
     * <ul>
     *     <li>0: FIELD_OUTSIDE_SANDWICH</li>
     *     <li>1: FIELD_IS_SANDWICH_CRUST</li>
     *     <li>2: FIELD_INSIDE_SANDWICH</li>
     * </ul>
     */
    private IntVar[] createValidStateVars(CpModel model, BoardVariables boardVariables, IntFunction<IntVar> getAtPosition) {
        int lineSize = switch (direction) {
            case ROW -> boardVariables.getColumnCount();
            case COLUMN -> boardVariables.getRowCount();
        };

        IntVar[] stateVars = new IntVar[lineSize];
        for (int i = 0; i < lineSize; i++) {
            String name = boardVariables.generateUniqueHelperVarName(String.format("sandwich-state-%s-%d-i%d", direction, rowOrColumnIdx, i));
            stateVars[i] = model.newIntVar(0, 2, name);
        }
        createStateTransitionAutomaton(model, stateVars);
        createState1IsCrustConstraint(model, boardVariables, stateVars, getAtPosition);

        return stateVars;
    }

    /**
     * Make sure that a field having state 1 is equivalent to that field being either crust1 or crust2
     */
    private void createState1IsCrustConstraint(CpModel model, BoardVariables boardVariables, IntVar[] stateVars, IntFunction<IntVar> getAtPosition) {
        BoolVar[] isSandwichCrust = createBooleanArrayForState(model, boardVariables, stateVars, FIELD_IS_SANDWICH_CRUST);
        // underlyingValue in {1, 9} <=> state == 1
        for (int i = 0; i < stateVars.length; i++) {
            IntVar underlyingValue = getAtPosition.apply(i);
            String nameIsCrust1 = boardVariables.generateUniqueHelperVarName(String.format("sandwich-isCrust1-%s-%d-i%d-i%d", direction, rowOrColumnIdx, i - 1, i));
            BoolVar isCrust1 = model.newBoolVar(nameIsCrust1);
            model.addEquality(underlyingValue, crust1).onlyEnforceIf(isCrust1);
            model.addDifferent(underlyingValue, crust1).onlyEnforceIf(isCrust1.not());

            String nameIsCrust2 = boardVariables.generateUniqueHelperVarName(String.format("sandwich-isCrust2-%s-%d-i%d-i%d", direction, rowOrColumnIdx, i - 1, i));
            BoolVar isCrust2 = model.newBoolVar(nameIsCrust2);
            model.addEquality(underlyingValue, crust2).onlyEnforceIf(isCrust2);
            model.addDifferent(underlyingValue, crust2).onlyEnforceIf(isCrust2.not());

            model.addBoolOr(List.of(isCrust1, isCrust2)).onlyEnforceIf(isSandwichCrust[i]);
            model.addEquality(LinearExpr.newBuilder().add(isCrust1).add(isCrust2).build(), 0).onlyEnforceIf(isSandwichCrust[i].not());

        }
    }

    /**
     * Create a state automaton constraint to validate the transition between field states.
     * It will enforce that the stateVars match the RegEx ^0*12*10*$
     * <p>
     * To do that, it uses the following internal states:
     * <ul>
     *     <li>0: before the first crust</li>
     *     <li>1: first crust</li>
     *     <li>2: inside the crust</li>
     *     <li>3: second crust</li>
     *     <li>4: after the second crust</li>
     * </ul>
     * These states are not needed outside this validation method, so they map to the global stateVars FIELD_IS_SANDWICH_CRUST, FIELD_INSIDE_SANDWICH and FIELD_OUTSIDE_SANDWICH
     * The "mapping" works by using the globalState as transitions for the internalState.
     *
     * <p>
     * Example for a sandwich row of length 9 with crust1 = 1 and crust2 = 9
     * <pre>
     * cell values:   |  4 5 9 2 7 3 1 6 8
     * ---------------+-------------------
     * internalState: | 0 0 0 1 2 2 2 3 4 4
     * globalState:   |  0 0 1 2 2 2 1 0 0
     * </pre>
     * The crust may also touch the edge:
     * <pre>
     * cell values:   |  9 5 4 2 7 3 8 6 1
     * ---------------+-------------------
     * internalState: | 0 1 2 2 2 2 2 2 2 3
     * globalState:   |  1 2 2 2 2 2 2 2 1
     * </pre>
     * ... or span a 0-cell sandwich:
     * <pre>
     * cell values:   |  5 4 2 9 1 7 3 8 6
     * ---------------+-------------------
     * internalState: | 0 0 0 0 1 3 4 4 4 4
     * globalState:   |  0 0 0 1 1 0 0 0 0
     * </pre>
     */
    private void createStateTransitionAutomaton(CpModel model, IntVar[] stateVars) {

        // state definitions
        final long BEFORE_FIRST_CRUST_STATE = 0;
        final long FIRST_CRUST_STATE = 1;
        final long INSIDE_CRUST_STATE = 2;
        final long SECOND_CRUST_STATE = 3;
        final long AFTER_SECOND_CRUST_STATE = 4;

        AutomatonConstraint automatonConstraint = model.addAutomaton(stateVars, BEFORE_FIRST_CRUST_STATE, new long[]{SECOND_CRUST_STATE, AFTER_SECOND_CRUST_STATE});

        addTransition(automatonConstraint, BEFORE_FIRST_CRUST_STATE, BEFORE_FIRST_CRUST_STATE, FIELD_OUTSIDE_SANDWICH);
        addTransition(automatonConstraint, BEFORE_FIRST_CRUST_STATE, FIRST_CRUST_STATE, FIELD_IS_SANDWICH_CRUST);
        addTransition(automatonConstraint, FIRST_CRUST_STATE, INSIDE_CRUST_STATE, FIELD_INSIDE_SANDWICH);
        addTransition(automatonConstraint, FIRST_CRUST_STATE, SECOND_CRUST_STATE, FIELD_IS_SANDWICH_CRUST);
        addTransition(automatonConstraint, INSIDE_CRUST_STATE, INSIDE_CRUST_STATE, FIELD_INSIDE_SANDWICH);
        addTransition(automatonConstraint, INSIDE_CRUST_STATE, SECOND_CRUST_STATE, FIELD_IS_SANDWICH_CRUST);
        addTransition(automatonConstraint, SECOND_CRUST_STATE, AFTER_SECOND_CRUST_STATE, FIELD_OUTSIDE_SANDWICH);
        addTransition(automatonConstraint, AFTER_SECOND_CRUST_STATE, AFTER_SECOND_CRUST_STATE, FIELD_OUTSIDE_SANDWICH);
    }


    private static void addTransition(AutomatonConstraint constraint, long tail, long head, long label) {
        constraint.getBuilder().getAutomatonBuilder().addTransitionTail(tail).addTransitionLabel(label).addTransitionHead(head);
    }

}
