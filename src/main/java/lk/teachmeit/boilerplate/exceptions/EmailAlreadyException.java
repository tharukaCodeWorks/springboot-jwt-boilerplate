package lk.teachmeit.boilerplate.exceptions;

public class EmailAlreadyException extends RuntimeException{
    public EmailAlreadyException(String exception){
        super(exception);
    }
}
