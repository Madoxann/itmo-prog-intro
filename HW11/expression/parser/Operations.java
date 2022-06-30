package expression.parser;

import expression.common.Reason;

import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class Operations {
    public static final Operation X = variable("x", 0);

    public static final Operation Y = variable("y", 1);
    public static final Operation Z = variable("z", 2);

    public static final Operation NEGATE = unary("-", a -> -a);
    @SuppressWarnings("Convert2MethodRef")
    public static final Operation ADD       = binary("+",  200, (a, b) -> a + b);
    public static final Operation SUBTRACT  = binary("-", -200, (a, b) -> a - b);
    public static final Operation MULTIPLY  = binary("*",  301, (a, b) -> a * b);
    public static final Operation DIVIDE    = binary("/", -300, (a, b) -> b == 0 ? Reason.DBZ.error() : a / b);

    @FunctionalInterface
    private interface Operation extends Consumer<ParserTester> {}

    private static Operation variable(final String name, final int index) {
        return tests -> tests.variable(name, index);
    }

    private static Operation unary(final String name, final LongUnaryOperator op) {
        return tests -> tests.unary(name, op);
    }

    private static Operation binary(final String name, final int priority, final LongBinaryOperator op) {
        return tests -> tests.binary(name, priority, op);
    }
}
