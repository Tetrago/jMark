package jmark;

import jmark.node.*;

public class HtmlParser
{
    private Lexer lexer_;

    public HtmlParser(Lexer lexer)
    {
        lexer_ = lexer;
    }

    public String parse()
    {
        StringBuilder builder = new StringBuilder();
        recursiveParse(lexer_.getRootNode(), builder);
        return builder.toString();
    }

    private void recursiveParse(Node node, StringBuilder builder)
    {
        writeHeader(node, builder);

        for(Node child : node.getNodes())
        {
            recursiveParse(child, builder);
        }

        writeFooter(node, builder);
    }

    private void writeHeader(Node node, StringBuilder builder)
    {
        switch(node.getToken())
        {
        case DOCUMENT:
            builder.append("<html><head><title>").append(((Document)node).getTitle())
                    .append("</title></head><body>");
            break;
        case HEADING:
            builder.append("<h").append(((Heading)node).getLevel()).append(">");
            break;
        case TEXT:
            builder.append("<p>").append(((Text)node).getText());
            break;
        case LIST_GROUP:
            builder.append(((ListGroup)node).isOrdered() ? "<ol>" : "<ul>");
            break;
        case LIST_ITEM:
            builder.append("<li>");
            break;
        case TABLE:
            builder.append("<table>");
            break;
        case TABLE_ROW:
            builder.append("<tr>");
            break;
        case TABLE_CELL:
            builder.append("<td>");
            break;
        }
    }

    private void writeFooter(Node node, StringBuilder builder)
    {
        switch(node.getToken())
        {
        case DOCUMENT:
            builder.append("</body></html>");
            break;
        case HEADING:
            builder.append("</h").append(((Heading)node).getLevel()).append(">");
            break;
        case TEXT:
            builder.append("</p>");
            break;
        case LIST_GROUP:
            builder.append(((ListGroup)node).isOrdered() ? "</ol>" : "</ul>");
            break;
        case LIST_ITEM:
            builder.append("</li>");
            break;
        case TABLE:
            builder.append("</table>");
            break;
        case TABLE_ROW:
            builder.append("</tr>");
            break;
        case TABLE_CELL:
            builder.append("</td>");
            break;
        }
    }
}
