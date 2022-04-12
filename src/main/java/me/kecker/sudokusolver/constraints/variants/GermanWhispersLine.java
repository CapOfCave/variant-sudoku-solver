package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.WhispersLine;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;

public class GermanWhispersLine extends WhispersLine {
    public GermanWhispersLine(List<Position> line) {
        super(5, line);
    }
}
