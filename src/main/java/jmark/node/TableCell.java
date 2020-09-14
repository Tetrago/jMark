package jmark.node;

import jmark.Token;
import jmark.html.IHtmlParsable;

public class TableCell extends Node implements IHtmlParsable
{
    private boolean header_;
    private Table.Align align_ = Table.Align.LEFT;

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

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append(header_ ? "<th" : "<td").append(" class=\"align-")
                .append(align_.toString().toLowerCase()).append("\">");
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append(header_ ? "</th>" : "</td>");
    }
}
