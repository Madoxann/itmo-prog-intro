package expression.exceptions;

public class InvalidNumberException extends ArithmeticException {
    InvalidNumberException (String sourceErr) {
        super("Invalid number " + sourceErr);
    }
}
