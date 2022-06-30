package expression.exceptions;

import expression.UniteExpression;

public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(UniteExpression lTerm, UniteExpression rTerm) {
        super(lTerm, rTerm);
    }

    @Override
    public int evaluate(int x) {
        return leftTerm.evaluate(x, x, x) / rightTerm.evaluate(x, x, x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int leftCalc = leftTerm.evaluate(x, y ,z);
        int rightCalc = rightTerm.evaluate(x, y, z);
        if (leftCalc == Integer.MIN_VALUE && rightCalc == -1) {
            throw new OverflowException();
        }
        return leftCalc / rightCalc;
    }

    @Override
    public String toString() {
        return "(" + leftTerm + " / " + rightTerm + ")";
    }
}

