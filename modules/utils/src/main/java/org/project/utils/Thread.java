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

public class Thread {

    public static ExecutorService executor() {
        return newCachedThreadPool();
    }

    public static ExecutorService executorScheduled() {
        return newSingleThreadScheduledExecutor();
    }

    public static <T, E extends Exception> T setTimeout(SupplierWithExceptions<T, E> cb) throws InterruptedException, E {
        return setTimeout(cb, o -> o);
    }

    public static <T, E extends Exception> T setTimeout(long sleep, SupplierWithExceptions<T, E> cb) throws InterruptedException, E {
        return setTimeout(sleep, cb, o -> o);
    }

    public static <T, E extends Exception> T setTimeout(long sleep, long timeout, SupplierWithExceptions<T, E> cb) throws InterruptedException, E {
        return setTimeout(sleep, timeout, cb, o -> o);
    }

    public static <T, R, E extends Exception> R setTimeout(SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws InterruptedException, E {
        return setTimeout(1000, cb, out);
    }

    public static <T, R, E extends Exception> R setTimeout(long sleep, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws InterruptedException, E {
        return setTimeout(sleep, sleep + 1000, cb, out);
    }

    public static <T, R, E extends Exception> R setTimeout(long sleep, long timeout, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out) throws InterruptedException, E
    {
        return setTimeout(sleep, timeout, MILLISECONDS, cb, out);
    }

    public static <T, R, E extends Exception> R setTimeout(long sleep, long timeout, TimeUnit unit, SupplierWithExceptions<T, E> cb, FunctionWithExceptions<T, R, E> out)
        throws InterruptedException, E
    {
        Future<T> future = executor().submit(() -> { sleep(sleep); return cb.get(); });
        T result = null;
        try { result = future.get(timeout, unit); }
        catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        finally { future.cancel(true); sleep(sleep); return out.apply(result); } // may or may not desire this
    }

}
