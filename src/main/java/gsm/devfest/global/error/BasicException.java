package gsm.devfest.global.error;

import org.springframework.http.HttpStatus;

public class BasicException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus status;

    public BasicException(String errorMessage, HttpStatus status) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
