package md2html.markup;

public class Code extends AbstractMarkupElement {
    public Code(String str) { super(str.substring(1, str.length() - 1), "code"); }

    @Override
    public String toMarkdown() { return markupText.toString(); }

}
