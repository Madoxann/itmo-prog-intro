package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    @Override
    public TripleExpression parse(String expression) {
        return new TripleParser(new StringSource(expression)).parseExpression();
    }

    private static class TripleParser extends BaseParser {
        protected TripleParser(CharSource source) {
            super(source);
        }

        TripleExpression parseFirst() {
            TripleExpression currExpr = null;
            skipWS();
            if (take('(')) {
                currExpr = parseExpression();
                take(')');
            }
            if (between('0', '9')) {
                currExpr = parseConst(0);
            }
            if (between('x', 'z')) {
                currExpr = parseVar(0);
            }
            if (currExpr == null) {
                if (take('-')) {
                    currExpr = parseMinus();
                }
                if (take('l')) {
                    if (take('0')) {
                        currExpr = parseLZero();
                    }
                }
                if (take('t')) {
                    if (take('0')) {
                        currExpr = parseTZero();
                    }
                }

            }
            return currExpr;
        }

        TripleExpression parseSecond(TripleExpression currExpr) {
            while (true) {
                skipWS();
                if (take('(')) {
                    currExpr = parseExpression();
                    take(')');
                    continue;
                }
                if (currExpr == null) {
                    currExpr = parseFirst();
                    continue;
                }
                if (take('*')) {
                    currExpr = parseMultiply(currExpr);
                    continue;
                }
                if (take('/')) {
                    currExpr = parseDivide(currExpr);
                    continue;
                }
                return currExpr;
            }
        }

        TripleExpression parseThird(TripleExpression currExpr) {
            while (true) {
                skipWS();
                if (take('(')) {
                    currExpr = parseExpression();
                    take(')');
                    continue;
                }
                if (currExpr == null) {
                    currExpr = parseFirst();
                    continue;
                }
                if (take('+')) {
                    currExpr = parseAdd(currExpr);
                    continue;
                }
                if (take('-')) {
                    currExpr = parseSubtraction(currExpr);
                    continue;
                }
                return currExpr;
            }
        }

        TripleExpression parseExpression() {
            skipWS();
            TripleExpression expression;
            expression = parseFirst();
            expression = parseSecond(expression);
            expression = parseThird(expression);
            take(BaseParser.END);
            return expression;
        }

        private TripleExpression parseConst(int multiplier) {
            skipWS();
            final StringBuilder sb = new StringBuilder();
            takeInteger(sb);
            try {
                return new Const((Integer.parseInt(Math.pow(-1, multiplier) < 0 ? "-" + sb.toString() : sb.toString())));
            } catch (final NumberFormatException e) {
                throw error("Invalid number " + sb);
            }
        }

        private TripleExpression parseVar(int multiplier) {
            skipWS();
            char varName = take();
            return Math.pow(-1, multiplier) < 0 ? new Negate(new Variable(String.valueOf(varName))) : new Variable(String.valueOf(varName));
        }

        private TripleExpression parseMinus() {
            skipWS();
            int multiplier = 1;
            if (take('-')) {
                return new Negate((UniteExpression) parseMinus());
            }
            if (between('0', '9')) {
                return parseConst(multiplier);
            }
            if (between('x', 'z')) {
                return parseVar(multiplier);
            }

            return new Negate((UniteExpression) parseFirst());
        }

        private TripleExpression parseLZero() {
            skipWS();
            if (take('-')) {
                return new LZero((UniteExpression) parseMinus());
            }
            return new LZero((UniteExpression) parseFirst());
        }

        private TripleExpression parseTZero() {
            skipWS();
            if (take('-')) {
                return new TZero((UniteExpression) parseMinus());
            }
            return new TZero((UniteExpression) parseFirst());
        }

        private TripleExpression parseAdd(TripleExpression expression) {
            skipWS();
            TripleExpression getExpression = parseSecond(null);
            return new Add((UniteExpression) expression, (UniteExpression) getExpression);

        }

        private TripleExpression parseSubtraction(TripleExpression expression) {
            skipWS();
            TripleExpression getExpression = parseSecond(null);
            return new Subtract((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private TripleExpression parseMultiply(TripleExpression expression) {
            skipWS();
            TripleExpression getExpression = parseFirst();
            return new Multiply((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private TripleExpression parseDivide(TripleExpression expression) {
            skipWS();
            TripleExpression getExpression = parseFirst();
            return new Divide((UniteExpression) expression, (UniteExpression) getExpression);
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