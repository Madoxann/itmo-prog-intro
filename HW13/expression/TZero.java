package expression;

public class TZero extends UnaryOperation {
    public TZero(UniteExpression exp) {
        super(exp);
    }

    private int calculate(int num) {
        if (num == 0) {
            return 32;
        }
        int cnt = 0;
        while ((num & 1) != 1) {
            num >>= 1;
            cnt++;
        }
        return cnt;
    }

    @Override
    public int evaluate(int x) {
        return calculate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(expression.evaluate(x,y,z));
    }

    @Override
    public String toString() {
        return "t0(" + expression + ")";
    }
}
