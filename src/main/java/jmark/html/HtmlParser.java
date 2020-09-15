package jmark.html;

import jmark.Lexer;
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
     * @param node Base node.
     * @param builder String builder to append.
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
     * Loads embedded resource into string.
     *
     * @param name Name of resource.
     *
     * @return Loaded resource data.
     */
    private static String loadResource(String name)
    {
        StringBuilder builder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(HtmlParser.class.getResourceAsStream(name))))
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

    /**
     * Loads embedded stylesheet into string.
     *
     * @return Stylesheet data.
     */
    public static String loadStylesheet()
    {
        return loadResource("style.css");
    }

    /**
     * Loads embedded JavaScript into string.
     *
     * @return JavaScript code.
     */
    public static String loadScript()
    {
        return loadResource("script.js");
    }

    /**
     * Writes beginning tag for node.
     *
     * @param node      Node to parse.
     * @param builder   Builder to append to.
     */
    private void writeHeader(Node node, StringBuilder builder)
    {
        if(node instanceof IHtmlParsable)
        {
            ((IHtmlParsable)node).writeHtmlHeader(builder);
        }
    }

    /**
     * Writes ending tag for node.
     *
     * @param node      Node to parse.
     * @param builder   Builder to append.
     */
    private void writeFooter(Node node, StringBuilder builder)
    {
        if(node instanceof IHtmlParsable)
        {
            ((IHtmlParsable)node).writeHtmlFooter(builder);
        }
    }
}
