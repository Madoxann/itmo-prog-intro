package expression.exceptions;

public class MissingUnaryArgumentException extends MissingException {
    MissingUnaryArgumentException() {
        super("Missing argument of unary operator");
    }
}
