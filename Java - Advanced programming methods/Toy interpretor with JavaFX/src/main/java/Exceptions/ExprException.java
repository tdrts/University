package Exceptions;

public class ExprException extends Exception {
    public ExprException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Expression Exception: " + super.getMessage();
    }
}
