package expression.exceptions;

import java.util.Objects;
import expression.UniteExpression;

public abstract class BinaryOperation implements UniteExpression {
    protected final UniteExpression rightTerm;

    protected final UniteExpression leftTerm;

    public BinaryOperation(UniteExpression l, UniteExpression r) {
        rightTerm = r;
        leftTerm = l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperation that = (BinaryOperation) o;
        return Objects.equals(rightTerm, that.rightTerm) && Objects.equals(leftTerm, that.leftTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rightTerm, leftTerm, getClass());
    }
}
