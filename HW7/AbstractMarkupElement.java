package markup;

import java.util.List;

public abstract class AbstractMarkupElement {
    protected StringBuilder markupText;
    protected StringBuilder BBCodeText;

    public AbstractMarkupElement(StringBuilder s) {
        markupText = new StringBuilder(s);
        BBCodeText = new StringBuilder(s);
    }

    public AbstractMarkupElement(List<AbstractMarkupElement> lst, String markupSpecial, String BBCodeSpecial) {
        markupText = new StringBuilder(markupSpecial);
        BBCodeText = new StringBuilder(BBCodeSpecial);
        for (AbstractMarkupElement item : lst) {
            StringBuilder getMarkdown = new StringBuilder();
            StringBuilder getBBCode = new StringBuilder();
            item.toMarkdown(getMarkdown);
            markupText.append(getMarkdown);
            item.toBBCode(getBBCode);
            BBCodeText.append(getBBCode);
        }
        markupText.append(markupSpecial);
        String closeTag = BBCodeSpecial.replace("[", "[/");
        BBCodeText.append(closeTag);
    }

    public abstract void toMarkdown(StringBuilder str);

    public abstract void toBBCode(StringBuilder str);
}
