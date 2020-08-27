package jmark;

import jmark.node.*;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
    private static final Pattern PATTERN = Pattern.compile("(?<table>(\\|[:\\-]+)+\\|)|(?<row>(\\|[^\\|\\n]+)+\\|)|(?<heading>#+)|(?<item>(?<ordered>\\d\\.)|(-))|(?<text>[^\\n]+)");

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
            if((match = matcher.group("table")) != null)        // Table ----------------------------------------
            {

            }
            else if((match = matcher.group("row")) != null)     // Row ------------------------------------------
            {
                if(stack.peek().getToken() != Token.TABLE)
                {
                    addWhenValid(new Table(), true);
                }

                TableRow row = new TableRow();
                stack.peek().add(row);
                stack.push(row);

                String[] split = match.split("\\|");
                for(int i = 1; i < split.length; ++i)
                {
                    TableCell cell = new TableCell();
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

            if(stack.peek().isComplete())
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
