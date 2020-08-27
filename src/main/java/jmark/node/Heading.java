package jmark.node;

import jmark.Token;

public class Heading extends Node
{
    private int level_;

    public Heading(int level)
    {
        super(Token.HEADING);

        level_ = level;
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
    public String toString()
    {
        return "Heading: " + level_;
    }

    public int getLevel() { return level_; }
}
