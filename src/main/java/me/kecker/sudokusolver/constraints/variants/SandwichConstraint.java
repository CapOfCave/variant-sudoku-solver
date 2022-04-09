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

    public SandwichConstraint(SudokuDirection direction, int rowOrColumnIdx, int crust1, int crust2, int value) {
        this.direction = direction;
        this.rowOrColumnIdx = rowOrColumnIdx;
        this.crust1 = crust1;
        this.crust2 = crust2;
        this.value = value;

    }

    @Override
    public void apply(CpModel model, BoardVariables boardVariables) {

        int lineSize = switch (direction) {
            case ROW -> boardVariables.getColumnCount();
            case COLUMN -> boardVariables.getRowCount();
        };

        IntFunction<IntVar> getAtPosition = (int position) -> switch (direction) {
            case ROW -> boardVariables.get(rowOrColumnIdx, position);
            case COLUMN -> boardVariables.get(position, rowOrColumnIdx);
        };

        // give each cell along the line (row or column) a state
        // 0: outside of the sandwich clue
        // 1: limiting the sandwich clue (crust1 or crust2)
        // 2: inside of the sandwich clue

        IntVar[] stateVars = new IntVar[lineSize];
        for (int i = 0; i < lineSize; i++) {
            String name = boardVariables.generateUniqueHelperVarName(String.format("sandwich-state-%s-%d-i%d", direction, rowOrColumnIdx, i));
            stateVars[i] = model.newIntVar(0, 2, name);
        }

        // helper arrays which map the stateVar to booleanVars: is this var in this state?
        BoolVar[] isInsideSandwich = new BoolVar[lineSize];
        BoolVar[] isSandwichCrust = new BoolVar[lineSize];
        for (int i = 0; i < lineSize; i++) {
            String nameInside = boardVariables.generateUniqueHelperVarName(String.format("sandwich-inside-%s-%d-i%d", direction, rowOrColumnIdx, i));
            isInsideSandwich[i] = model.newBoolVar(nameInside);
            // (stateVars[i] == 2)  ==  isInsideSandWich
            model.addEquality(stateVars[i], 2).onlyEnforceIf(isInsideSandwich[i]);
            model.addDifferent(stateVars[i], 2).onlyEnforceIf(isInsideSandwich[i].not());

            String nameCrust = boardVariables.generateUniqueHelperVarName(String.format("sandwich-crust-%s-%d-i%d", direction, rowOrColumnIdx, i));
            isSandwichCrust[i] = model.newBoolVar(nameCrust);
            // (stateVars[i] == 1)  ==  isSandwichCrust
            model.addEquality(stateVars[i], 1).onlyEnforceIf(isSandwichCrust[i]);
            model.addDifferent(stateVars[i], 1).onlyEnforceIf(isSandwichCrust[i].not());

        }


//        // the state variables must NOT go from S0 to S2 without passing S1
//        // which means: there must be a 1 or 9 at the transition between S0 and S2
//        // which also means: the difference between two adjacent states must not be greater than 1;
//        for (int i = 1; i < lineSize; i++) {
//            IntVar first = stateVars[i - 1];
//            IntVar second = stateVars[i];
//
//            // assert abs(difference between first and second) <= 1
//            String nameDifference = boardVariables.generateUniqueHelperVarName(String.format("sandwich-different-%s-%d-i%d-i%d", direction, rowOrColumnIdx, i - 1, i));
//            IntVar difference = model.newIntVar(-boardVariables.getMaxValue(), boardVariables.getMaxValue(), nameDifference);
//            // difference = second - first <=> first + difference = second
//            model.addEquality(LinearExpr.newBuilder().add(first).add(difference).build(), second);
//
//            String nameAbsDifference = boardVariables.generateUniqueHelperVarName(String.format("sandwich-abs-different-%s-%d-i%d-i%d", direction, rowOrColumnIdx, i - 1, i));
//            IntVar absDifference = model.newIntVar(0, boardVariables.getMaxValue(), nameAbsDifference);
//            // absDifference = abs(difference)
//            model.addAbsEquality(absDifference, difference);
//
//            // absDifference <= 1
//            model.addLessOrEqual(absDifference, 1);
//        }
//
//        // the outside state variables must not have state 2
//        model.addDifferent(stateVars[0], 2);
//        model.addDifferent(stateVars[stateVars.length - 1], 2);

        /*
         state automaton to describe the transition between sandwich crust
         States:
           - 0: before the first crust
           - 1: first crust
           - 2: inside the crust
           - 3: second crust
           - 4: after the second crust

         Example for a sandwich row of length 9 with crust1 = 1 and crust2 = 9
          state:       | 0 0 1 2 2 2 3 4 4
          -------------+------------------
          cell values: | 4 5 9 2 7 3 1 6 8

         The crust may also touch the edge:
         state:       | 1 2 2 2 2 2 2 2 3
         -------------+------------------
         cell values: | 9 5 4 2 7 3 8 6 1

        ... or span a 0-cell sandwich:
        state:       | 0 0 0 1 3 4 4 4 4
        -------------+------------------
        cell values: | 5 4 2 9 1 7 3 8 6

        */
        // state definitions
        final long BEFORE_FIRST_CRUST_STATE = 0;
        final long FIRST_CRUST_STATE = 1;
        final long INSIDE_CRUST_STATE = 2;
        final long SECOND_CRUST_STATE = 3;
        final long AFTER_SECOND_CRUST_STATE = 4;

        AutomatonConstraint automatonConstraint = model.addAutomaton(stateVars, BEFORE_FIRST_CRUST_STATE, new long[]{SECOND_CRUST_STATE, AFTER_SECOND_CRUST_STATE});

        addTransition(automatonConstraint, BEFORE_FIRST_CRUST_STATE, BEFORE_FIRST_CRUST_STATE, 0);
        addTransition(automatonConstraint, BEFORE_FIRST_CRUST_STATE, FIRST_CRUST_STATE, 1);
        addTransition(automatonConstraint, FIRST_CRUST_STATE, INSIDE_CRUST_STATE, 2);
        addTransition(automatonConstraint, FIRST_CRUST_STATE, SECOND_CRUST_STATE, 1);
        addTransition(automatonConstraint, INSIDE_CRUST_STATE, INSIDE_CRUST_STATE, 2);
        addTransition(automatonConstraint, INSIDE_CRUST_STATE, SECOND_CRUST_STATE, 1);
        addTransition(automatonConstraint, SECOND_CRUST_STATE, AFTER_SECOND_CRUST_STATE, 0);
        addTransition(automatonConstraint, AFTER_SECOND_CRUST_STATE, AFTER_SECOND_CRUST_STATE, 0);


        // underlyingValue in {1, 9} <=> state == 1
        for (int i = 0; i < lineSize; i++) {
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


        // all numbers *inside* the 1 and the 9 sum to the target value

        // step one: create weighted summands
        IntVar[] weightedSummands = new IntVar[lineSize];
        for (int i = 0; i < lineSize; i++) {
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

    private static void addTransition(AutomatonConstraint constraint, long tail, long head, long label) {
        constraint.getBuilder().getAutomatonBuilder().addTransitionTail(tail).addTransitionLabel(label).addTransitionHead(head);
    }

}
