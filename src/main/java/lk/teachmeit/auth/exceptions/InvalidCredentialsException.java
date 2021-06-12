package lk.teachmeit.auth.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String exception){
        super(exception);
    }
}
