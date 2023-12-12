package birdsnail.example.base;

import birdsnail.example.entity.Person;

import java.util.Comparator;
import java.util.function.Supplier;

/**
 * 实现两个元素的自定义比较
 */
public class CompareTest {


    public static <T> int compare(T o, T n, Supplier<Comparator<T>> comparatorSupplier) {
        return comparatorSupplier.get().compare(o, n);
    }

    public static void main(String[] args) {
        System.out.println(
                compare(5, 8, () -> Integer::compareTo));
        System.out.println(
                compare("fsadf", "rewrq", () -> String::compareTo));

        Person p1 = new Person();
        p1.setAge(10);
        p1.setFullName("fsdaf");
        Person p2 = new Person();
        p2.setAge(18);
        p2.setFullName("kkk");
        System.out.println(
                compare(p1, p2, () -> Comparator.comparingInt(Person::getAge))); // 年龄比较
        System.out.println(
                compare(p1, p2, () -> Comparator.comparing(Person::getFullName))); // name比较
        System.out.println(
                compare(p1, p2, () -> Comparator.comparingInt(Person::getAge).reversed())); // 年龄反序比较
    }


}
