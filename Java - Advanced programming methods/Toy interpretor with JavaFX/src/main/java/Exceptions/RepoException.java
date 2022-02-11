package Exceptions;

public class RepoException extends Exception{
    public RepoException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Repo Exception: " + super.getMessage();
    }
}
