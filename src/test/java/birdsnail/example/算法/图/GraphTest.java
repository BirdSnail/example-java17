package birdsnail.example.算法.图;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import birdsnail.example.graph.Graph;
import birdsnail.example.graph.GraphEdge;
import birdsnail.example.graph.GraphNode;

class GraphTest {

    @Test
    void createGraphFromMatrix() {
        int[][] matrix = {
                {5, 0, 1},
                {2, 1, 3},
                {1, 6, 9},
                {4, 4, 1},
                {75, 1, 9}
        };
        Graph<Integer, Integer> graph = Graph.generateGraphFromMatrix(matrix);
        Assertions.assertEquals(matrix.length, graph.edgeSize());

        Set<Integer> nodeSet = new HashSet<>();
        for (int[] nodes : matrix) {
            nodeSet.add(nodes[1]);
            nodeSet.add(nodes[2]);
        }

        Assertions.assertEquals(nodeSet.size(), graph.size());

        for (Integer value : nodeSet) {
            GraphNode<Integer> node = graph.getNode(value);
            Assertions.assertEquals(value, node.getValue());
        }

        GraphNode<Integer> node = graph.getNode(0);
        Assertions.assertEquals(1, node.getOut());
        Assertions.assertEquals(0, node.getInPut());
        Assertions.assertEquals(node.getNext().get(0), graph.getNode(1));

        node = graph.getNode(1);
        Assertions.assertEquals(2, node.getInPut());
        Assertions.assertEquals(2, node.getOut());
        Assertions.assertEquals(2, node.getNext().size());
        Assertions.assertTrue(node.getNext().containsAll(Arrays.asList(graph.getNode(3), graph.getNode(9))));

        node = graph.getNode(3);
        Assertions.assertEquals(1, node.getInPut());
        Assertions.assertEquals(0, node.getOut());
        Assertions.assertEquals(0, node.getNext().size());

        node = graph.getNode(9);
        Assertions.assertEquals(2, node.getInPut());
        Assertions.assertEquals(0, node.getOut());
        Assertions.assertEquals(0, node.getNext().size());

        // 单独的添加边
        GraphNode<Integer> from = new GraphNode<>(0);
        GraphNode<Integer> to = new GraphNode<>(4);
        graph.addEdge(new GraphEdge<>(16, from, to));

        node = graph.getNode(0);
        Assertions.assertEquals(2, node.getOut());
        Assertions.assertEquals(0, node.getInPut());
        Assertions.assertTrue(node.getNext().containsAll(Arrays.asList(graph.getNode(1), graph.getNode(4))));

    }

    @Test
    void bfsGraph() {
        int[][] matrix = {
                {5, 0, 1},
                {2, 1, 3},
                {1, 6, 9},
                {4, 4, 1},
                {75, 1, 9},
                {25, 1, 6}
        };

        Graph<Integer, Integer> graph = Graph.generateGraphFromMatrix(matrix);
        GraphNode<Integer> node = graph.getNode(0);
        List<Integer> bfsValue = GraphOperation.bfsGraph(node);
        Assertions.assertEquals(Arrays.asList(0, 1, 3, 9, 6), bfsValue);

        List<Integer> dfsValue = GraphOperation.dfsGraph(node);
        Assertions.assertEquals(Arrays.asList(0, 1, 3, 9, 6), dfsValue);

        GraphNode<Integer> newNode = graph.getNode(1);
        dfsValue = GraphOperation.dfsGraph(newNode);
        Assertions.assertEquals(Arrays.asList(1, 3, 9, 6), dfsValue);

        // 新加edge
        GraphNode<Integer> from = new GraphNode<>(0);
        GraphNode<Integer> to = new GraphNode<>(4);
        graph.addEdge(new GraphEdge<>(16, from, to));

        bfsValue = GraphOperation.bfsGraph(node);
        Assertions.assertEquals(Arrays.asList(0, 1, 4, 3, 9, 6), bfsValue);

        dfsValue = GraphOperation.dfsGraph(node);
        Assertions.assertEquals(Arrays.asList(0, 1, 3, 9, 6, 4), dfsValue);

    }

    @Test
    void topoSorting() {
        int[][] matrix = {
                {5, 0, 1},
                {2, 1, 3},
                {1, 6, 9},
                {4, 4, 1},
                {75, 1, 9}
        };

        List<Integer> res = GraphOperation.topoSorting(matrix);
        Assertions.assertEquals(Arrays.asList(0, 4, 6, 1, 3, 9), res);

    }

    @Test
    void kruskalMst() {
        int[][] matrix = {
                {5, 0, 1},
                {2, 1, 3},
                {1, 6, 9},
                {4, 4, 1},
                {75, 1, 9},
                {25, 1, 6}
        };

        Set<GraphEdge<Integer>> mst = GraphOperation.kruskalMst(matrix);
        Assertions.assertEquals(Set.of(1, 2, 4, 5, 25), mst.stream().map(GraphEdge::getWeight).collect(Collectors.toSet()));

    }

}