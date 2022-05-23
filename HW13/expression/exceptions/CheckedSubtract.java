package expression.exceptions;

import java.math.BigInteger;
import expression.UniteExpression;

public class CheckedSubtract  extends BinaryOperation {
    public CheckedSubtract(UniteExpression lTerm, UniteExpression rTerm) {
        super(lTerm, rTerm);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int leftCalc = leftTerm.evaluate(x, y ,z);
        int rightCalc = rightTerm.evaluate(x, y, z);
        if ((new BigInteger(String.valueOf(leftCalc)).subtract(new BigInteger(String.valueOf(rightCalc)))).compareTo(new BigInteger(String.valueOf(leftCalc-rightCalc))) != 0) {
            throw new OverflowException();
        }
        return leftCalc - rightCalc;
    }

    @Override
    public int evaluate(int x) {
        return leftTerm.evaluate(x, x, x) - rightTerm.evaluate(x, x, x);
    }

    @Override
    public String toString() {
        return "(" + leftTerm + " - " + rightTerm + ")";
    }
}

