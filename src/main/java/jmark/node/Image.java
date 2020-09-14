package jmark.node;

import jmark.Token;
import jmark.html.IHtmlParsable;

public class Image extends Node implements IHtmlParsable
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

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<img src=\"")
                .append(path_).append("\" alt=\"")
                .append(alt_).append("\" title=\"")
                .append(title_).append("\"/>");
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {

    }
}
