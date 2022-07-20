package birdsnail.example.graph;

import java.util.*;
import java.util.function.Function;

/**
 * 图结构
 */
public class Graph<K, V> {

    /**
     * 图中所有的node
     */
    private final Map<K, GraphNode<V>> nodeMap = new HashMap<>();

    /**
     * 图中所有的边
     */
    private final Set<GraphEdge<V>> edgesSet = new HashSet<>();

    private final Function<? super V, ? extends K> keyMapper;

    public Graph(Function<V, K> keyMapper) {
        Objects.requireNonNull(keyMapper);
        this.keyMapper = keyMapper;
    }

    private GraphNode<V> putNode(K key, GraphNode<V> node) {
        nodeMap.putIfAbsent(key, node);
        return nodeMap.get(key);
    }


    public void addEdge(GraphEdge<V> edge) {
        if (edge == null || edgesSet.contains(edge) || edge.getFrom() == null) {
            return;
        }

        GraphNode<V> from = putNode(keyMapper.apply(edge.getFrom().getValue()), edge.getFrom());
        from.addEdge(edge);
        edge.setFrom(from);
        if (edge.getTo() != null) {
            GraphNode<V> to = putNode(keyMapper.apply(edge.getTo().getValue()), edge.getTo());
            // 目标节点入度加一
            to.incrementInput();
            edge.setTo(to);
        }

        edgesSet.add(edge);
    }

    public GraphNode<V> getNode(V e) {
        return putNode(keyMapper.apply(e), new GraphNode<>(e));
    }

    public int size() {
        return nodeMap.size();
    }

    public int edgeSize() {
        return edgesSet.size();
    }

    public List<GraphEdge<V>> getEdges() {
        return new ArrayList<>(edgesSet);
    }

    public List<GraphNode<V>> getNodes() {
        return new ArrayList<>(nodeMap.values());
    }

    public GraphNode<V> getMinInputNode() {
        Collection<GraphNode<V>> nodes = nodeMap.values();
        if (nodes.isEmpty()) {
            return null;
        }

        return nodes.stream().min(Comparator.comparing(GraphNode::getInPut)).orElse(null);
    }

    public void remove(K key) {
        GraphNode<V> node = nodeMap.get(key);
        if (node == null) {
            return;
        }

        nodeMap.remove(key);
        node.getEdges().forEach(edgesSet::remove);

        for (GraphNode<V> next : node.getNext()) {
            next.decrementInput();
        }

    }

    public boolean isEmpty() {
        return nodeMap.isEmpty();
    }


    // ===========================STATIC FACTORY METHOD=======================================

    /**
     * 矩阵格式【边的权重，from节点的值，to节点的值】
     */
    public static Graph<Integer, Integer> generateGraphFromMatrix(int[][] matrix) {
        Graph<Integer, Integer> graph = new Graph<>(Function.identity());
        for (int[] nodeArr : matrix) {
            int weight = nodeArr[0];
            GraphNode<Integer> fromNode = graph.getNode(nodeArr[1]);// from节点
            GraphNode<Integer> toNode = graph.getNode(nodeArr[2]);// to 节点
            GraphEdge<Integer> edge = new GraphEdge<>(weight, fromNode, toNode);
            graph.addEdge(edge);
        }

        return graph;
    }

}
