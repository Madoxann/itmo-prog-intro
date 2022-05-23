package expression.exceptions;

import expression.UniteExpression;

public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(UniteExpression lTerm, UniteExpression rTerm) {
        super(lTerm, rTerm);
    }

    @Override
    public int evaluate(int x) {
        return leftTerm.evaluate(x, x, x) + rightTerm.evaluate(x, x, x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int leftCalc = leftTerm.evaluate(x, y ,z);
        int rightCalc = rightTerm.evaluate(x, y, z);
        if ((leftCalc > 0 && Integer.MAX_VALUE - leftCalc < rightCalc) ||
                (leftCalc < 0 && Integer.MIN_VALUE - leftCalc > rightCalc)) {
            throw new OverflowException();
        }
        return leftCalc + rightCalc;
    }

    @Override
    public String toString() {
        return "(" + leftTerm + " + " + rightTerm + ")";
    }
}
