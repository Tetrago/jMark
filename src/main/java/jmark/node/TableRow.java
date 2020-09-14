package jmark.node;

import jmark.Token;
import jmark.html.ISimpleHtmlParsable;

public class TableRow extends Node implements ISimpleHtmlParsable
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

    @Override
    public String getHtmlTagName()
    {
        return "tr";
    }
}
