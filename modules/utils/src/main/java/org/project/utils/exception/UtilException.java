package org.project.utils.exception;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.project.utils.Helper._equals;

import org.project.utils.function.BiConsumerWithExceptions;
import org.project.utils.function.ConsumerWithExceptions;
import org.project.utils.function.ConsumerVoidWithExceptions;
import org.project.utils.function.FunctionNoArgsWithExceptions;
import org.project.utils.function.FunctionWithExceptions;
import org.project.utils.function.RunnableWithExceptions;
import org.project.utils.function.SupplierWithExceptions;

/**
 *
 */
public final class UtilException {

    /**
     * .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name)))); or .forEach(rethrowConsumer(ClassNameUtil::println));
     * @param consumer ConsumerWithExceptions
     * @return Consumer
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> Consumer<T> rethrowConsumer(ConsumerWithExceptions<T, E> consumer) throws E {
        return t -> {
            try { consumer.accept(t); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }

    /**
     *
     * @param biConsumer BiConsumerWithExceptions
     * @return BiConsumer
     * @param <T> T
     * @param <U> U
     * @param <E> E
     * @throws E throws
     */
    public static <T, U, E extends Exception> BiConsumer<T, U> rethrowBiConsumer(BiConsumerWithExceptions<T, U, E> biConsumer) throws E {
        return (t, u) -> {
            try { biConsumer.accept(t, u); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }

    /**
     * .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName))
     * @param function FunctionWithExceptions
     * @return Function
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(FunctionWithExceptions<T, R, E> function) throws E {
        return t -> {
            try { return function.apply(t); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }

    /**
     * rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, "UTF-8"))),
     * @param function SupplierWithExceptions
     * @return Supplier
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> Supplier<T> rethrowSupplier(SupplierWithExceptions<T, E> function) throws E {
        return () -> {
            try { return function.get(); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }

    /**
     * uncheck(() -> Class.forName("xxx"));
     * @param t RunnableWithExceptions
     */
    public static void uncheck(RunnableWithExceptions t) {
        try { t.run(); }
        catch (Exception exception) { throwAsUnchecked(exception); }
    }

    /**
     * uncheck(() -> Class.forName("xxx"));
     * @param supplier SupplierWithExceptions
     * @return R
     * @param <R> R
     * @param <E> E
     */
    public static <R, E extends Exception> R uncheck(SupplierWithExceptions<R, E> supplier) {
        try { return supplier.get(); }
        catch (Exception exception) { throwAsUnchecked(exception); return null; }
    }

    /**
     * uncheck(Class::forName, "xxx");
     * @param function FunctionWithExceptions
     * @param t T
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     */
    public static <T, R, E extends Exception> R uncheck(FunctionWithExceptions<T, R, E> function, T t) {
        try { return function.apply(t); }
        catch (Exception exception) { throwAsUnchecked(exception); return null; }
    }

    /**
     *
     * @param exception Exception
     * @param <E> E
     * @throws E throws
     */
    @SuppressWarnings ("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E { throw (E)exception; }

    /**
     *
     * @param cb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatch(FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatch(cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param cb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryWithPrint(FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatch(cb, e -> { e.printStackTrace(); return null; });
    }

    /**
     *
     * @param cb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryWithIgnore(FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatch(cb, e -> null);
    }

    /**
     *
     * @param cb FunctionWithExceptions
     * @param catchCb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatch(FunctionWithExceptions<T, R, E> cb, FunctionWithExceptions<Exception, R, E> catchCb) throws E {
        try { return cb.apply(null); }
        catch (Exception e) { return catchCb.apply(e); }
    }

    /**
     *
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatchNoArgs(FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryCatchNoArgs(cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryNoArgsWithPrint(FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryCatchNoArgs(cb, e -> { e.printStackTrace(); return null; });
    }

    /**
     *
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryNoArgsWithIgnore(FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryCatchNoArgs(cb, e -> null);
    }

    /**
     *
     * @param cb FunctionNoArgsWithExceptions
     * @param catchCb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatchNoArgs(FunctionNoArgsWithExceptions<T, R, E> cb, FunctionWithExceptions<Exception, R, E> catchCb) throws E {
        try { return cb.apply(); }
        catch (Exception e) { return catchCb.apply(e); }
    }

    /**
     *
     * @param msg String
     * @param cb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatchMsg(String msg, FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatchMsg(msg, cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param msg String
     * @param cb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryWithPrintMsg(String msg, FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatchMsg(msg, cb, e -> { e.printStackTrace(); return null; });
    }

    /**
     *
     * @param msg String
     * @param cb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryWithIgnoreMsg(String msg, FunctionWithExceptions<T, R, E> cb) throws E {
        return tryCatchMsg(msg, cb, e -> null);
    }

    /**
     *
     * @param msg String
     * @param cb FunctionWithExceptions
     * @param catchCb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatchMsg(String msg, FunctionWithExceptions<T, R, E> cb, FunctionWithExceptions<Exception, R, E> catchCb) throws E {
        try { return cb.apply(null); }
        catch (Exception e) {
            if (_equals(e.getMessage(), msg)) return catchCb.apply(e);
            else throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param msg String
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatchNoArgsMsg(String msg, FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryCatchNoArgsMsg(msg, cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param msg String
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryNoArgsWithPrintMsg(String msg, FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryCatchNoArgsMsg(msg, cb, e -> { e.printStackTrace(); return null; });
    }

    /**
     *
     * @param msg String
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryNoArgsWithIgnoreMsg(String msg, FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryCatchNoArgsMsg(msg, cb, e -> null);
    }

    /**
     *
     * @param msg String
     * @param cb FunctionNoArgsWithExceptions
     * @param catchCb FunctionWithExceptions
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <T, R, E extends Exception> R tryCatchNoArgsMsg(String msg, FunctionNoArgsWithExceptions<T, R, E> cb, FunctionWithExceptions<Exception, R, E> catchCb) throws E {
        try { return cb.apply(); }
        catch (Exception e) {
            if (_equals(e.getMessage(), msg)) return catchCb.apply(e);
            else throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param res S
     * @param cb FunctionWithExceptions
     * @return R
     * @param <S> S
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, R, E extends Exception> R tryRes(S res, FunctionWithExceptions<S, R, E> cb) throws E {
        return tryRes(res, cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param res S
     * @param cb FunctionWithExceptions
     * @return R
     * @param <S> S
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, R, E extends Exception> R tryResWithPrint(S res, FunctionWithExceptions<S, R, E> cb) throws E {
        return tryRes(res, cb, e -> { e.printStackTrace(); return null; });
    }

    /**
     *
     * @param res S
     * @param cb FunctionWithExceptions
     * @return R
     * @param <S> S
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, R, E extends Exception> R tryResWithIgnore(S res, FunctionWithExceptions<S, R, E> cb) throws E {
        return tryRes(res, cb, e -> null);
    }

    /**
     *
     * @param res S
     * @param cb FunctionWithExceptions
     * @param catchCb FunctionWithExceptions
     * @return R
     * @param <S> S
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, R, E extends Exception> R tryRes(S res, FunctionWithExceptions<S, R, E> cb, FunctionWithExceptions<Exception, R, E> catchCb) throws E {
        try (res) { return cb.apply(res); }
        catch (Exception e) { return catchCb.apply(e); }
    }

    /**
     *
     * @param res S
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <S> S
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, R, E extends Exception> R tryResNoArgs(S res, FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryResNoArgs(res, cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param res S
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <S> S
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, R, E extends Exception> R tryResNoArgsWithPrint(S res, FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryResNoArgs(res, cb, e -> { e.printStackTrace(); return null; });
    }

    /**
     *
     * @param res S
     * @param cb FunctionNoArgsWithExceptions
     * @return R
     * @param <S> S
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, R, E extends Exception> R tryResNoArgsWithIgnore(S res, FunctionNoArgsWithExceptions<T, R, E> cb) throws E {
        return tryResNoArgs(res, cb, e -> null);
    }

    /**
     *
     * @param res S
     * @param cb FunctionNoArgsWithExceptions
     * @param catchCb FunctionWithExceptions
     * @return R
     * @param <S> S
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, R, E extends Exception> R tryResNoArgs(S res, FunctionNoArgsWithExceptions<T, R, E> cb, FunctionWithExceptions<Exception, R, E> catchCb) throws E {
        try (res) { return cb.apply(); }
        catch (Exception e) { return catchCb.apply(e); }
    }

    /**
     *
     * @param cb ConsumerWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumer(ConsumerWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param cb ConsumerWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumerWithPrint(ConsumerWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> { e.printStackTrace(); });
    }

    /**
     *
     * @param cb ConsumerWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumerWithIgnore(ConsumerWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> {});
    }

    /**
     *
     * @param cb ConsumerWithExceptions
     * @param catchCb Consumer
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumer(ConsumerWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try { cb.accept(null); }
        catch (Exception e) { catchCb.accept(e); }
    }

    /**
     *
     * @param cb ConsumerVoidWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumer(ConsumerVoidWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param cb ConsumerVoidWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumerWithPrint(ConsumerVoidWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> { e.printStackTrace(); });
    }

    /**
     *
     * @param cb ConsumerVoidWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumerWithIgnore(ConsumerVoidWithExceptions<T, E> cb) throws E {
        tryConsumer(cb, e -> {});
    }

    /**
     *
     * @param cb ConsumerVoidWithExceptions
     * @param catchCb Consumer
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void tryConsumer(ConsumerVoidWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try { cb.accept(); }
        catch (Exception e) { catchCb.accept(e); }
    }

    /**
     *
     * @param cb SupplierWithExceptions
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void trySupplier(SupplierWithExceptions<T, E> cb) throws E {
        trySupplier(cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param cb SupplierWithExceptions
     * @param catchCb Consumer
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <T, E extends Exception> void trySupplier(SupplierWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try { cb.get(); }
        catch (Exception e) { catchCb.accept(e); }
    }

    /**
     *
     * @param res S
     * @param cb ConsumerWithExceptions
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumer(S res, ConsumerWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param res S
     * @param cb ConsumerWithExceptions
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumerWithPrint(S res, ConsumerWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> { e.printStackTrace(); });
    }

    /**
     *
     * @param res S
     * @param cb ConsumerWithExceptions
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumerWithIgnore(S res, ConsumerWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> {});
    }

    /**
     *
     * @param res S
     * @param cb ConsumerWithExceptions
     * @param catchCb Consumer
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumer(S res, ConsumerWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try (res) { cb.accept(null); }
        catch (Exception e) { catchCb.accept(e); }
    }

    /**
     *
     * @param res S
     * @param cb ConsumerVoidWithExceptions
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumer(S res, ConsumerVoidWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> { throw new RuntimeException(e); });
    }

    /**
     *
     * @param res S
     * @param cb ConsumerVoidWithExceptions
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumerWithPrint(S res, ConsumerVoidWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> { e.printStackTrace(); });
    }

    /**
     *
     * @param res S
     * @param cb ConsumerVoidWithExceptions
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumerWithIgnore(S res, ConsumerVoidWithExceptions<T, E> cb) throws E {
        tryResConsumer(res, cb, e -> {});
    }

    /**
     *
     * @param res S
     * @param cb ConsumerVoidWithExceptions
     * @param catchCb Consumer
     * @param <S> S
     * @param <T> T
     * @param <E> E
     * @throws E throws
     */
    public static <S extends AutoCloseable, T, E extends Exception> void tryResConsumer(S res, ConsumerVoidWithExceptions<T, E> cb, Consumer<Exception> catchCb) throws E {
        try (res) { cb.accept(); }
        catch (Exception e) { catchCb.accept(e); }
    }
}
