package org.project.utils.base;

import static java.lang.System.out;
import static java.util.Collections.enumeration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.project.utils.Helper.last;
import static org.project.utils.base.HashMap.newTreeMap;
import static org.project.utils.base.HashMap.newTreeSet;
import static org.project.utils.base.HashMap.sortByK;
import static org.project.utils.exception.UtilException.tryResWithPrint;
import static org.project.utils.fs.File.path;
import static org.project.utils.fs.Reader.getRes;
import static org.project.utils.fs.Reader.rootPathList;
import static org.project.utils.stream.InputStream.input;

import org.project.utils.function.FunctionWithExceptions;

public class Properties extends java.util.Properties {
    protected static final Map<String, Map<Object, Object>> propsMap = new HashMap<>();
    protected static final Properties props = new Properties();
    protected static boolean setBaseProps = true;
    protected static String basePropsPrefix = "org.project.utils.";
    protected static List<String> baseProps = List.of("dev", "test", "web", "win");
    protected static String propsDir = "src/main/resources/";
    protected static String kPrefix = "props.";
    protected static String kBasePrefixName = "utils.";
    protected static String kBasePrefix = kPrefix + kBasePrefixName;
    protected static String propsExt = ".properties";
    protected final Map<Object, Object> map = newTreeMap();

    public static Map<String, Map<Object, Object>> propsMap() {
        return propsMap;
    }

    public static Properties props() {
        return props;
    }

    public static boolean isSetBaseProps() {
        return setBaseProps;
    }

    public static boolean setBaseProps(boolean setBaseProps) {
        return Properties.setBaseProps = setBaseProps;
    }

    public static String basePropsPrefix() {
        return basePropsPrefix;
    }

    public static String basePropsPrefix(String prefix) {
        return basePropsPrefix = prefix;
    }

    public static String propsDir() {
        return propsDir;
    }

    public static String propsDir(String path) {
        return propsDir = path;
    }

    public static List<String> baseProps() {
        return baseProps;
    }

    public static List<String> baseProps(List<String> props) {
        return baseProps = props;
    }

    public static List<String> baseProps(String... props) {
        return baseProps(List.of(props));
    }

    public static String kPrefix() {
        return kPrefix;
    }

    public static String kPrefix(String prefix) {
        return kPrefix = prefix;
    }

    public static String kBasePrefixName() {
        return kBasePrefixName;
    }

    public static String kBasePrefixName(String prefix) {
        return kBasePrefixName = prefix;
    }

    public static String kBasePrefix() {
        return kBasePrefix;
    }

    public static String kBasePrefix(String prefix) {
        return kBasePrefix = prefix;
    }

    public static String propsExt() {
        return propsExt;
    }

    public static String propsExt(String ext) {
        return propsExt = ext;
    }

    public static Properties setBaseProps() {
        if (props.isEmpty()) {
            out.println("baseProps: " + baseProps.stream().map(f -> basePropsPrefix + f + propsExt).toList());
            String _kPrefix = kPrefix;
            kPrefix(kBasePrefix);
            setProps(baseProps, f -> basePropsPrefix + f + propsExt, Properties::loadPropsClasspath);
            kPrefix(_kPrefix);
            return props;
        }
        return null;
    }

    public static Properties setProps() {
        return setProps(propsDir);
    }

    public static Properties setProps(String loadDir) {
        if (setBaseProps) setBaseProps();
        try {
            List<Path> files = rootPathList(propsDir).toList();
            out.println("read: " + files);
            setProps(files, f -> f.getFileName().toString(), cp -> loadProps(loadDir, cp));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static <T> Properties setProps(List<T> files, Function<T, String> cb, Function<String, Properties> setProp) {
        files.forEach(f -> setProp(cb.apply(f), setProp));
        return props;
    }

    public static String setProp(String file, Function<String, Properties> props) {
        String name = propsName(file);
        String last = last(name, "\\.");
        String key = kPrefix + last;
        return setProp(props.apply(file), key, file);
    }

    public static String setProp(Function<String, Properties> props, String key, String classpath) {
        return setProp(props.apply(classpath), key, classpath);
    }

    public static String setProp(Properties props, String key, String classpath) {
        String prop = System.setProperty(key, "classpath:" + classpath);
        propsMap.put(key, props);
        //kBasePrefixName
        return prop;
    }

    public static Properties loadProps(String classpath) {
        return loadProps(propsDir, classpath);
    }

    public static Properties loadProps(String loadDir, String classpath) {
        Properties _props = loadPropsFile(path(loadDir, classpath));
        mergeProps(props, _props);
        return _props;
    }

    public static Properties loadPropsFile(String path) {
        return loadPropsFile(props -> props.loadPath(path));
    }

    public static <R extends Properties, E extends IOException> R loadPropsFile(FunctionWithExceptions<Properties, R, E> load) {
        Properties props = new Properties();
        try { load.apply(props); }
        catch (IOException e) { e.printStackTrace(); }
        return (R) props;
    }

    public static Properties mergeProps(Properties props1, Properties props2) {
        props1.putAll(props2);
        return props1;
    }

    public static String propsName(String classpath) {
        return classpath.replace(propsExt, "");
    }

    public static Properties loadPropsClasspath(String classpath) {
        return loadProps(Properties.class, classpath, true);
    }

    public static Properties loadProps(Class<?> clazz, String classpath) {
        return loadProps(clazz, classpath, true);
    }

    /**
     * В случае загрузки ресурсов из текущего класса путь указывается относительно класса.
     * <p>Для загрузки по абсолютным путям от корня classpath используется начальный слеш:
     * <p>Например, {@code InputStream input = ClassName.class.getResourceAsStream("/com/example/file.properties")}.
     * @param clazz
     * @param classpath
     */
    public static Properties loadProps(Class<?> clazz, String classpath, boolean absolute) {
        return loadPropsFile(props -> tryResWithPrint(getRes(clazz, classpath, absolute), props::loadProps));
    }

    public Map<Object, Object> map() {
        return map;
    }

    public void map(Map<Object, Object> m) {
        map.putAll(m);
    }

    /*
     * Using comparator to avoid the following exception on jdk >=9:
     * java.lang.ClassCastException: java.base/java.util.concurrent.ConcurrentHashMap$MapEntry cannot be cast to java.base/java.lang.Comparable
     */
    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return newTreeSet(super.entrySet());
    }

    @Override
    public Set<Object> keySet() {
        return new TreeSet<>(super.keySet());
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return enumeration(new TreeSet<>(super.keySet()));
    }

    @Override
    public void load(InputStream in) throws IOException {
        super.load(in);
        map(newTreeMap(this));
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        super.store(out, comments);
    }

    @Override
    public synchronized String toString() {
        try {
            return sortedProps().toString();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties loadProps(InputStream in) throws IOException {
        load(in);
        return props;
    }

    public Properties putAll(Properties p) {
        super.putAll(p);
        map(p.map());
        return props;
    }

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        map.forEach(action);
    }

    public Properties loadPath(Path path) throws IOException {
        return loadPath(path.toString());
    }

    public Properties loadPath(String path) throws IOException {
        load(input(path));
        return props;
    }

    public Properties load(String classpath) {
        return loadProps(this.getClass(), classpath);
    }

    public Map<Object, Object> sortedProps() throws ReflectiveOperationException {
        return sortByK(new HashMap<>(this));
    }

}
