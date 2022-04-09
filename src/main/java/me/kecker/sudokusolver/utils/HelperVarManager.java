package me.kecker.sudokusolver.utils;

public class HelperVarManager {

    private static long helperVarCount = 0;

    public static String generateUniqueHelperVarName(String prefix) {

        String name = String.format("%s-helper[%d]", prefix, helperVarCount);
        helperVarCount++;
        return name;
    }
}
