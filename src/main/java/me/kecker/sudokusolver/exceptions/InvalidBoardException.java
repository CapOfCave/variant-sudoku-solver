package me.kecker.sudokusolver.exceptions;

public class InvalidBoardException extends RuntimeException{

    public InvalidBoardException(String message) {
        super(message);
    }
}
