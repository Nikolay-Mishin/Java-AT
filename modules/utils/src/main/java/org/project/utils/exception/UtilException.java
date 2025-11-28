package org.project.utils.exception;

import java.util.function.*;

import org.project.utils.function.*;

public final class UtilException {

    /** .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name)))); or .forEach(rethrowConsumer(ClassNameUtil::println)); */
    public static <T, E extends Exception> Consumer<T> rethrowConsumer(ConsumerWithExceptions<T, E> consumer) throws E {
        return t -> {
            try { consumer.accept(t); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }

    public static <T, U, E extends Exception> BiConsumer<T, U> rethrowBiConsumer(BiConsumerWithExceptions<T, U, E> biConsumer) throws E {
        return (t, u) -> {
            try { biConsumer.accept(t, u); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }

    /** .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName)) */
    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(FunctionWithExceptions<T, R, E> function) throws E {
        return t -> {
            try { return function.apply(t); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }

    /** rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, "UTF-8"))), */
    public static <T, E extends Exception> Supplier<T> rethrowSupplier(SupplierWithExceptions<T, E> function) throws E {
        return () -> {
            try { return function.get(); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }

    /** uncheck(() -> Class.forName("xxx")); */
    public static void uncheck(RunnableWithExceptions t) {
        try { t.run(); }
        catch (Exception exception) { throwAsUnchecked(exception); }
    }

    /** uncheck(() -> Class.forName("xxx")); */
    public static <R, E extends Exception> R uncheck(SupplierWithExceptions<R, E> supplier) {
        try { return supplier.get(); }
        catch (Exception exception) { throwAsUnchecked(exception); return null; }
    }

    /** uncheck(Class::forName, "xxx"); */
    public static <T, R, E extends Exception> R uncheck(FunctionWithExceptions<T, R, E> function, T t) {
        try { return function.apply(t); }
        catch (Exception exception) { throwAsUnchecked(exception); return null; }
    }

    @SuppressWarnings ("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E { throw (E)exception; }

    public static <T, R, E extends Exception> R tryCatch(FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatch(cb, e -> { throw new RuntimeException(e); });
    }

    public static <T, R, E extends Exception> R tryWithPrint(FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatch(cb, e -> { e.printStackTrace(); return null; });
    }

    public static <T, R, E extends Exception> R tryWithIgnore(FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatch(cb, e -> null);
    }

    public static <T, R, E extends Exception> R tryCatch(FunctionWithExceptions<T, R, E> cb, Function<Exception, R> catchCb) throws E {
        try { return cb.apply(null); }
        catch (Exception e) { return catchCb.apply(e); }
    }

    public static <S extends AutoCloseable, T, R, E extends Exception> R tryRes(S res, FunctionWithExceptions<T, R, E> cb) throws E {
        return tryRes(res, cb, e -> { throw new RuntimeException(e); });
    }

    public static <S extends AutoCloseable, T, R, E extends Exception> R tryResWithPrint(S res, FunctionWithExceptions<T, R, E> cb) throws E {
        return tryRes(res, cb, e -> { e.printStackTrace(); return null; });
    }

    public static <S extends AutoCloseable, T, R, E extends Exception> R tryResWithIgnore(S res, FunctionWithExceptions<T, R, E> cb) throws E {
        return tryRes(res, cb, e -> null);
    }

    public static <S extends AutoCloseable, T, R, E extends Exception> R tryRes(S res, FunctionWithExceptions<T, R, E> cb, Function<Exception, R> catchCb) throws E {
        try (res) { return cb.apply(null); }
        catch (Exception e) { return catchCb.apply(e); }
    }

    public static <T, E extends Exception> void tryConsumer(ConsumerWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> { throw new RuntimeException(e); });
    }

    public static <T, E extends Exception> void tryConsumerWithPrint(ConsumerWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> { e.printStackTrace(); });
    }

    public static <T, E extends Exception> void tryConsumerWithIgnore(ConsumerWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> {});
    }

    public static <T, E extends Exception> void tryConsumer(ConsumerWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try { cb.accept(null); }
        catch (Exception e) { catchCb.accept(e); }
    }

    public static <T, E extends Exception> void tryConsumer(SupplierWithExceptions<T, E> cb) throws E {
        trySupplier(cb, e -> { throw new RuntimeException(e); });
    }

    public static <T, E extends Exception> void trySupplier(SupplierWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try { cb.get(); }
        catch (Exception e) { catchCb.accept(e); }
    }

    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumer(S res, ConsumerWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> { throw new RuntimeException(e); });
    }

    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumerWithPrint(S res, ConsumerWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> { e.printStackTrace(); });
    }

    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumerWithIgnore(S res, ConsumerWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> {});
    }

    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumer(S res, ConsumerWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try (res) { cb.accept(null); }
        catch (Exception e) { catchCb.accept(e); }
    }
}
