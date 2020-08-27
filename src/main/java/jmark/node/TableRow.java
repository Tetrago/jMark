package jmark.node;

import jmark.Token;

public class TableRow extends Node
{
    public TableRow()
    {
        super(Token.TABLE_ROW);
    }

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
    public String toString()
    {
        return "Row";
    }
}
