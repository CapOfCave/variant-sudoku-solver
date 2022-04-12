package me.kecker.sudokusolver.constraints.variants;

import me.kecker.sudokusolver.constraints.base.WhispersLineConstraint;
import me.kecker.sudokusolver.dtos.Position;

import java.util.List;

public class GermanWhispersLineConstraint extends WhispersLineConstraint {
    public GermanWhispersLineConstraint(List<Position> line) {
        super(5, line);
    }
}
