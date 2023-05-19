package src.Exceptions;
public class IncorrectRegisterValue extends Exception {
    public IncorrectRegisterValue(String message) {
        super(message);
    }
    public IncorrectRegisterValue() {
        super("Incorrect register value");
    }


}