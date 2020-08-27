package jmark.node;

import jmark.Token;

public class TableRow extends Node
{
    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.TABLE_CELL;
    }

    @Override
    public Token getToken()
    {
        return Token.TABLE_ROW;
    }

    @Override
    public String toString()
    {
        return "Row";
    }
}
