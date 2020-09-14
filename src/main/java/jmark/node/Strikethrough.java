package jmark.node;

import jmark.Token;
import jmark.html.IHtmlParsable;

public class Strikethrough extends Node implements IHtmlParsable
{
    private String text_;

    public Strikethrough(String text)
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
        return Token.STRIKETHROUGH;
    }

    @Override
    public String toString()
    {
        return "Strike Through: " + text_;
    }

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<del>").append(text_);
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append("</del>");
    }
}
