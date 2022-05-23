package expression.exceptions;

public class MissingOperatorException extends MissingException {
    MissingOperatorException() {
        super("No binary operator found");
    }
}
