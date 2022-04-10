# Variant Sudoku Solver
[![Maven CI](https://github.com/CapOfCave/sudoku-solver/actions/workflows/ci.yml/badge.svg)](https://github.com/CapOfCave/sudoku-solver/actions/workflows/ci.yml)

A fast and extensible variant sudoku solver java library made with [Google OR Tools](https://developers.google.com/optimization).

## Features

This library aims toward being able to solve all somewhat popular Sudoku variants.

### List of supported Sudoku Variants

- [x] Normal Sudoku (duh)
- [x] Sub Doku and Super Doku, i. e. sudoku with grids of any size 
- [x] Sudoku X aka Diagonal Sudoku, with one or two diagonals
- [x] Anti-Chess Sudoku
  - [x] Anti-Knight Sudoku
  - [x] Anti-King Sudoku
- [x] Killer Sudoku (and, consequently, Windoku and Extra Region Sudoku)
- [x] Little Killer Sudoku
- [x] Sandwich Sudoku
- [x] Thermo Sudoku
- [x] Odd-/Even-Sudoku
- [x] Arrow Sudoku
- [x] Palindrome Sudoku
- [x] Disjoint Groups Sudoku
- [x] Nonconsecutive Sudoku
- [x] Clone Sudoku

### Coming Soon (-ish):

- [ ] Difference/Ratio Sudoku
  - [ ] Difference Sudoku
  - [ ] Ratio Sudoku 
  - [ ] Difference/Ratio Sudoku with Negative Constraint
- [ ] Between Line Sudoku
- [ ] Minimum/Maximum Sudoku
- [ ] XV-Sudoku
  - [ ] X-Sudoku
  - [ ] V-Sudoku
  - [ ] XV-Sudoku with Negative Constraint
- [ ] Quadruple Sudoku
- [ ] Renban Lines/Groups
- [ ] German Whisper Lines (incl. variations like Dutch Whispers)


## Usage

This library offers a fluent API for defining a sudoku ruleset. It offers shortcuts for common constraint configurations while still allowing fine-granular modification for edge-case sudokus.

The constraints can be configured as follows (Shown with "The Miracle" by Mitchell Lee):

```java
SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
    .withConstraint(new NonconsecutiveConstraint())
    .withConstraint(new KingSudokuConstraint())
    .withConstraint(new KnightSudokuConstraint())
    .withGivenDigit(5, 3, 1) // digit '1' at r5c3
    .withGivenDigit(6, 7, 2) // digit '2' at r6c7
    .solve();
```

For Sudokus with more given digits, the board can be provided with a useful shortcut (Shown with "Disjoint Groups Sudoku" by Rajesh Kumar):

```java
int[][] board = {
    {1, 0, 0, 0, 7, 0, 3, 0, 4},
    {0, 5, 0, 0, 6, 0, 0, 9, 0},
    {0, 0, 4, 0, 5, 0, 7, 0, 0},
    {0, 0, 0, 8, 0, 6, 0, 0, 0},
    {6, 1, 8, 0, 0, 0, 4, 2, 9},
    {0, 0, 0, 4, 0, 2, 0, 0, 0},
    {0, 0, 3, 0, 2, 0, 9, 0, 0},
    {0, 4, 0, 0, 8, 0, 0, 7, 0},
    {9, 0, 6, 0, 4, 0, 0, 0, 3},
};
SudokuSolveSolution solve = SudokuSolver.normalSudokuRulesApply()
    .withGivenDigitsFromIntArray(board)
    .withConstraint(new DisjointGroupsConstraints())
    .solve();
```

Examples for all implemented constraints can be found in [the test cases](src/test/java/me/kecker/sudokusolver/constraints/variants).


## How does it work?

Variant Sudoku Solver uses Google OR Tools under the hood, more precisely [CP-SAT](https://developers.google.com/optimization/cp/cp_solver), Google's constraint programming solver.

Constraint Programming (CP) is defined as follows:

> A set of techniques for finding feasible solutions to a problem expressed as constraints (e.g., a room can't be used for two events simultaneously, or the distance to the crops must be less than the length of the hose, or no more than five TV shows can be recorded at once).
> 
> -- <cite>[Google Developers](https://developers.google.com/optimization/introduction/overview)</cite>

As such, this library translates the sudoku ruleset into a set of constraints that need to be fulfilled in order to produce a valid solution. The underlying _Constraint Programming Solver_ then uses these constraints to produce all solutions that satisfy the ruleset. 

This means that the execution is **highly optimized**, while the actual implementation of sudoku rulesets is usually lightweight[^1], which makes the solver **easily extensible and maintainable**.

[^1]: Some Sudoku rules are more difficult to model as a set of constraints than others, as seen in [the Sandwich Sudoku Implementation](src/main/java/me/kecker/sudokusolver/constraints/variants/SandwichConstraint.java).