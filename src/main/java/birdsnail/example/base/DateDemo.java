package birdsnail.example.base;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateDemo {

    public static void main(String[] args) {
        System.out.println(betweenYear("2024-09-04"));
        System.out.println(betweenYear("2023-09-04"));
        System.out.println(betweenYear("2023-09-03"));
        System.out.println(betweenYear("2023-09-05"));
        System.out.println("======");
        System.out.println(betweenYear2("2024-09-04"));
        System.out.println(betweenYear2("2023-09-04"));
        System.out.println(betweenYear2("2023-09-03"));
        System.out.println(betweenYear2("2023-09-05"));
        System.out.println("======");
        System.out.println(betweenYear3("2024-09-04"));
        System.out.println(betweenYear3("2023-09-04"));
        System.out.println(betweenYear3("2023-09-03"));
        System.out.println(betweenYear3("2023-09-05"));
    }

    public static int betweenYear(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();

        if (now.getMonthValue() == localDate.getMonthValue() && now.getDayOfMonth() == localDate.getDayOfMonth()) {
            return now.getYear() - localDate.getYear();
        }

        long years = ChronoUnit.YEARS.between(localDate, now);
        return (int) years + 1;
    }

    public static int betweenYear2(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();

        return betweenYear2(localDate, now, 0);
    }

    private static int betweenYear2(LocalDate start, LocalDate end, int gap) {
        if (!start.isBefore(end)) {
            return gap;
        }
        return betweenYear2(start.plusYears(1), end, gap + 1);
    }

    private static int betweenYear3(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();

        int gap = now.getYear() - localDate.getYear();
        LocalDate base = LocalDate.of(now.getYear(), localDate.getMonth(), localDate.getDayOfMonth());
        if (base.isBefore(now)) {
            gap++;
        }
        return gap;
    }

}
