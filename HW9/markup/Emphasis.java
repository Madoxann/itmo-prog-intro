package md2html.markup;

public class Emphasis extends AbstractMarkupElement {
    public Emphasis(String str) { super(str.substring(1, str.length() - 1), "em"); }

    @Override
    public String toMarkdown() { return markupText.toString(); }
}
