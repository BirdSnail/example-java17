package birdsnail.example.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**

 * 图中连接节点的边
 */
@Setter
@Getter
public class GraphEdge<E> {

    /**
     * 边的权重
     */
    private int weight;

    /**
     * 起始节点
     */
    @EqualsAndHashCode.Include
    private GraphNode<E> from;

    /**
     * 目标节点
     */
    @EqualsAndHashCode.Include
    private GraphNode<E> to;

    public GraphEdge(int weight, GraphNode<E> from, GraphNode<E> to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
