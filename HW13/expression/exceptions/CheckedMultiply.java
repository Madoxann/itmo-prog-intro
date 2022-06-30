package expression.exceptions;

import expression.UniteExpression;

public class CheckedMultiply extends BinaryOperation{
    public CheckedMultiply(UniteExpression lTerm, UniteExpression rTerm) {
        super(lTerm, rTerm);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int leftCalc = leftTerm.evaluate(x, y ,z);
        int rightCalc = rightTerm.evaluate(x, y, z);
        // conditions MUST be in this order, otherwise speculative execution will fail tests
        if (rightCalc > 0 && leftCalc > 0 && Integer.MAX_VALUE / leftCalc < rightCalc) throw new OverflowException();
        if (rightCalc < 0 && leftCalc < 0 && Integer.MAX_VALUE / leftCalc > rightCalc) throw new OverflowException();
        if (rightCalc > 0 && leftCalc < 0 && Integer.MIN_VALUE / rightCalc > leftCalc) throw new OverflowException();
        if (rightCalc < 0 && leftCalc > 0 && Integer.MIN_VALUE / leftCalc > rightCalc) throw new OverflowException();

        return leftCalc * rightCalc;
    }

    @Override
    public int evaluate(int x) {
        return leftTerm.evaluate(x, x, x) * rightTerm.evaluate(x, x, x);
    }

    @Override
    public String toString() {
        return "(" + leftTerm + " * " + rightTerm + ")";
    }
}
