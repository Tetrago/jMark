package jmark.node;

import jmark.Token;

public class Table extends Node
{
    public Table()
    {
        super(Token.TABLE);
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.TABLE_ROW;
    }
}
