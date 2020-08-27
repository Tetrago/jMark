package jmark.node;

import jmark.Token;

public class TableCell extends Node
{
    public TableCell()
    {
        super(Token.TABLE_CELL);
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return true;
    }
}
