import java.io.*;
import java.util.NoSuchElementException;

public class MyReader implements AutoCloseable {
    private final Reader in;

    private final StringBuilder str;
    private int charsRead;
    private int countLines;

    private Integer cachedInt;
    private String cachedLine;
    private String cachedString;

    private boolean lastNewLineRead = false;

    private static final int defaultBufferSize = 1024;

    public MyReader(Reader rd, int sz) throws IOException {
        this.in = rd;
        this.str = new StringBuilder();
        if (sz <= 0) {
            throw new IllegalArgumentException("Buffer size must be > 0");
        }
        char[] cbuf = new char[sz];
        int readChars = in.read(cbuf);
        while (readChars != -1) {
            str.append(cbuf, 0, readChars);
            readChars = in.read(cbuf);
        }
        charsRead = 0;
        countLines = 1;
    }

    public MyReader(Reader rd) throws IOException {
        this(rd, defaultBufferSize);
    }

    public MyReader(InputStream stream) throws IOException {
        this(new InputStreamReader(stream), defaultBufferSize);
    }

    public MyReader(InputStream stream, int sz) throws IOException {
        this(new InputStreamReader(stream), sz);
    }

    public MyReader(String str) throws IOException {
        this(new StringReader(str), defaultBufferSize);
    }

    public MyReader(String str, int sz) throws IOException {
        this(new StringReader(str), sz);
    }

    public int nextInt() {
        if (cachedInt != null) {
            int tmp = cachedInt;
            cachedInt = null;
            return tmp;
        }
        StringBuilder retInt = new StringBuilder();
        for (int i = charsRead; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                retInt.append(currentChar);
                if (i == str.length() - 1) {
                    charsRead = i + 1;
                    return Integer.parseInt(retInt.toString());
                }
            } else if (currentChar == '-' && retInt.length() == 0) {
                retInt.append(currentChar);
            } else {
                if (!retInt.isEmpty()) {
                    charsRead = i + 1;
                    return Integer.parseInt(retInt.toString());
                }
            }
        }
        throw new NoSuchElementException();
    }

    public String nextLine() {
        if (cachedLine != null) {
            String tmp = cachedLine;
            cachedLine = null;
            return tmp;
        }
        for (int i = charsRead; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (currentChar == '\n' || currentChar == '\r' || i == str.length() - 1) {
                int tmp = charsRead;
                charsRead = i + 1;
                if (i + 1 < str.length() && System.lineSeparator().equals("" + currentChar + str.charAt(i+1)) ) {
                    charsRead++;
                }
                return str.substring(tmp, i);
            }
        }
        return null;
    }

    public String nextWord() throws IOException {
        if (cachedString != null) {
            String tmp = cachedString;
            cachedString = null;
            return tmp;
        }
        StringBuilder retStr = new StringBuilder();
        for (int i = charsRead; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            lastNewLineRead = false;
            if (Character.isLetter(currentChar) || (Character.getType(currentChar) == Character.DASH_PUNCTUATION) || (currentChar == '\'')) {
                retStr.append(Character.toLowerCase(currentChar));
                if (i == str.length() - 1) {
                    charsRead = i + 1;
                    return retStr.toString();
                }
            } else {
                if (currentChar == '\n' || System.lineSeparator().equals(String.valueOf(currentChar))) {
                    countLines++;
                    lastNewLineRead = true;
                }
                if (!retStr.isEmpty()) {
                    charsRead = i + 1;
                    return retStr.toString();
                }
            }
        }
        return null;
    }

    public boolean hasNextInt() {
        if (cachedInt != null) return true;
        try {
            cachedInt = nextInt();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean hasNextLine() {
        if (cachedLine != null) return true;
        cachedLine = nextLine();
        return cachedLine != null;
    }

    public boolean hasNextWord() throws IOException {
        if (cachedString != null) return true;
        cachedString = nextWord();
        return cachedString != null;
    }

    public int getCountLines() {
        if (lastNewLineRead) return countLines - 1;
        return countLines;
    }

    public void close() throws IOException {
        this.in.close();
    }
}