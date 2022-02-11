package Exceptions;

public class AdtException extends Exception{
   public AdtException(String message) {
      super(message);
   }

   @Override
   public String getMessage() {
      return "Adt Exception: " + super.getMessage();
   }
}
