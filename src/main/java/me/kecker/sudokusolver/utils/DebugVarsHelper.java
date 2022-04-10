package me.kecker.sudokusolver.utils;

import com.google.ortools.sat.IntVar;

import java.util.ArrayList;
import java.util.List;

public class DebugVarsHelper {
    public static List<IntVar> debugVars = new ArrayList<>();

    public static <T extends IntVar> T db(T intVar) {
        debugVars.add(intVar);
        return intVar;
    }

    public static <T extends IntVar> T xdb(T intVar) {
        return intVar;
    }

    public static List<IntVar> get() {
        return debugVars;
    }
}
