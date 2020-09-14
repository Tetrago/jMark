package jmark.node;

import jmark.Token;
import jmark.html.ISimpleHtmlParsable;

public class ListItem extends Node implements ISimpleHtmlParsable
{
    private boolean ordered_;

    public ListItem(boolean ordered)
    {
        ordered_ = ordered;
    }

    @Override
    public boolean isComplete()
    {
        return getNodes().size() == 1;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.HEADING
                || node.getToken() == Token.TEXT;
    }

    @Override
    public Token getToken()
    {
        return Token.LIST_ITEM;
    }

    @Override
    public String toString()
    {
        return "Item";
    }

    public boolean isOrdered() { return ordered_; }

    @Override
    public String getHtmlTagName()
    {
        return "li";
    }
}
