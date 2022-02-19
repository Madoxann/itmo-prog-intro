package markup;

import java.util.List;

public class Paragraph {
    private final List<AbstractMarkupElement> elements;

    public Paragraph(List<AbstractMarkupElement> lst) {
        elements = lst;
    }

    public void toMarkdown(StringBuilder str) {
        for (AbstractMarkupElement item : elements) {
            StringBuilder getMarkdown = new StringBuilder();
            item.toMarkdown(getMarkdown);
            str.append(getMarkdown.toString());
        }
    }

    public void toBBCode(StringBuilder str) {
        for (AbstractMarkupElement item : elements) {
            StringBuilder getBBCode = new StringBuilder();
            item.toBBCode(getBBCode);
            str.append(getBBCode.toString());
        }
    }
}
