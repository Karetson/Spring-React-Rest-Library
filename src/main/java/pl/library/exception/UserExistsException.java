package pl.library.exception;

public class UserExistsException extends Exception{
    public UserExistsException(String s) {
        super(s);
    }
}
