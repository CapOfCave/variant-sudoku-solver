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
- [x] Palindrome Sudoku
- [x] Disjoint Groups Sudoku
- [x] Nonconsecutive Sudoku

### Coming Soon (-ish):

- [ ] Difference/Ratio Sudoku
  - [ ] Difference Sudoku
  - [ ] Ratio Sudoku 
  - [ ] Difference/Ratio Sudoku with Negative Constraint
- [ ] Clone Sudoku
- [ ] Arrow Sudoku
- [ ] Between Line Sudoku
- [ ] Minimum/Maximum Sudoku
- [ ] XV-Sudoku
  - [ ] X-Sudoku
  - [ ] V-Sudoku
  - [ ] XV-Sudoku with Negative Constraint
- [ ] Quadruple Sudoku

## How does it work?

sudoku-solver uses Google OR Tools under the hood, more precisely [CP-SAT](https://developers.google.com/optimization/cp/cp_solver), Google's constraint programming solver.

Constraint Programming is defined as follows:

> A set of techniques for finding feasible solutions to a problem expressed as constraints (e.g., a room can't be used for two events simultaneously, or the distance to the crops must be less than the length of the hose, or no more than five TV shows can be recorded at once).
> 
> -- <cite>[Google Developers](https://developers.google.com/optimization/introduction/overview)</cite>

As such, this library translates the sudoku ruleset into a set of constraints that need to be fulfilled in order to produce a valid solution. The underlying _Constraint Programming Solver_ then uses these constraints to produce all solutions that satisfy the ruleset. 

This means that the execution is **highly optimized**, while the actual implementation of sudoku rulesets is usually lightweight, which makes the solver **easily extensible and maintainable**.