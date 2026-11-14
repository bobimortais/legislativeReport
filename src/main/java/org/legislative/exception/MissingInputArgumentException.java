package org.legislative.exception;

public class MissingInputArgumentException extends RuntimeException{
    public MissingInputArgumentException() {
        super("There are missing arguments for the execution. It expects four input values. Please refer to README.md file.");
    }
}
