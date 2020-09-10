package jmark.node;

import jmark.Token;

public class Strikethrough extends Node
{
    private String text_;

    public Strikethrough(String text)
    {
        text_ = text;
    }

    @Override
    public boolean isComplete()
    {
        return true;
    }

    @Override
    public boolean accepts(Node node)
    {
        return false;
    }

    @Override
    public Token getToken()
    {
        return Token.STRIKETHROUGH;
    }

    @Override
    public String toString()
    {
        return "Strike Through: " + text_;
    }

    public String getText() { return text_; }
}
