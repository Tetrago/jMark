package jmark;

import java.util.ListIterator;

public class HtmlParser
{
    private Lexer lexer_;

    public HtmlParser(Lexer lexer)
    {
        lexer_ = lexer;
    }

    public String parse()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");

        ListIterator<Token> iterator = lexer_.getTokens().listIterator();
        while(iterator.hasNext())
        {
            parseElement(builder, iterator);
        }

        builder.append("</body></html>");
        return builder.toString();
    }

    private void parseElement(StringBuilder builder, ListIterator<Token> iterator)
    {
        Token current = iterator.next();

        if(current.getType() == Token.Type.Heading)
        {
            int level = current.getValue().length();

            builder.append("<h").append(level).append(">");

            if(iterator.hasNext())
                builder.append(iterator.next().getValue());

            builder.append("</h").append(level).append(">");
        }
        else if(current.getType() == Token.Type.Text)
        {
            builder.append("<p>").append(current.getValue()).append("</p>");
        }
        else if(current.getType() == Token.Type.UnorderedList)
        {
            builder.append("<ul>");



            builder.append("</ul>");
        }
    }
}
