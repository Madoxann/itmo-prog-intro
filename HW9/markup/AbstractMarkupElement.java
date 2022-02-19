package md2html.markup;

public abstract class AbstractMarkupElement {
    protected StringBuilder markupText;

    public AbstractMarkupElement(String str, String markupSpecial) {
        markupSpecial = "<" + markupSpecial + ">";
        markupText = new StringBuilder(markupSpecial);
        markupText.append(str);
        markupText.append(markupSpecial.replace("<", "</"));
    }

    public abstract String toMarkdown();
}
