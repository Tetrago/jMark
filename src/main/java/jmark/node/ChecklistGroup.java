package jmark.node;

import jmark.Token;
import jmark.html.IPropertyHtmlParsable;

public class ChecklistGroup extends Node implements IPropertyHtmlParsable
{
    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.CHECKLIST_ITEM;
    }

    @Override
    public Token getToken()
    {
        return Token.CHECKLIST_GROUP;
    }

    @Override
    public String toString()
    {
        return "Checklist Group";
    }

    @Override
    public String getHtmlTagProperties()
    {
        return "class=\"checklist\"";
    }

    @Override
    public String getHtmlTagName()
    {
        return "ul";
    }
}
