package md2html.markup;

public class Variable extends AbstractMarkupElement {
    public Variable(String str) { super(str.substring(1, str.length() - 1), "var"); }

    @Override
    public String toMarkdown() { return markupText.toString(); }
}
