package birdsnail.example.graph;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 图中节点
 */
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GraphNode<E> {

    /**
     * 节点value
     */
    @EqualsAndHashCode.Include
    private E value;

    /**
     * 节点入度。指向该节点的数量
     */
    private int inPut;

    /**
     * 节点出度。节点指出的数量
     */
    private int out;

    /**
     * 邻近节点。以本几点为起始节点，可以直接到达的目标节点
     */
    private List<GraphNode<E>> next = new ArrayList<>();

    /**
     * 节点包含的边。指向的邻近节点才算边
     */
    private List<GraphEdge<E>> edges = new ArrayList<>();

    public GraphNode(E value) {
        this.value = value;
        inPut = 0;
        out = 0;
    }

    /**
     * 添加边
     */
    public void addEdge(GraphEdge<E> edge) {
        if (edge == null || !this.equals(edge.getFrom())) {
            return;
        }

        // 增加邻近节点
        edges.add(edge);
        out++;
        next.add(edge.getTo());
    }

    /**
     * 入度自增
     */
    public void incrementInput() {
        inPut++;
    }

    /**
     * 入度自减
     */
    public void decrementInput() {
        inPut--;
    }

    public void destroy() {
        this.inPut = 0;
        this.out = 0;
        this.next = null;
        this.edges = null;
    }

}
