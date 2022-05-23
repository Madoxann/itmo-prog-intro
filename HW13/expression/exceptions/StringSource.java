package expression.exceptions;

public class StringSource implements CharSource {
    private String data = "";
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public String getErrorMsg() {
        StringBuilder highlightedError = new StringBuilder(data);
        highlightedError.insert(pos - 1, ">");
        highlightedError.insert(pos + 1, "<");
        return pos + " in string: " + highlightedError;
    }
}
