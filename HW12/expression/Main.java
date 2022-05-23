package expression;

import expression.parser.ExpressionParser;
import expression.parser.StringSource;

public class Main {
    public static void main(String[] args) {
        ExpressionParser expr = new ExpressionParser();
        System.out.println(expr.parse("\n" +
                "\n" +
                "((\t \u2029(y\n" +
                "\n" +
                "+ x)\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "--2147483648)+\u2029\n" +
                "               \n" +
                "\t (x\n" +
                "           + x)\t\n" +
                "                )\u2029\n" +
                "                  \n"));
        System.out.println(expr.parse("1 + 3 * 5"));
        System.out.println( expr.parse("1 + 4/5 + 7"));
        System.out.println(expr.parse("x+y+x"));
    }
}
