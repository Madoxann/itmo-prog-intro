package expression.exceptions;

import expression.UniteExpression;
import expression.TripleExpression;

public class ExpressionParser implements Parser {
    public ExpressionParser() {
        //lmao why the constructor?
    }

    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        return new TripleParser(new StringSource(expression)).parseExpression();
    }

    private static class TripleParser extends BaseParser {
        protected TripleParser(CharSource source) {
            super(source);
        }

        //) - closing bracket
        //F - first priority
        //S - second
        //T - third

        TripleExpression parseExpression(char ch) throws ParsingException {
            TripleExpression currExpr = null;
            while (!take(ch)) {
                skipWS();
                if (!isAcceptable() && !test(')') ||
                        test(')') && ch == BaseParser.END) {
                    if (this.ch == '\u0000') {
                        throw new MissingSecondArgumentException();
                    }
                    throw new UnexpectedSymbolException(source.getErrorMsg());
                }

                if (take('(')) {
                    currExpr = parseExpression(')');
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
                        if (!(Character.isWhitespace(this.ch) || (isOpeningBr()))) {
                            throw new WhitespaceException(source.getErrorMsg());
                        }
                        currExpr = parseLZero();
                    }
                    if (take('t')) {
                        expect('0');
                        if (!(Character.isWhitespace(this.ch) || (isOpeningBr()))) {
                            throw new WhitespaceException(source.getErrorMsg());
                        }
                        currExpr = parseTZero();
                    }
                }

                if (ch == 'F') {
                    if (currExpr == null){
                        throw new MissingUnaryArgumentException();
                    }
                    return currExpr;
                }
                if (ch == 'S' || ch == BaseParser.END || ch == ')') {
                    if (currExpr == null) {
                        throw new MissingFirstArgumentException();
                    }
                    if (take('*')) {
                        currExpr = parseMultiply(currExpr);
                    }
                    if (take('/')) {
                        currExpr = parseDivide(currExpr);
                    }
                }

                if ((test('+') || test('-') || test(END) || test(')') || test('m') ) && ch == 'S') {
                    return currExpr;
                }

                //if ((test('+') || test('-') || test(END) || test(')') || test('m') )) {
                  //  return currExpr;
                //}

                if (ch == BaseParser.END || ch == ')') {
                    if (currExpr == null) {
                        //Mb first arg
                        throw new ParsingException("Binary operator as unary");
                    }
                    if (take('+')) {
                        currExpr = parseAdd(currExpr);
                    }
                    if (take('-')) {
                        currExpr = parseSubtraction(currExpr);
                    }
                    if (take('m')) {
                        if (take('i')) {
                            expect('n');
                            currExpr = parseMin(currExpr);
                        } else {
                            expect("ax");
                            currExpr = parseMax(currExpr);
                        }
                    }
                }

                if (currExpr != null && ch != BaseParser.END && ch != ')' && ch != 'S') {
                    return currExpr;
                }
            }
            if (currExpr == null) throw new MissingBracketExpressionException();
            return currExpr;
        }

        private TripleExpression parseExpression() throws ParsingException {
            return parseExpression(BaseParser.END);
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

        private TripleExpression parseVar(int multiplier) throws ParsingException {
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
            return new CheckedNegate((UniteExpression) parseExpression('F'));
        }

        private TripleExpression parseLZero() throws ParsingException{
            skipWS();
            if (take('-')) {
                return new CheckedLZero((UniteExpression) parseMinus());
            }
            return new CheckedLZero((UniteExpression) parseExpression('F'));
        }

        private TripleExpression parseTZero() throws ParsingException {
            skipWS();
            if (take('-')) {
                return new CheckedTZero((UniteExpression) parseMinus());
            }
            return new CheckedTZero((UniteExpression) parseExpression('F'));
        }

        private TripleExpression parseAdd(TripleExpression expression) throws ParsingException {
            skipWS();
            TripleExpression getExpression = parseExpression('S');
            return new CheckedAdd((UniteExpression) expression, (UniteExpression) getExpression);

        }

        private TripleExpression parseSubtraction(TripleExpression expression) throws ParsingException{
            skipWS();
            TripleExpression getExpression = parseExpression('S');
            return new CheckedSubtract((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private TripleExpression parseMultiply(TripleExpression expression) throws ParsingException{
            skipWS();
            TripleExpression getExpression = parseExpression('F');
            return new CheckedMultiply((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private TripleExpression parseDivide(TripleExpression expression) throws ParsingException{
            skipWS();
            TripleExpression getExpression = parseExpression('F');
            return new CheckedDivide((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private TripleExpression parseMin(TripleExpression expression) throws ParsingException {
            if (!(Character.isWhitespace(this.ch) || isOpeningBr() || this.ch == '-')) {
                throw new WhitespaceException(source.getErrorMsg());
            }
            skipWS();
            TripleExpression getExpression = parseExpression('S');
            return new CheckedMin((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private TripleExpression parseMax(TripleExpression expression) throws ParsingException {
            if (!(Character.isWhitespace(this.ch) || isOpeningBr() || this.ch == '-')) {
                throw new WhitespaceException(source.getErrorMsg());
            }
            skipWS();
            TripleExpression getExpression = parseExpression('S');
            return new CheckedMax((UniteExpression) expression, (UniteExpression) getExpression);
        }

        private void takeDigits(final StringBuilder sb) {
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
                throw new InvalidNumberException(sb.toString() + take());
            }
        }
    }
}
