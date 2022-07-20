package birdsnail.example.flow;

public class Pair {
    int one;
    int two;

    public Pair(String one, String two) {
        this.one = Integer.parseInt(one);
        this.two = Integer.parseInt(two);
    }

    public Pair(int one, int two) {
        this.one = one;
        this.two = two;
    }

    public int getOne() {
        return one;
    }

    public int getTwo() {
        return two;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "one=" + one +
                ", two=" + two +
                '}';
    }

    public static Pair of(int one, int two) {
        return new Pair(one, two);
    }

}