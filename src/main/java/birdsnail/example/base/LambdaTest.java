package birdsnail.example.base;

import birdsnail.example.entity.Person;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public class LambdaTest {


//    static void load(String tag,) {
//
//    }

    private PersonConsumer getPersonConsumer(BiConsumer<PersonCollect, Person> addMethod) {
        return (tag, person) -> {
//            addMethod.accept();
        };
    }


    @FunctionalInterface
    private interface PersonConsumer {

        void accept(String tag, Person person);

    }

    static class PersonCollect {

        private LinkedList<Person> res = new LinkedList<>();

        void addFirst(Person str) {
            res.addFirst(str);
        }

        void addLast(Person str) {
            res.addLast(str);
        }

    }


}
