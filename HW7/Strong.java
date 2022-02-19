package markup;

import java.util.List;

public class Strong extends AbstractMarkupElement {
    public Strong(List<AbstractMarkupElement> lst) {
        super(lst, "__", "[b]");
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
