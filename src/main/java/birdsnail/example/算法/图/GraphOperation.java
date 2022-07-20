package birdsnail.example.算法.图;

import java.util.*;

import birdsnail.example.graph.Graph;
import birdsnail.example.graph.GraphEdge;
import birdsnail.example.graph.GraphNode;
import birdsnail.example.算法.并查集.DefaultUnionSet;
import birdsnail.example.算法.并查集.IUnionSet;

/**
 * 图操作
 */
public class GraphOperation {


    /**
     * 图的深度优先遍历
     *
     * @param node 图节点
     */
    public static List<Integer> dfsGraph(GraphNode<Integer> node) {
        if (node == null) {
            return Collections.emptyList();
        }

        Set<GraphNode<Integer>> temp = new HashSet<>();
        List<Integer> result = new ArrayList<>();

        doDfs(node, temp, result);

        return result;
    }

    private static void doDfs(GraphNode<Integer> node, Set<GraphNode<Integer>> exists, List<Integer> result) {
        if (node == null || exists.contains(node)) {
            return;
        }

        result.add(node.getValue());
        exists.add(node);

        for (GraphNode<Integer> nextNode : node.getNext()) {
            doDfs(nextNode, exists, result);
        }

    }


    /**
     * 图的宽度优先遍历
     *
     * @param node 图节点
     */
    public static List<Integer> bfsGraph(GraphNode<Integer> node) {
        Deque<GraphNode<Integer>> queue = new ArrayDeque<>();
        Set<GraphNode<Integer>> temp = new HashSet<>();
        queue.add(node);
        temp.add(node);

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            GraphNode<Integer> gn = queue.poll();
            result.add(gn.getValue());

            if (gn.getNext() != null) {
                for (GraphNode<Integer> nn : gn.getNext()) {
                    if (!temp.contains(nn)) {
                        queue.add(nn);
                        temp.add(nn);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 图的拓扑排序，只有DAG（有向无环图）才有拓扑排序一说
     * <ol>
     *     <li>先找出图中入度为零的点</li>
     *     <li>移除它以及它包含的边</li>
     *     <li>重复一，二步骤</li>
     * </ol>
     *
     * @return 按照拓扑排序后的顶点
     */
    public static List<Integer> topoSorting(int[][] graphArray) {
        Graph<Integer, Integer> graph = Graph.generateGraphFromMatrix(graphArray);

        List<Integer> result = new ArrayList<>();
        while (!graph.isEmpty()) {
            GraphNode<Integer> minInputNode = graph.getMinInputNode();
            if (minInputNode == null) {
                return result;
            }
            result.add(minInputNode.getValue());
            graph.remove(minInputNode.getValue());
        }

        return result;
    }

    /**
     * 生成树：无向图 G 的生成树（英语：Spanning Tree）是具有 G 的全部顶点，但边数最少的连通子图。
     * <p>
     * K算法最小生成树(最小权重生成树)：在一个连通图中可能有多个生成树，总会有一个生成树的边的权值之和小于或等于其他生成树的边的权值之和
     * <br/>
     *
     * <p>思路：<br/>
     *    - 贪心算法：遍历edge，从还未遍历的edge中取权重最小的边<br/>
     *    - 并查集：合并两个没有连通的边
     * </p>
     *
     * @return 权重之和最小的 边的集合
     */
    public static Set<GraphEdge<Integer>> kruskalMst(int[][] graphArray) {
        Graph<Integer, Integer> graph = Graph.generateGraphFromMatrix(graphArray);

        List<GraphEdge<Integer>> edges = graph.getEdges();
        edges.sort(Comparator.comparingInt(GraphEdge::getWeight)); // 排完序后从小到大遍历
        IUnionSet<GraphNode<Integer>> edgeUnionSet = new DefaultUnionSet<>(graph.getNodes());

        Set<GraphEdge<Integer>> result = new HashSet<>();
        for (GraphEdge<Integer> edge : edges) {
            if (!edgeUnionSet.isSameSet(edge.getFrom(), edge.getTo())) {
                edgeUnionSet.union(edge.getFrom(), edge.getTo());
                result.add(edge);
            }
        }

        return result;
    }


}
