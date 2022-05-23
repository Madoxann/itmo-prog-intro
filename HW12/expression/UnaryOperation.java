package expression;

import java.util.Objects;

public abstract class UnaryOperation implements UniteExpression {
    protected final UniteExpression expression;

    public UnaryOperation(UniteExpression expr) {
        expression = expr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryOperation that = (UnaryOperation) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
