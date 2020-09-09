package jmark.node;

import jmark.Token;

public class BlockQuote extends Node
{
    public static class Item extends Node
    {
        @Override
        public boolean isComplete()
        {
            return getNodes().size() >= 1;
        }

        @Override
        public boolean accepts(Node node)
        {
            return true;
        }

        @Override
        public Token getToken()
        {
            return Token.BLOCK_QUOTE;
        }

        @Override
        public String toString()
        {
            return "Block Quote Item";
        }
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public boolean accepts(Node node)
    {
        return node.getToken() == Token.BLOCK_QUOTE;
    }

    @Override
    public Token getToken()
    {
        return Token.BLOCK_QUOTE;
    }

    @Override
    public String toString()
    {
        return "Block Quote";
    }
}
