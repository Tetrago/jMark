package jmark.node;

import jmark.Token;
import jmark.html.IPropertyHtmlParsable;

public class Hyperlink extends Node implements IPropertyHtmlParsable
{
    private String location_;

    public Hyperlink(String location)
    {
        location_ = location;
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.STYLE
                || node.getToken() == Token.TEXT;
    }

    @Override
    public Token getToken()
    {
        return Token.HYPERLINK;
    }

    @Override
    public String toString()
    {
        return "Hyperlink: " + location_;
    }

    @Override
    public String getHtmlTagProperties()
    {
        return String.format("href=\"%s\"", location_);
    }

    @Override
    public String getHtmlTagName()
    {
        return "a";
    }
}
