package birdsnail.example.flow;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.SubmissionPublisher;

class MySubscribeTest {

    @Test
    void publisherTest() {
        List<String> items = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new MySubscribe<>());

        items.forEach(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publisher.submit(s);
        });
        publisher.close();
    }

    @Test
    void stringJoinTest() {
        StringJoiner joiner = new StringJoiner(";");
        System.out.println(joiner);

        List<String> items = List.of("1-1", "2-43", "3-4", "4-2", "5-19", "5-64", "7-8", "8-34", "9-29", "3-87");
        items.stream()
                .map(it -> {
                    String[] split = it.split("-");
                    return new Pair(split[0], split[1]);
                })
                .sorted(Comparator.comparing(Pair::getOne, Comparator.reverseOrder())
                        .thenComparing(Pair::getTwo, Comparator.reverseOrder()))
                .forEach(System.out::println);
    }


}