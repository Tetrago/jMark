package jmark.node;

import jmark.Token;
import jmark.html.IHtmlParsable;

public class StyleNode extends Node implements IHtmlParsable
{
    public enum Type
    {
        ITALIC,
        BOLD,
        BOTH
    }

    private Type type_;
    private String open_;
    private String closed_;

    public StyleNode(Type type)
    {
        type_ = type;
        open_ = findStyleTags(type);
        closed_ = open_.replaceAll("<", "</");
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
    public String toString()
    {
        return "Style: " + type_.toString().toLowerCase();
    }

    @Override
    public Token getToken()
    {
        return Token.STYLE;
    }

    /**
     * Finds tags used for styling.
     *
     * @param type  Type to parse.
     *
     * @return String style tag.
     */
    private static String findStyleTags(Type type)
    {
        switch(type)
        {
        default: return "";
        case ITALIC: return "<em>";
        case BOLD: return "<strong>";
        case BOTH: return "<em><strong>";
        }
    }

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append(open_);
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append(closed_);
    }
}
