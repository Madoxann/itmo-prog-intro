package markup;

import java.util.List;

public class Emphasis extends AbstractMarkupElement {
    public Emphasis(List<AbstractMarkupElement> lst) {
        super(lst, "*", "[i]");
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        //str = markupText;
        str.append(markupText);
    }

    @Override
    public void toBBCode(StringBuilder str) {
        str.append(BBCodeText);
    }
}
