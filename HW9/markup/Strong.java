package md2html.markup;

public class Strong extends AbstractMarkupElement {
    public Strong(String str) { super(str.substring(2, str.length() - 2), "strong"); }

    @Override
    public String toMarkdown() { return markupText.toString(); }

}
