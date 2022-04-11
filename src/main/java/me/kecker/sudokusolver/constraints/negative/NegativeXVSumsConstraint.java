package me.kecker.sudokusolver.constraints.negative;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singleton;

public class NegativeXVSumsConstraint extends NegativeAdjacentPairSumConstraint {


    public NegativeXVSumsConstraint() {
        this(NegativeXVSumsConstraintOption.NEGATIVE_X_AND_V);
    }

    public NegativeXVSumsConstraint(NegativeXVSumsConstraintOption negativeXVSumsConstraintOption) {
        super(negativeXVSumsConstraintOption.forbiddenValues);
    }

    public enum NegativeXVSumsConstraintOption {
        NEGATIVE_X_ONLY(singleton(10)),
        NEGATIVE_V_ONLY(singleton(5)),
        NEGATIVE_X_AND_V(List.of(10, 5));

        private final Collection<Integer> forbiddenValues;

        NegativeXVSumsConstraintOption(Collection<Integer> forbiddenValues) {
            this.forbiddenValues = forbiddenValues;
        }
    }
}
