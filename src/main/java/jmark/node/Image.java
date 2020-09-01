package jmark.node;

import jmark.Token;

public class Image extends Node
{
    private String alt_;
    private String path_;
    private String title_;

    public Image(String alt, String path, String title)
    {
        alt_ = alt;
        path_ = path;
        title_ = title;
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
        return Token.IMAGE;
    }

    @Override
    public String toString()
    {
        return "Image: " + title_;
    }

    public String getAlt() { return alt_; }
    public String getPath() { return path_; }
    public String getTitle() { return title_; }
}
