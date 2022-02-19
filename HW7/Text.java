package markup;

public class Text extends AbstractMarkupElement {

    public Text(String s) {
        super(new StringBuilder(s));
    }

    @Override
    public void toMarkdown(StringBuilder str) {
        str.append(markupText);
    }

    public void toBBCode(StringBuilder str) {
        str.append(BBCodeText);
    }
}
