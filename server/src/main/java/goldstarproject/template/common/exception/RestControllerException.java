package goldstarproject.template.common.exception;

import lombok.Getter;

public class RestControllerException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;


    public RestControllerException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;

    }
}

