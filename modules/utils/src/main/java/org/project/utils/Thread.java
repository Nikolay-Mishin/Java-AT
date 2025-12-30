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

import static org.project.utils.config.Config.config;

import org.project.utils.config.BaseConfig;
import org.project.utils.function.FunctionWithExceptions;
import org.project.utils.function.SupplierWithExceptions;

/**
 *
 */
public class Thread {
    /**
     *
     */
    protected static BaseConfig c = config();
    /**
     *
     */
    protected static long sleep = c.getSleep();
    /**
     *
     */
    protected static long timeout = c.getTimeout();

    /**
     *
     * @return long
     */
    public static long getSleep() {
        return sleep;
    }

    /**
     *
     * @return long
     */
    public static long timeout() {
        return timeout;
    }

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
    public static <T, E extends Exception> T setTimeout(SupplierWithExceptions<T, E> cb) throws E {
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
    public static <T, E extends Exception> T setTimeout(long sleep, SupplierWithExceptions<T, E> cb) throws E {
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
    public static <T, E extends Exception> T setTimeout(long sleep, long timeout, SupplierWithExceptions<T, E> cb) throws E {
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
    public static <T, R, E extends Exception> R setTimeout(SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws E {
        return setTimeout(sleep, cb, out);
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
    public static <T, R, E extends Exception> R setTimeout(long sleep, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws E {
        return setTimeout(sleep, timeout, cb, out);
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
    public static <T, R, E extends Exception> R setTimeout(long sleep, long timeout, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws E
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
        throws E
    {
        Future<T> future = executor().submit(() -> { T v = cb.get(); sleep(sleep); return v; });
        T result = null;
        try { result = future.get(timeout, unit); }
        catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        finally { future.cancel(true); } // may or may not desire this
        return out.apply(result);
    }

}
