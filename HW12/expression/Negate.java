package expression;

public class Negate extends UnaryOperation{
    public Negate(UniteExpression exp) {
        super(exp);
    }

    @Override
    public int evaluate(int x) {
        return -1 * expression.evaluate(x,x,x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -1 * expression.evaluate(x,y,z);
    }

    @Override
    public String toString() {
        return "-(" + expression + ")";
    }
}