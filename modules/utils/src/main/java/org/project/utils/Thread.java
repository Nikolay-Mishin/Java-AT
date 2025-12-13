package org.project.utils;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.project.utils.function.FunctionWithExceptions;
import org.project.utils.function.SupplierWithExceptions;

/**
 *
 */
public class Thread {

    /**
     *
     * @return ExecutorService
     */
    public static ExecutorService executor() {
        return newCachedThreadPool();
    }

    /**
     *
     * @return ExecutorService
     */
    public static ExecutorService executorScheduled() {
        return newSingleThreadScheduledExecutor();
    }

    /**
     *
     * @param cb SupplierWithExceptions
     * @return T
     * @param <T> T
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, E extends Exception> T setTimeout(SupplierWithExceptions<T, E> cb) throws InterruptedException, E {
        return setTimeout(cb, o -> o);
    }

    /**
     *
     * @param sleep long
     * @param cb SupplierWithExceptions
     * @return T
     * @param <T> T
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, E extends Exception> T setTimeout(long sleep, SupplierWithExceptions<T, E> cb) throws InterruptedException, E {
        return setTimeout(sleep, cb, o -> o);
    }

    /**
     *
     * @param sleep long
     * @param timeout long
     * @param cb SupplierWithExceptions
     * @return T
     * @param <T> T
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, E extends Exception> T setTimeout(long sleep, long timeout, SupplierWithExceptions<T, E> cb) throws InterruptedException, E {
        return setTimeout(sleep, timeout, cb, o -> o);
    }

    /**
     *
     * @param cb SupplierWithExceptions
     * @param out FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, R, E extends Exception> R setTimeout(SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws InterruptedException, E {
        return setTimeout(1000, cb, out);
    }

    /**
     *
     * @param sleep long
     * @param cb SupplierWithExceptions
     * @param out FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, R, E extends Exception> R setTimeout(long sleep, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws InterruptedException, E {
        return setTimeout(sleep, sleep + 1000, cb, out);
    }

    /**
     *
     * @param sleep long
     * @param timeout long
     * @param cb SupplierWithExceptions
     * @param out FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, R, E extends Exception> R setTimeout(long sleep, long timeout, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws InterruptedException, E
    {
        return setTimeout(sleep, timeout, MILLISECONDS, cb, out);
    }

    /**
     *
     * @param sleep long
     * @param timeout long
     * @param unit TimeUnit
     * @param cb SupplierWithExceptions
     * @param out FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws InterruptedException throws
     * @throws E throws
     */
    public static <T, R, E extends Exception> R setTimeout(long sleep, long timeout, TimeUnit unit, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out)
        throws InterruptedException, E
    {
        Future<T> future = executor().submit(() -> { sleep(sleep); return cb.get(); });
        T result = null;
        try { result = future.get(timeout, unit); }
        catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        finally { future.cancel(true); sleep(sleep); } // may or may not desire this
        return out.apply(result);
    }

}
