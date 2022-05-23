package expression.exceptions;

public class ArithmeticException extends RuntimeException {
    public ArithmeticException(String message) {
        super(message);
    }

    public ArithmeticException() {
        super("An arithmetic exception has occurred");
    }
}
