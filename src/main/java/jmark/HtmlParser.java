package jmark;

import jmark.node.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HtmlParser
{
    private Lexer lexer_;

    public HtmlParser(Lexer lexer)
    {
        lexer_ = lexer;
    }

    /**
     * Parses token tree into html.
     *
     * @return String of html.
     */
    public String parse()
    {
        StringBuilder builder = new StringBuilder();
        recursiveParse(lexer_.getRootNode(), builder);
        return builder.toString();
    }

    /**
     * Recursive method to parse node its children.
     *
     * @param node      Base node.
     * @param builder   String builder to append.
     */
    private void recursiveParse(Node node, StringBuilder builder)
    {
        writeHeader(node, builder);

        for(Node child : node.getNodes())
        {
            recursiveParse(child, builder);
        }

        writeFooter(node, builder);
    }

    /**
     * Loads embedded stylesheet into string.
     *
     * @return Stylesheet data.
     */
    private String loadStylesheet()
    {
        StringBuilder builder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("style.css"))))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                builder.append(line.trim());
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }

        return builder.toString();
    }

    /***
     * Finds tags used for styling.
     *
     * @param type  Enum type.
     *
     * @return String style tag.
     */
    private String findStyleTags(StyleNode.Type type)
    {
        switch(type)
        {
        default: return "";
        case ITALIC: return "<em>";
        case BOLD: return "<strong>";
        case BOTH: return "<em><strong>";
        }
    }

    /***
     * Writes beginning tag for node.
     *
     * @param node      Node to parse.
     * @param builder   Builder to append to.
     */
    private void writeHeader(Node node, StringBuilder builder)
    {
        switch(node.getToken())
        {
        case DOCUMENT:
            builder.append("<html><head><title>").append(((Document)node).getTitle())
                    .append("</title><style>").append(loadStylesheet())
                    .append("</style></head><body>");
            break;
        case HEADING:
            builder.append("<h").append(((Heading)node).getLevel()).append(" class=\"heading\">");
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
            ((Table)node).applyAlignments();
            builder.append("<table>");
            break;
        case TABLE_ROW:
            builder.append("<tr>");
            break;
        case TABLE_CELL:
            TableCell cell = (TableCell)node;
            builder.append(cell.isHeader() ? "<th" : "<td").append(" class=\"align-")
                    .append(cell.getAlign().toString().toLowerCase()).append("\">");
            break;
        case STYLE:
            builder.append(findStyleTags(((StyleNode)node).getType()));
            break;
        case HYPERLINK:
            builder.append("<a href=\"").append(((Hyperlink)node).getLocation()).append("\">");
            break;
        case IMAGE:
            Image image = (Image)node;
            builder.append("<img src=\"")
                    .append(image.getPath()).append("\" alt=\"")
                    .append(image.getAlt()).append("\" title=\"")
                    .append(image.getTitle()).append("\">");
            break;
        case CODE:
            Code code = (Code)node;

            if(code.isMultiline())
            {
                builder.append("<pre>");
            }

            builder.append("<code>");
            builder.append(code.getText());
            break;
        case BLOCK_QUOTE:
            builder.append("<blockquote>");
            break;
        }
    }

    /***
     * Wrties ending tag for node.
     *
     * @param node      Node to parse.
     * @param builder   Builder to append.
     */
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
            builder.append(((TableCell)node).isHeader() ? "</th>" : "</td>");
            break;
        case STYLE:
            builder.append(findStyleTags(((StyleNode)node).getType()).replaceAll("<", "</"));
            break;
        case HYPERLINK:
            builder.append("</a>");
            break;
        case CODE:
            builder.append("</code>");

            if(((Code)node).isMultiline())
            {
                builder.append("</pre>");
            }

            break;
        case BLOCK_QUOTE:
            builder.append("</blockquote>");
            break;
        }
    }
}
