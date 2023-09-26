package core.libs;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static java.lang.System.out;

public class _Math {
    public static int random(int min, int max) {
        return rnd(min, max);
    }

    /**
     * Метод получения псевдослучайного целого числа от min до max (включая max);
     */
    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static <T> List<T> generateArray(int size, Callable<T> cb) throws Exception {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(cb.call());
        }
        return list;
    }
}
