package src.Exceptions;

public class AddressOutOfBounds extends Exception {
    public AddressOutOfBounds(String message) {
        super(message);
    }


    public AddressOutOfBounds() {
        super("Address out of bounds");
    }
}