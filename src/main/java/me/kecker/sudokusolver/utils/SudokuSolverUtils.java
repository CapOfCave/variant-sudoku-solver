package me.kecker.sudokusolver.utils;

import me.kecker.sudokusolver.Board;

public class SudokuSolverUtils {

    public static boolean isSquare(int value) {
        int squared = (int) Math.sqrt(value);
        return value == squared * squared;
    }

    public static void printBoard(
            ValueSupplier valuesByPosition,
            Board board) {
        int boxSizeX = board.getBoxSizeX();
        int boxSizeY = board.getBoxSizeY();
        int boxCountX = board.getBoxCountX();
        int boxCountY = board.getBoxCountY();

        for (int boxY = 0; boxY < boxCountY; boxY++) {
            printRowSeparator(boxSizeX, boxCountX);

            for (int rowInBoxIdx = 0; rowInBoxIdx < boxSizeY; rowInBoxIdx++) {
                for (int boxX = 0; boxX < boxCountX; boxX++) {
                    System.out.print("| ");

                    for (int columnInBoxIdx = 0; columnInBoxIdx < boxSizeX; columnInBoxIdx++) {
                        System.out.printf("%s ", valuesByPosition.get(boxY * boxSizeY + rowInBoxIdx, boxX * boxSizeX + columnInBoxIdx));
                    }

                }
                System.out.println("|");
            }

        }
        printRowSeparator(boxSizeX, boxCountX);
        System.out.println();

    }

    public static void printRowSeparator(int boxSizeX, int boxCountX) {
        for (int i = 0; i < boxCountX; i++) {
            System.out.print("+");
            System.out.print("-".repeat(boxSizeX * 2 + 1));
        }
        System.out.println("+");

    }

    public interface ValueSupplier {
        String get(int rowIdx, int columnIdx);
    }
}
