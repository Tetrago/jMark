package jmark.node;

import jmark.Token;
import jmark.html.IHtmlParsable;

public class ChecklistItem extends Node implements IHtmlParsable
{
    private boolean checked_;

    public ChecklistItem(boolean checked)
    {
        checked_ = checked;
    }

    @Override
    public boolean isComplete()
    {
        return getNodes().size() == 1;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.HEADING
                || node.getToken() == Token.TEXT;
    }

    @Override
    public Token getToken()
    {
        return Token.CHECKLIST_ITEM;
    }

    @Override
    public String toString()
    {
        return "Checklist Item: " + checked_;
    }

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<li><input type=\"checkbox\" ");
        
        if(checked_)
        {
            builder.append("name=\"checkedbox\"");
        }

        builder.append("/>");
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append("</li>");
    }
}
