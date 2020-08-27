package jmark.node;

import jmark.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Node
{
    private Token token_;
    private List<Node> nodes_ = new ArrayList<>();

    public Node(Token token)
    {
        token_ = token;
    }

    public void add(Node node)
    {
        nodes_.add(node);
    }

    public abstract boolean isComplete();
    public abstract boolean accepts(Node node);

    public final Token getToken() { return token_; }
    public final List<Node> getNodes() { return Collections.unmodifiableList(nodes_); }
}
