package jmark.node;

import jmark.Token;
import jmark.html.IPropertyHtmlParsable;

public class Heading extends Node implements IPropertyHtmlParsable
{
    private int level_;

    public Heading(int level)
    {
        level_ = level;
    }

    @Override
    public boolean isComplete()
    {
        return getNodes().size() >= 1;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.TEXT;
    }

    @Override
    public Token getToken()
    {
        return Token.HEADING;
    }

    @Override
    public String toString()
    {
        return "Heading: " + level_;
    }

    @Override
    public String getHtmlTagProperties()
    {
        return "class=\"heading\"";
    }

    @Override
    public String getHtmlTagName()
    {
        return "h" + level_;
    }
}
