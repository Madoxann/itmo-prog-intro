package expression.exceptions;

import expression.UniteExpression;

public class CheckedNegate extends UnaryOperation{
    public CheckedNegate(UniteExpression exp) {
        super(exp);
    }

    @Override
    public int evaluate(int x) {
        return -1 * expression.evaluate(x,x,x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int calc = expression.evaluate(x,y,z);
        if (calc == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -calc;
    }

    @Override
    public String toString() {
        return "-(" + expression + ")";
    }
}
