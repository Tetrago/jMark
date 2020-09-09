package jmark.node;

import jmark.Token;

public class Code extends Node
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

    public String getText() { return text_; }
    public boolean isMultiline() { return multiline_; }
}
