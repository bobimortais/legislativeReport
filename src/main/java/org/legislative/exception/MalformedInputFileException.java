package org.legislative.exception;

public class MalformedInputFileException extends RuntimeException{
    public MalformedInputFileException() {
        super("There is an issue with an input file. Please refer to README.md file.");
    }
}
