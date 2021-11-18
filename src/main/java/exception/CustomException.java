package exception;

public class CustomException extends Exception{
    private String errorMessage;
    private int errorCode;

    public CustomException() {
        super();
    }

    public CustomException(String errorMessage, int code) {
        this.errorMessage = errorMessage;
        this.errorCode = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
