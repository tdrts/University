package Exceptions;

public class StmtException extends Exception{
    public StmtException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Adt Exception: " + super.getMessage();
    }
}
