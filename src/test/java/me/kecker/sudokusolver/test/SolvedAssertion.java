package me.kecker.sudokusolver.test;

import me.kecker.sudokusolver.result.SingleSolution;
import me.kecker.sudokusolver.result.Solution;
import org.assertj.core.api.SoftAssertions;

public class SolvedAssertion {

    @Deprecated
    public static void assertSolved(Solution solve, int[][] solution) {
        SoftAssertions softAssertions = new SoftAssertions();
        for (int rowIdx = 0; rowIdx < solution.length; rowIdx++){
            for (int columnIdx = 0; columnIdx < solution[rowIdx].length; columnIdx++){
                softAssertions.assertThat(solve.value(rowIdx + 1, columnIdx + 1))
                        .as(String.format("r%dc%d", rowIdx + 1, columnIdx + 1))
                        .isEqualTo(solution[rowIdx][columnIdx]);
            }
        }
        softAssertions.assertAll();
    }

    public static void assertSolved(SingleSolution solve, int[][] solution) {
        SoftAssertions softAssertions = new SoftAssertions();
        for (int rowIdx = 0; rowIdx < solution.length; rowIdx++){
            for (int columnIdx = 0; columnIdx < solution[rowIdx].length; columnIdx++){
                softAssertions.assertThat(solve.value(rowIdx + 1, columnIdx + 1))
                        .as(String.format("r%dc%d", rowIdx + 1, columnIdx + 1))
                        .isEqualTo(solution[rowIdx][columnIdx]);
            }
        }
        softAssertions.assertAll();
    }
}
