package utils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;

import static java.lang.System.out;

public class Helper {

    public static Charset getDefaultCharset() {
        return Charset.defaultCharset();
    }

    public static void DefaultCharsetPrinter() {
        out.println(getDefaultCharset());
    }
}
