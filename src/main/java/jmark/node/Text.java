package jmark.node;

import jmark.Token;

public class Text extends Node
{
    private String text_;

    public Text(String text)
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
        return Token.TEXT;
    }

    @Override
    public String toString()
    {
        return "Text: " + text_;
    }

    public String getText() { return text_; }
}
