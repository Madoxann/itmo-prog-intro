package md2html.markup;

public class Strikeout extends AbstractMarkupElement {
    public Strikeout(String str) { super(str.substring(2, str.length() - 2), "s"); }

    @Override
    public String toMarkdown() { return markupText.toString(); }
}
