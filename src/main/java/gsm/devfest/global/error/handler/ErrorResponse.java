package gsm.devfest.global.error.handler;

public class ErrorResponse {

    private final String errorMessage;
    private final int status;

    public ErrorResponse(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }
}
