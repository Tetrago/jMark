package jmark.node;

import jmark.Token;

public class TableCell extends Node
{
    private Table.Align align_ = Table.Align.LEFT;

    public void align(Table.Align align)
    {
        align_ = align;
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

    @Override
    public Token getToken()
    {
        return Token.TABLE_CELL;
    }

    @Override
    public String toString()
    {
        return "Cell: " + align_.toString();
    }

    public Table.Align getAlign() { return align_; }
}
