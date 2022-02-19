package md2html.markup;

public class Paragraph extends AbstractMarkupElement {
    public Paragraph(String str) { super(str, "p"); }


    @Override
    public String toMarkdown() { return markupText.toString(); }
}

