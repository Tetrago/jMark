package jmark.node;

import jmark.Token;

public class TableCell extends Node
{
    private boolean header_;
    private Table.Align align_ = Table.Align.LEFT;

    public TableCell() { this(false); }
    public TableCell(boolean header)
    {
        header_ = header;
    }

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

    public boolean isHeader() { return header_; }
    public Table.Align getAlign() { return align_; }
}
