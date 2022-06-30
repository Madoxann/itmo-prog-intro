package expression.exceptions;

import expression.UniteExpression;
import expression.TripleExpression;

import java.util.Arrays;
import java.util.HashSet;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        return new TripleParser(new StringSource(expression)).parseExpression(BaseParser.END);
    }

    private static class TripleParser extends BaseParser {
        private boolean isMissingWS() {
            return !isWS() && !test('(');
        }

        protected TripleParser(CharSource source) {
            super(source);
            startingSymbols = new HashSet<>(Arrays.asList('x', 'y', 'z', '(', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', '-', '+', '*', '/', 'l', 't', 'm'));
        }

        private TripleExpression parseFirst() throws ParsingException {
            TripleExpression currExpr = null;
            skipWS();
            if (take('(')) {
                currExpr = parseParenthesis();
            }
            if (between('0', '9')) {
                if (currExpr != null) {
                    throw new MissingOperatorException();
                }
                currExpr = parseConst(0);
            }
            if (between('x', 'z')) {
                if (currExpr != null) {
                    throw new MissingOperatorException();
                }
                currExpr = parseVar(0);
            }
            if (currExpr == null) {
                if (take('-')) {
                    currExpr = parseMinus();
                }
                if (take('l')) {
                    expect('0');
                    if (isMissingWS()) {
                        throw new WhitespaceException(source.getErrorMsg());
                    }
                    currExpr = parseLTZero("l0");
                }
                if (take('t')) {
                    expect('0');
                    if (isMissingWS()) {
                        throw new WhitespaceException(source.getErrorMsg());
                    }
                    currExpr = parseLTZero("t0");
                }
            }

            return currExpr;
        }

        private TripleExpression parseSecond(TripleExpression currExpr) throws ParsingException {
            while (true) {
                skipWS();
                if (take('(')) {
                    currExpr = parseParenthesis();
                    continue;
                }
                if (currExpr == null) {
                    currExpr = parseFirst();
                    if (currExpr == null) return null;
                    continue;
                }
                if (take('*')) {
                    currExpr = parseASMD(currExpr, '*');
                    continue;
                }
                if (take('/')) {
                    currExpr = parseASMD(currExpr, '/');
                    continue;
                }
                return currExpr;
            }
        }

        private TripleExpression parseThird(TripleExpression currExpr) throws ParsingException {
            while (true) {
                skipWS();
                if (take('(')) {
                    currExpr = parseParenthesis();
                    continue;
                }
                if (currExpr == null) {
                    currExpr = parseFirst();
                    if (currExpr == null) return null;
                    continue;
                }
                if (take('+')) {
                    currExpr = parseASMD(currExpr, '+');
                    continue;
                }
                if (take('-')) {
                    currExpr = parseASMD(currExpr, '-');
                    continue;
                }
                if (take('m')) {
                    if (take('i')) {
                        expect('n');
                        currExpr = parseMinMax(currExpr, "min");
                    } else {
                        expect("ax");
                        currExpr = parseMinMax(currExpr, "max");
                    }
                    continue;
                }

                return currExpr;
            }
        }

        private TripleExpression parseParenthesis() throws ParsingException {
            TripleExpression ret;
            ret = parseExpression(')');
            if (ret == null) throw new MissingBracketExpressionException();
            return ret;
        }

        public TripleExpression parseExpression(final char end) throws ParsingException {
            skipWS();
            if (!isAcceptable() && !test(')')) {
                if (eof()) {
                    throw new MissingSecondArgumentException();
                }
                throw new UnexpectedSymbolException(source.getErrorMsg());
            }
            TripleExpression expression;
            expression = parseFirst();
            expression = parseSecond(expression);
            expression = parseThird(expression);
            expect(end);

            return expression;
        }

        private TripleExpression parseConst(int multiplier) {
            skipWS();
            final StringBuilder sb = new StringBuilder();
            takeInteger(sb);

            try {
                return new Const((Integer.parseInt(Math.pow(-1, multiplier) < 0 ? "-" + sb : sb.toString())));
            } catch (final NumberFormatException e) {
                throw new InvalidNumberException(sb.toString() + take());
            }
        }

        private TripleExpression parseVar(int multiplier) throws UnexpectedVariableException {
            skipWS();
            char varName = take();
            if (varName == 'x' || varName == 'y' || varName == 'z') {
                return Math.pow(-1, multiplier) < 0 ? new CheckedNegate(new Variable(String.valueOf(varName))) : new Variable(String.valueOf(varName));
            }
            throw new UnexpectedVariableException(source.getErrorMsg());
        }

        private TripleExpression parseMinus() throws ParsingException {
            skipWS();
            int multiplier = 1;
            if (take('-')) {
                return new CheckedNegate((UniteExpression) parseMinus());
            }
            if (between('0', '9')) {
                return parseConst(multiplier);
            }
            if (between('x', 'z')) {
                return parseVar(multiplier);
            }

            UniteExpression getExpression = (UniteExpression) parseFirst();
            if (getExpression == null) throw new MissingFirstArgumentException();

            return new CheckedNegate(getExpression);
        }

        private TripleExpression parseLTZero(String taken) throws ParsingException {
            skipWS();
            if (take('-')) {
                if (taken.equals("l0")) return new CheckedLZero((UniteExpression) parseMinus());
                return new CheckedTZero((UniteExpression) parseMinus());
            }

            UniteExpression getExpression = (UniteExpression) parseFirst();
            if (getExpression == null) throw new MissingFirstArgumentException();

            if (taken.equals("l0")) return new CheckedLZero(getExpression);
            return new CheckedTZero(getExpression);
        }

        private TripleExpression parseASMD(TripleExpression expression, char taken) throws ParsingException {
            if (expression == null) throw new MissingFirstArgumentException();
            skipWS();
            UniteExpression getExpression;
            if (taken == '+' || taken == '-') {
                getExpression = (UniteExpression) parseSecond(null);
            } else {
                getExpression = (UniteExpression) parseFirst();
            }
            if (getExpression == null) throw new MissingSecondArgumentException();
            switch (taken) {
                case '+':
                    return new CheckedAdd((UniteExpression) expression, getExpression);
                case '-':
                    return new CheckedSubtract((UniteExpression) expression, getExpression);
                case '*':
                    return new CheckedMultiply((UniteExpression) expression, getExpression);
                case '/':
                    return new CheckedDivide((UniteExpression) expression, getExpression);
            }
            throw new MissingOperatorException();
        }

        private TripleExpression parseMinMax(TripleExpression expression, String taken) throws ParsingException {
            if (expression == null) throw new MissingFirstArgumentException();
            if (isMissingWS() && !test('-')) {
                throw new WhitespaceException(source.getErrorMsg());
            }
            skipWS();
            UniteExpression getExpression = (UniteExpression) parseSecond(null);
            if (getExpression == null) throw new MissingSecondArgumentException();
            switch (taken) {
                case "min" :
                    return new CheckedMin((UniteExpression) expression, getExpression);
                case "max":
                    return new CheckedMax((UniteExpression) expression, getExpression);
            }
            throw new MissingOperatorException();
        }

        private void takeDigits(final StringBuilder sb) {
            skipWS();
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private void takeInteger(final StringBuilder sb) {
            skipWS();
            if (take('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                takeDigits(sb);
            } else {
                throw error("Invalid number");
            }
        }

    }
}
