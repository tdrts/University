package Exceptions;

public class ServiceException extends Exception{
    public ServiceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Service Exception: " + super.getMessage();
    }
}
