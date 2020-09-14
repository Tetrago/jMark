package jmark.node;

import jmark.Token;
import jmark.html.IHtmlParsable;

public class Text extends Node implements IHtmlParsable
{
    private String text_;

    public Text(String text)
    {
        text_ = text;
    }

    @Override
    public boolean isComplete()
    {
        return true;
    }

    @Override
    public boolean accepts(Node node)
    {
        return false;
    }

    @Override
    public Token getToken()
    {
        return Token.TEXT;
    }

    @Override
    public String toString()
    {
        return "Text: " + text_;
    }

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<p>").append(text_);
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append("</p>");
    }
}
