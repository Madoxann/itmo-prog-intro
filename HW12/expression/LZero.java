package expression;

public class LZero extends UnaryOperation {
    public LZero(UniteExpression exp) {
        super(exp);
    }

    private int calculate(int num) {
        int cnt = 0;
        String strVal = Integer.toBinaryString(num);
        if (strVal.equals("0")) {
            return 32;
        }
        return 32 - strVal.length();
    }

    @Override
    public int evaluate(int x) {
        return calculate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(expression.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "l0(" + expression + ")";
    }
}
