package jmark.node;

import jmark.Token;
import jmark.html.ISimpleHtmlParsable;

public class Table extends Node implements ISimpleHtmlParsable
{
    public enum Align
    {
        LEFT,
        CENTER,
        RIGHT
    }

    private int columns_;
    private Align[] alignments_;

    public Table(int columns)
    {
        columns_ = columns;
        alignments_ = new Align[columns];
    }

    public void applyAlignments()
    {
        for(Node row : getNodes())
        {
            for(int i = 0; i < columns_; ++i)
            {
                ((TableCell)row.getNodes().get(i)).align(alignments_[i]);
            }
        }
    }

    public void align(int i, Align align)
    {
        alignments_[i] = align;
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

    @Override
    public Token getToken()
    {
        return Token.TABLE;
    }

    @Override
    public String toString()
    {
        applyAlignments();
        return "Table: " + columns_;
    }

    public int getColumnCount() { return columns_; }

    @Override
    public String getHtmlTagName()
    {
        applyAlignments();
        return "table";
    }
}
