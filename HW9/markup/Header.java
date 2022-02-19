package md2html.markup;

public class Header extends AbstractMarkupElement {
    public Header(String str, int headerLevel) { super(str.substring(headerLevel + 1), "h" + headerLevel); }

    @Override
    public String toMarkdown() { return markupText.toString(); }
}
