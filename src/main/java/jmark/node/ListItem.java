package jmark.node;

import jmark.Token;

public class ListItem extends Node
{
    private boolean ordered_;

    public ListItem(boolean ordered)
    {
        super(Token.LIST_ITEM);

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
    public String toString()
    {
        return "Item";
    }

    public boolean isOrdered() { return ordered_; }
}
