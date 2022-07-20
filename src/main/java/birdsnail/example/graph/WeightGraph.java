package birdsnail.example.graph;

import java.util.*;

public class WeightGraph {

    private final Map<String, Node> nodeMap = new HashMap<>();

    public WeightGraph() {
        init();
    }

    public void init() {
        Node start = Node.of("one");
        Node two = Node.of("two");
        Node three = Node.of("three");
        Node four = Node.of("four");
        Node five = Node.of("five");
        Node fine = Node.of("fine");

        nodeMap.put(start.getVertexName(), start);
        nodeMap.put(two.getVertexName(), two);
        nodeMap.put(three.getVertexName(), three);
        nodeMap.put(four.getVertexName(), four);
        nodeMap.put(five.getVertexName(), five);
        nodeMap.put(fine.getVertexName(), fine);

        start.addEdge("two", 5);
        start.addEdge("three", 2);

        two.addEdge("four", 4);
        two.addEdge("five", 2);

        three.addEdge("two", 8);
        three.addEdge("five", 7);

        four.addEdge("five", 6);
        four.addEdge("fine", 3);

        five.addEdge("fine", 1);

    }

    public Node getNodeByName(String name) {
        return nodeMap.get(name);
    }

    public int shortWithWeight(Node start, Node end) {
        Map<String, Integer> priceMap = new HashMap<>();
        Map<String, Node> patternMap = new HashMap<>();
        Set<String> alreadyProcess = new HashSet<>();

        for (Edge edge : start.getEdges()) {
            Node destinationNode = nodeMap.get(edge.getEndVertex());
            priceMap.put(destinationNode.getVertexName(), edge.getWeight());
            // priceMap.put(end.getVertexName(), Integer.MAX_VALUE);
            patternMap.put(destinationNode.getVertexName(), start);
        }

        var min = getMin(priceMap, alreadyProcess);

        while (min != null) {
            Node neighbor = getNodeByName(min.getKey());
            Integer beforePrice = priceMap.get(neighbor.getVertexName());

            for (Edge edge : neighbor.getEdges()) {
                String endVertex = edge.getEndVertex();
                Node endNode = getNodeByName(endVertex);
                int total = edge.getWeight() + beforePrice;
                // 有更有效的路径就更新
                if (total < priceMap.getOrDefault(endNode.getVertexName(), Integer.MAX_VALUE)) {
                    priceMap.put(endVertex, total);
                    patternMap.put(end.getVertexName(), neighbor);
                }
            }
            alreadyProcess.add(neighbor.getVertexName());

            min = getMin(priceMap, alreadyProcess);
        }

        return priceMap.getOrDefault(end.getVertexName(), -2222);
    }

    private Map.Entry<String, Integer> getMin(Map<String, Integer> priceMap, Set<String> alreadyProcess) {
        return priceMap.entrySet()
                .stream()
                .filter(it -> !alreadyProcess.contains(it.getKey()))
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .orElse(null);
    }


    public static void main(String[] args) {
        WeightGraph weightGraph = new WeightGraph();

        Node s = weightGraph.getNodeByName("one");
        Node fine = weightGraph.getNodeByName("fine");
        System.out.println(weightGraph.shortWithWeight(s, fine));

        Node two = weightGraph.getNodeByName("two");
        System.out.println(weightGraph.shortWithWeight(s, two));

        Node five = weightGraph.getNodeByName("five");
        System.out.println(weightGraph.shortWithWeight(s, five));

        Node four = weightGraph.getNodeByName("four");
        System.out.println(weightGraph.shortWithWeight(s, four));

        System.out.println(weightGraph.shortWithWeight(five, fine));
        System.out.println(weightGraph.shortWithWeight(two, fine));
        System.out.println(weightGraph.shortWithWeight(fine, s));
    }

}
