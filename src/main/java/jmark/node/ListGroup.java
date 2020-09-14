package jmark.node;

import jmark.Token;
import jmark.html.ISimpleHtmlParsable;

public class ListGroup extends Node implements ISimpleHtmlParsable
{
    private boolean ordered_;

    public ListGroup(boolean ordered)
    {
        ordered_ = ordered;
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.LIST_ITEM
                && ((ListItem)node).isOrdered() == ordered_;
    }

    @Override
    public Token getToken()
    {
        return Token.LIST_GROUP;
    }

    @Override
    public String toString()
    {
        return (ordered_ ? "Ordered" : "Unordered") + " List";
    }

    @Override
    public String getHtmlTagName()
    {
        return ordered_ ? "ol" : "ul";
    }
}
