package expression;

import expression.exceptions.ExpressionParser;
import expression.exceptions.ParsingException;

public class Main {
    public static void main(String[] args) throws ParsingException {
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse("x / y / z").evaluate(0,1,2));
    }
}
