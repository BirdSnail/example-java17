package birdsnail.example.graph;

public record Edge(String startVertex, String endVertex, int weight) {

    public String getStartVertex() {
        return startVertex;
    }

    public String getEndVertex() {
        return endVertex;
    }

    public int getWeight() {
        return weight;
    }


}
