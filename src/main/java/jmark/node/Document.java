package jmark.node;

import jmark.html.HtmlParser;
import jmark.html.IHtmlParsable;
import jmark.Token;

public class Document extends Node implements IHtmlParsable
{
    private String title_;

    public Document(String title)
    {
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
    public Token getToken()
    {
        return Token.DOCUMENT;
    }

    @Override
    public String toString()
    {
        return "Document: " + title_;
    }

    @Override
    public void writeHtmlHeader(StringBuilder builder)
    {
        builder.append("<html><head><title>").append(title_)
                .append("</title><style>").append(HtmlParser.loadStylesheet())
                .append("</style></head><body>");
    }

    @Override
    public void writeHtmlFooter(StringBuilder builder)
    {
        builder.append("<script>").append(HtmlParser.loadScript()).append("</script>")
                .append("</body></html>");
    }
}
