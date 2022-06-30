package expression.exceptions;

import java.util.Set;

public class BaseParser {
    public static final char END = '\0';
    public final CharSource source;

    protected Set<Character> startingSymbols = null;

    private char ch = 0xffff;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    public boolean isWS() {
        return Character.isWhitespace(ch) || ch == '\r' || ch == '\n' || ch == '\t';
    }

    public boolean isAcceptable() {
        return startingSymbols == null || startingSymbols.contains(ch);
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    public void skipWS() {
        while (isWS()) {
            ch = source.hasNext() ? source.next() : END;
        }
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) throws UnexpectedSymbolException {
        if (!take(expected)) {
            throw new UnexpectedSymbolException(source.getErrorMsg());
        }
    }

    protected void expect(final String value) throws UnexpectedSymbolException {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return new IllegalArgumentException(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
