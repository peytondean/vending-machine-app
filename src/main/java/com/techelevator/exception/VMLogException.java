package com.techelevator.exception;

/**
 * Handles exceptions that involves using VMLog and adds an enum property to clarify why the exception is being thrown
 */
public class VMLogException extends RuntimeException implements ErrorCodeInterface {

    private final ErrorCode errorCode;

    public VMLogException(String runTimeMessage, Throwable cause, ErrorCode code) {
        super(runTimeMessage,cause);
        this.errorCode = code;
    }

    public VMLogException(ErrorCode code) {
        super();
        this.errorCode = code;
    }

    public VMLogException(String runTimeMessage, ErrorCode code) {
        super(runTimeMessage);
        this.errorCode = code;
    }

    public VMLogException(Throwable cause, ErrorCode code) {
        super(cause);
        this.errorCode = code;
    }

    public final ErrorCode getErrorCode() {return this.errorCode;}

    @Override
    public String generateErrorMessage() {
        return errorCode.toString();
    }
}
