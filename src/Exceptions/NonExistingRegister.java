package src.Exceptions;

public class NonExistingRegister extends Exception{
    public NonExistingRegister(String message) {
        super(message);
    }
    public NonExistingRegister() {
        super("Non existing register");
    }
}
