package lk.teachmeit.auth.exceptions;

public class EmailAlreadyException extends RuntimeException{
    public EmailAlreadyException(String exception){
        super(exception);
    }
}
