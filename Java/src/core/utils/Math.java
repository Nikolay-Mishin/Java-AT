package core.utils;

import java.util.*;
import java.util.concurrent.Callable;

public class Math {
    public static int random(int min, int max) {
        return rnd(min, max);
    }

    /**
     * Метод получения псевдослучайного целого числа от min до max (включая max);
     */
    public static int rnd(int min, int max) {
        max -= min;
        return (int) (java.lang.Math.random() * ++max) + min;
    }

    public static <T> List<T> generateArray(int size, Callable<T> cb) throws Exception {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(cb.call());
        }
        return list;
    }
}
