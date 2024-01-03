package birdsnail.example.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 图中的节点, 是一个顶点，有若干个边
 */
public class Node {

    private final String vertexName;
    private final List<Edge> edges;

    public Node(String vertexName) {
        this.vertexName = vertexName;
        edges = new ArrayList<>();
    }

    public String getVertexName() {
        return vertexName;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        if (!Objects.equals(this.vertexName, edge.getStartVertex())) {
            throw new IllegalStateException("不是该顶点的边");
        }
        edges.add(edge);
    }

    public void addEdge(String destination, int weight) {
        Edge edge = new Edge(this.vertexName, destination, weight);
        addEdge(edge);
    }

    public static Node of(String name) {
        return new Node(name);
    }
}
