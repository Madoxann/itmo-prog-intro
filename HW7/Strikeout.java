package markup;

import java.util.List;

public class Strikeout extends AbstractMarkupElement {
    public Strikeout(List<AbstractMarkupElement> lst) {
        super(lst, "~", "[s]");
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
