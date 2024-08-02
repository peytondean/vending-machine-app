package com.techelevator.exception;

/**
 * A custom exception to handle inheritance and creation issues from extending from
 * the abstract class.
 */
public class SnackException extends RuntimeException implements ErrorCodeInterface{

    private final ErrorCode errorCode;

    public SnackException(String runTimeMessage, Throwable cause, ErrorCode code) {
        super(runTimeMessage,cause);
        this.errorCode = code;
    }

    public SnackException(ErrorCode code) {
        super();
        this.errorCode = code;
    }

    public SnackException(String runTimeMessage, ErrorCode code) {
        super(runTimeMessage);
        this.errorCode = code;
    }

    public SnackException(Throwable cause, ErrorCode code) {
        super(cause);
        this.errorCode = code;
    }

    @Override
    public String generateErrorMessage() {
        return errorCode.toString();
    }
}