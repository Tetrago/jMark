package jmark.node;

import jmark.html.IHtmlParsable;
import jmark.Token;

public class Code extends Node implements IHtmlParsable
{
    private String text_;
    private boolean multiline_;

    public Code(String text, boolean multiline)
    {
        text_ = text;
        multiline_ = multiline;
    }

    @Override
    public boolean isComplete()
    {
        return getNodes().size() >= 1;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.TEXT;
    }

    @Override
    public Token getToken()
    {
        return Token.CODE;
    }

    @Override
    public String toString()
    {
        return "Code: " + (multiline_ ? "Block" : "Inline");
    }

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        if(multiline_)
        {
            builder.append("<pre>");
        }

        builder.append("<code>");
        builder.append(text_);
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append("</code>");

        if(multiline_)
        {
            builder.append("</pre>");
        }
    }
}
