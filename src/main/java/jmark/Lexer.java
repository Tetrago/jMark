package jmark;

import jmark.node.*;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
    private static final Pattern PATTERN = Pattern.compile("(?<heading>#+)|(?<item>(?<ordered>\\d\\.)|(-))|(?<text>[^\\n]+)");

    private String title_;
    private Node document_;

    public Lexer(String title)
    {
        title_ = title;
    }

    public void analyze(String source)
    {
        Matcher matcher = PATTERN.matcher(source);

        Stack<Node> stack = new Stack<>();
        stack.add(document_ = new Document(title_));

        while(matcher.find())
        {
            String match;
            if((match = matcher.group("heading")) != null)      // Heading --------------------------------------
            {
                Heading heading = new Heading(match.length());

                while(!stack.peek().accepts(heading))
                {
                    stack.pop();
                }

                stack.peek().add(heading);
                stack.push(heading);
            }
            else if((match = matcher.group("item")) != null)    // List -----------------------------------------
            {
                boolean ordered = matcher.group("ordered") != null;

                Node item = new ListItem(ordered);

                if(stack.peek().getToken() != Token.LIST_GROUP)
                {
                    ListGroup group = new ListGroup(ordered);

                    while(!stack.peek().accepts(group))
                    {
                        stack.pop();
                    }

                    stack.peek().add(group);
                    stack.push(group);
                }
                else if(!stack.peek().accepts(item))
                {
                    ListGroup group = new ListGroup(ordered);

                    while(!stack.peek().accepts(group))
                    {
                        stack.pop();
                    }

                    stack.peek().add(group);
                    stack.push(group);
                }

                stack.peek().add(item);
                stack.push(item);
            }
            else if((match = matcher.group("text")) != null)    // Text -----------------------------------------
            {
                Text text = new Text(match.trim());

                while(!stack.peek().accepts(text))
                {
                    stack.pop();
                }

                stack.peek().add(text);
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
