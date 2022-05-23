package expression.exceptions;

public class UnexpectedSymbolException extends ParsingException {
    UnexpectedSymbolException(String sourceErr) {
        super("Unexpected symbol found at " + sourceErr);
    }
}
