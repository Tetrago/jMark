package jmark.node;

import jmark.Token;

public class Document extends Node
{
    private String title_;

    public Document(String title)
    {
        super(Token.DOCUMENT);

        title_ = title;
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
        return "Document: " + title_;
    }

    public String getTitle() { return title_; }
}
