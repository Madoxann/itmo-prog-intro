package expression.exceptions;

public class UnexpectedVariableException extends SyntaxException {
    UnexpectedVariableException(String sourceErr) {
        super("Unexpected symbol found at " + sourceErr);
    }
}
