package birdsnail.example.uitl;

public class ParamUtil {


    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("参数为null");
        }

    }
}
