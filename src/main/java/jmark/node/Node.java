package jmark.node;

import jmark.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Node
{
    private List<Node> nodes_ = new ArrayList<>();

    public final void add(Node node)
    {
        nodes_.add(node);
    }

    public abstract boolean isComplete();
    public abstract boolean accepts(Node node);

    public abstract Token getToken();
    public final List<Node> getNodes() { return Collections.unmodifiableList(nodes_); }
}
