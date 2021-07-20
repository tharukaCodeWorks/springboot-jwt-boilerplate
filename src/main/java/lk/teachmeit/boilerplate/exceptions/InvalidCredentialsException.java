package lk.teachmeit.boilerplate.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String exception){
        super(exception);
    }
}
