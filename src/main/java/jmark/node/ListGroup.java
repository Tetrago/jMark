package jmark.node;

import jmark.Token;

public class ListGroup extends Node
{
    private boolean ordered_;

    public ListGroup(boolean ordered)
    {
        super(Token.LIST_GROUP);

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
    public String toString()
    {
        return (ordered_ ? "Ordered" : "Unordered") + " List";
    }

    public boolean isOrdered() { return ordered_; }
}