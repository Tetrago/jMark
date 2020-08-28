package jmark.node;

import jmark.Token;

public class StyleNode extends Node
{
    public enum Type
    {
        ITALIC,
        BOLD,
        BOTH
    }

    private Type type_;

    public StyleNode(Type type)
    {
        type_ = type;
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
        return Token.STYLE;
    }

    public Type getType() { return type_; }
}
