package expression.exceptions;

public class WhitespaceException extends SyntaxException {
    WhitespaceException(String sourceErr) {
        super("Missing whitespace on " + sourceErr);
    }
}
