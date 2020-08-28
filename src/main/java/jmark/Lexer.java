package jmark;

import jmark.node.*;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
    private static final Pattern PATTERN = Pattern.compile("(?<style>(?<styleCount>\\*+)(?<styleText>[^*]+)\\*+)|(?<table>(\\| *[:-]+ *)+\\|)|(?<row>(\\|[^\\|\\n]+)+\\|)|(?<heading>#+)|(?<item>(?<ordered>\\d\\.)|(-))|(?<text>[^\\n]+)");

    private String title_;
    private Node document_;
    private Stack<Node> stack_ = new Stack<>();

    public Lexer(String title)
    {
        title_ = title;
    }

    public void analyze(String source)
    {
        stack_.clear();
        stack_.add(document_ = new Document(title_));

        recursiveAnalyzer(source, stack_);
    }

    private void addWhenValid(Node node) { addWhenValid(node, false); }
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

    private void recursiveAnalyzer(String data, Stack<Node> stack)
    {
        Matcher matcher = PATTERN.matcher(data);

        while(matcher.find())
        {
            String match;
            if((match = matcher.group("style")) != null)        // Style ----------------------------------------
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

                int level = stack.size();
                addWhenValid(style, true);

                String text = matcher.group("styleText");
                recursiveAnalyzer(text, stack);

                while(stack.size() > level)
                {
                    stack.pop();
                }
            }
            else if((match = matcher.group("table")) != null    // Table ----------------------------------------
                && stack.peek().getToken() == Token.TABLE
                && ((Table)stack.peek()).getNodes().size() == 1)
            {
                Table table = (Table)stack.peek();

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
                if(stack.peek().getToken() != Token.TABLE)
                {
                    addWhenValid(new Table(split.length - 1), true);
                    header = true;
                }

                TableRow row = new TableRow();
                stack.push(row);

                for(int i = 1; i < split.length; ++i)
                {
                    TableCell cell = new TableCell(header);
                    int level = stack.size();

                    row.add(cell);
                    stack.push(cell);

                    recursiveAnalyzer(split[i].trim(), stack);

                    while(stack.size() > level)
                    {
                        stack.pop();
                    }
                }

                stack.pop();
                stack.peek().add(row);
            }
            else if((match = matcher.group("heading")) != null) // Heading --------------------------------------
            {
                addWhenValid(new Heading(match.length()), true);
            }
            else if((match = matcher.group("item")) != null)    // List -----------------------------------------
            {
                boolean ordered = matcher.group("ordered") != null;

                Node item = new ListItem(ordered);

                if(stack.peek().getToken() != Token.LIST_GROUP || !stack.peek().accepts(item))
                {
                    addWhenValid(new ListGroup(ordered), true);
                }

                stack.peek().add(item);
                stack.push(item);
            }
            else if((match = matcher.group("text")) != null)    // Text -----------------------------------------
            {
                addWhenValid(new Text(match.trim()));
            }

            while(stack.peek().isComplete())
            {
                stack.pop();
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

    private void recursiveString(Node node, StringBuilder builder, int indentLevel)
    {
        builder.append("\t".repeat(indentLevel)).append(node).append('\n');

        for(Node child : node.getNodes())
        {
            recursiveString(child, builder, indentLevel + 1);
        }
    }

    public Node getRootNode() { return document_; }
}
