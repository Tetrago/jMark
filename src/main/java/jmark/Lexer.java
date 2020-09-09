package jmark;

import jmark.node.*;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
    private static final Pattern PATTERN = Pattern.compile(
            "(> +(?<blockQuote>[^\\n]+))" +
            "|(?<codeBlock>```\\n*?(?<codeBlockCode>.+)\\n*?```)|(?<inlineCode>`(?<inlineCodeCode>.+)`)" +
            "|(?<style>(?<styleCount>\\*+)(?<styleText>[^*]+)\\*+)" +
            "|(?<table>(\\| *[:-]+ *)+\\|)|(?<row>(\\|[^|\\n]+)+\\|)" +
            "|(?<heading>#+)|(?<item>(?<ordered>\\d\\.)|(-))" +
            "|(?<image>!\\[(?<imageAlt>[^\\n\\]]+)]\\((?<imagePath>[^\\n)]+) \"(?<imageTitle>[^\\n\"]+)\"\\))" +
            "|(?<link>\\[(?<linkText>[^\\n]+)]\\((?<linkLocation>[^\\n]+)\\))" +
            "|(?<text>[^\\n]+)");

    private String title_;
    private Node document_;
    private Stack<Node> stack_ = new Stack<>();

    /**
     * Creates and titles document.
     *
     * @param title Title of page.
     */
    public Lexer(String title)
    {
        title_ = title;
    }

    /**
     * Analyzes markdown.
     *
     * @param source    Markdown data.
     */
    public void analyze(String source)
    {
        stack_.clear();
        stack_.add(document_ = new Document(title_));

        recursiveAnalyzer(source);
    }

    /**
     * Adds to stack.
     *
     * <p>Will pop from stack until the top can accept a given node.</p>
     *
     * @param node  Node to add to top.
     */
    private void addWhenValid(Node node) { addWhenValid(node, false); }

    /**
     * Adds to stack.
     *
     * <p>Will pop from stack until the top can accept a given node.</p>
     *
     * @param node  Node to add to top.
     * @param push  Whether to push this element onto the stack on addition.
     */
    private void addWhenValid(Node node, boolean push)
    {
        while(!stack_.peek().accepts(node))
        {
            stack_.pop();
        }

        stack_.peek().add(node);
        if(push)
        {
            stack_.push(node);
        }
    }

    /**
     * Restores stack size.
     *
     * <p>Pops from stack until size is reached.</p>
     *
     * @param level Target stack size.
     */
    private void restore(int level)
    {
        while(stack_.size() > level)
        {
            stack_.pop();
        }
    }

    /**
     * Parses string into node tree.
     *
     * @param data  String to parse.
     */
    private void recursiveAnalyzer(String data)
    {
        Matcher matcher = PATTERN.matcher(data);

        while(matcher.find())
        {
            String match;
            if((match = matcher.group("blockQuote")) != null)   // Block Quote ----------------------------------
            {
                if(stack_.peek().getToken() != Token.BLOCK_QUOTE)
                {
                    addWhenValid(new BlockQuote(), true);
                }

                int level = stack_.size();

                addWhenValid(new BlockQuote.Item(), true);
                recursiveAnalyzer(match);

                restore(level);
            }
            else if((match = matcher.group("codeBlock")) != null)   // Block Code -------------------------------
            {
                addWhenValid(new Code(matcher.group("codeBlockCode"),true));
            }
            else if((match = matcher.group("inlineCode")) != null)  // Inline Code ------------------------------
            {
                addWhenValid(new Code(matcher.group("inlineCodeCode"),false));
            }
            else if((match = matcher.group("style")) != null)   // Style ----------------------------------------
            {
                StyleNode.Type type;

                switch(matcher.group("styleCount").length())
                {
                default:
                case 1:
                    type = StyleNode.Type.ITALIC;
                    break;
                case 2:
                    type = StyleNode.Type.BOLD;
                    break;
                case 3:
                    type = StyleNode.Type.BOTH;
                    break;
                }

                StyleNode style = new StyleNode(type);

                int level = stack_.size();
                addWhenValid(style, true);

                String text = matcher.group("styleText");
                recursiveAnalyzer(text);

                restore(level);
            }
            else if((match = matcher.group("table")) != null    // Table ----------------------------------------
                && stack_.peek().getToken() == Token.TABLE
                && ((Table)stack_.peek()).getNodes().size() == 1)
            {
                Table table = (Table)stack_.peek();

                String[] split = match.split("\\|");

                for(int i = 0; i < table.getColumnCount(); ++i)
                {
                    String trim = split[i + 1].trim();

                    if(trim.matches(":-+:"))
                    {
                        table.align(i, Table.Align.CENTER);
                    }
                    else if(trim.startsWith(":"))
                    {
                        table.align(i, Table.Align.LEFT);
                    }
                    else if(trim.endsWith(":"))
                    {
                        table.align(i, Table.Align.RIGHT);
                    }
                }
            }
            else if((match = matcher.group("row")) != null)     // Row ------------------------------------------
            {
                String[] split = match.split("\\|");

                boolean header = false;
                if(stack_.peek().getToken() != Token.TABLE)
                {
                    addWhenValid(new Table(split.length - 1), true);
                    header = true;
                }

                TableRow row = new TableRow();
                stack_.push(row);

                for(int i = 1; i < split.length; ++i)
                {
                    TableCell cell = new TableCell(header);
                    int level = stack_.size();

                    row.add(cell);
                    stack_.push(cell);

                    recursiveAnalyzer(split[i].trim());

                    restore(level);
                }

                stack_.pop();
                stack_.peek().add(row);
            }
            else if((match = matcher.group("heading")) != null) // Heading --------------------------------------
            {
                addWhenValid(new Heading(match.length()), true);
            }
            else if((match = matcher.group("item")) != null)    // List -----------------------------------------
            {
                boolean ordered = matcher.group("ordered") != null;

                Node item = new ListItem(ordered);

                if(stack_.peek().getToken() != Token.LIST_GROUP || !stack_.peek().accepts(item))
                {
                    addWhenValid(new ListGroup(ordered), true);
                }

                stack_.peek().add(item);
                stack_.push(item);
            }
            else if((match = matcher.group("image")) != null)   // Image ----------------------------------------
            {
                addWhenValid(new Image(
                        matcher.group("imageAlt"),
                        matcher.group("imagePath"),
                        matcher.group("imageTitle")));
            }
            else if((match = matcher.group("link")) != null)    // Link -----------------------------------------
            {
                String text = matcher.group("linkText");
                String location = matcher.group("linkLocation");
                Hyperlink link = new Hyperlink(location);

                int level = stack_.size();
                addWhenValid(link, true);

                recursiveAnalyzer(text);

                restore(level);
            }
            else if((match = matcher.group("text")) != null)    // Text -----------------------------------------
            {
                addWhenValid(new Text(match.trim()));
            }

            while(stack_.peek().isComplete())
            {
                stack_.pop();
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        recursiveString(document_, builder, 0);
        return builder.toString();
    }

    /**
     * String parser to convert tree in {@link #toString()}
     *
     * @param node        Node to convert to string.
     * @param builder     Builder to add to.
     * @param indentLevel Current level of indentation.
     */
    private void recursiveString(Node node, StringBuilder builder, int indentLevel)
    {
        builder.append("\t".repeat(indentLevel)).append(node).append('\n');

        for(Node child : node.getNodes())
        {
            recursiveString(child, builder, indentLevel + 1);
        }
    }

    /**
     * Gets the root node of the node tree.
     *
     * @return Root node.
     */
    public Node getRootNode() { return document_; }
}
