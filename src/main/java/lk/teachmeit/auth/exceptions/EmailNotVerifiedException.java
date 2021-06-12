package lk.teachmeit.auth.exceptions;

public class EmailNotVerifiedException extends RuntimeException{
    public EmailNotVerifiedException(String exception){
        super(exception);
    }
}
