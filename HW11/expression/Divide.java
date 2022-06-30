package expression;

public class Divide extends BinaryOperation {
    public Divide(UniteExpression lTerm, UniteExpression rTerm) {
        super(lTerm, rTerm);
    }

    @Override
    public int evaluate(int x) {
        return leftTerm.evaluate(x, x, x) / rightTerm.evaluate(x, x, x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftTerm.evaluate(x, y, z) / rightTerm.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + leftTerm + " / " + rightTerm + ")";
    }
}
