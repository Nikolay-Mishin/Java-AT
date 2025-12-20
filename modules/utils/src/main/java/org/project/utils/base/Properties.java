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

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.last;
import static org.project.utils.Helper.notNull;
import static org.project.utils.base.HashMap.newTreeMap;
import static org.project.utils.base.HashMap.newTreeSet;
import static org.project.utils.base.HashMap.sortByK;
import static org.project.utils.exception.UtilException.tryResWithPrint;
import static org.project.utils.fs.File.path;
import static org.project.utils.fs.Reader.getRes;
import static org.project.utils.fs.Reader.rootPathList;
import static org.project.utils.stream.InputStream.input;

import org.project.utils.function.FunctionWithExceptions;

/**
 *
 */
public class Properties extends java.util.Properties {
    /**
     *
     */
    protected static final Map<String, Map<Object, Object>> propsMap = new HashMap<>();
    /**
     *
     */
    protected static final Properties props = new Properties();
    /**
     *
     */
    protected static boolean loadEnv = true;
    /**
     *
     */
    protected static boolean setBaseProps = true;
    /**
     *
     */
    protected static String envFile = ".env";
    /**
     *
     */
    protected static List<String> baseProps = List.of("dev", "test", "web", "win");
    /**
     *
     */
    protected static String propsDir = "src/main/resources/";
    /**
     *
     */
    protected static String kPrefix = "props.";
    /**
     *
     */
    protected static String kBasePrefixName = "utils.";
    /**
     *
     */
    protected static String basePropsPrefix = "org.project." + kBasePrefixName;
    /**
     *
     */
    protected static String kBasePrefix = kPrefix + kBasePrefixName;
    /**
     *
     */
    protected static String propsExt = ".properties";
    /**
     *
     */
    protected final Map<Object, Object> map = newTreeMap();

    /**
     *
     * @return Map {String, Map {Object, Object}}
     */
    public static Map<String, Map<Object, Object>> propsMap() {
        return propsMap;
    }

    /**
     *
     * @param key String
     * @return Map {Object, Object}
     */
    public static Map<Object, Object> propsMap(String key) {
        return propsMap.get(key);
    }

    /**
     *
     * @return Properties
     */
    public static Properties props() {
        return props;
    }

    /**
     *
     * @return Map {Object, Object}
     * @throws ReflectiveOperationException throws
     */
    public static Map<Object, Object> getSortedProps() throws ReflectiveOperationException {
        return props.sortedProps();
    }

    /**
     *
     * @return Set {String}
     */
    public static Set<String> propsMapKeys() {
        return propsMap.keySet();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isLoadEnv() {
        return loadEnv;
    }

    /**
     *
     * @param load boolean
     * @return boolean
     */
    public static boolean loadEnv(boolean load) {
        return loadEnv = load;
    }

    /**
     *
     * @return boolean
     */
    public static boolean isSetBaseProps() {
        return setBaseProps;
    }

    /**
     *
     * @param setProps boolean
     * @return boolean
     */
    public static boolean setBaseProps(boolean setProps) {
        return setBaseProps = setProps;
    }

    /**
     *
     * @return String
     */
    public static String envFile() {
        return envFile;
    }

    /**
     *
     * @param fileName String
     * @return String
     */
    public static String envFile(String fileName) {
        return envFile = fileName;
    }

    /**
     *
     * @return String
     */
    public static String propsDir() {
        return propsDir;
    }

    /**
     *
     * @param path String
     * @return String
     */
    public static String propsDir(String path) {
        return propsDir = path;
    }

    /**
     *
     * @return List {String}
     */
    public static List<String> baseProps() {
        return baseProps;
    }

    /**
     *
     * @param props List {String}
     * @return List {String}
     */
    public static List<String> baseProps(List<String> props) {
        return baseProps = props;
    }

    /**
     *
     * @param props String[]
     * @return List {String}
     */
    public static List<String> baseProps(String... props) {
        return baseProps(List.of(props));
    }

    /**
     *
     * @return String
     */
    public static String kPrefix() {
        return kPrefix;
    }

    /**
     *
     * @param prefix String
     * @return String
     */
    public static String kPrefix(String prefix) {
        return kPrefix = prefix;
    }

    /**
     *
     * @return String
     */
    public static String kBasePrefixName() {
        return kBasePrefixName;
    }

    /**
     *
     * @param prefix String
     * @return String
     */
    public static String kBasePrefixName(String prefix) {
        return kBasePrefixName = prefix;
    }

    /**
     *
     * @return String
     */
    public static String basePropsPrefix() {
        return basePropsPrefix;
    }

    /**
     *
     * @param prefix String
     * @return String
     */
    public static String basePropsPrefix(String prefix) {
        return basePropsPrefix = prefix;
    }

    /**
     *
     * @return String
     */
    public static String kBasePrefix() {
        return kBasePrefix;
    }

    /**
     *
     * @param prefix String
     * @return String
     */
    public static String kBasePrefix(String prefix) {
        return kBasePrefix = prefix;
    }

    /**
     *
     * @return String
     */
    public static String propsExt() {
        return propsExt;
    }

    /**
     *
     * @param ext String
     * @return String
     */
    public static String propsExt(String ext) {
        return propsExt = ext;
    }

    /**
     *
     * @return Properties
     */
    public static Properties loadEnv() {
        return loadEnv(envFile);
    }

    /**
     *
     * @param classpath String
     * @return Properties
     */
    public static Properties loadEnv(String classpath) {
        out.println("loadEnv: " + classpath);
        out.println("setEnv: " + setProp(loadPropsClasspath(classpath), envKey(classpath), classpath));
        return props;
    }

    /**
     *
     * @param loadDir String
     * @return Properties
     */
    public static Properties loadEnvDir(String loadDir) {
        out.println("loadEnvDir: " + loadDir);
        out.println("setEnv: " + setProp(loadProps(loadDir, envFile), envKey(), envFile));
        props.forEach((k, v) -> System.setProperty(k.toString(), v.toString()));
        return props;
    }

    /**
     *
     * @return String
     */
    public static String envKey() {
        return envKey(envFile);
    }

    /**
     *
     * @param envFile String
     * @return String
     */
    public static String envKey(String envFile) {
        return envFile.replace(".", kPrefix);
    }

    /**
     *
     * @return Properties
     */
    public static Properties setBaseProps() {
        out.println("baseProps: " + baseProps.stream().map(f -> basePropsPrefix + f + propsExt).toList());
        String _kPrefix = kPrefix;
        kPrefix(kBasePrefix);
        setProps(baseProps, f -> basePropsPrefix + f + propsExt, Properties::loadPropsClasspath);
        kPrefix(_kPrefix);
        return props;
    }

    /**
     *
     * @return Properties
     */
    public static Properties setProps() {
        return setProps(propsDir);
    }

    /**
     *
     * @param loadDir String
     * @return Properties
     */
    public static Properties setProps(String loadDir) {
        if (props.isEmpty()) {
            if (loadEnv) loadEnvDir(loadDir);
            if (setBaseProps) setBaseProps();
            try {
                List<Path> files = rootPathList(loadDir).toList();
                out.println("read: " + files);
                setProps(files, f -> f.getFileName().toString(), cp -> loadProps(loadDir, cp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }

    /**
     *
     * @param files List {T}
     * @param cb Function
     * @param setProp Function
     * @return Properties
     * @param <T> T
     */
    public static <T> Properties setProps(List<T> files, Function<T, String> cb, Function<String, Properties> setProp) {
        files.forEach(f -> setProp(cb.apply(f), setProp));
        return props;
    }

    /**
     *
     * @param file String
     * @param props Function
     * @return String
     */
    public static String setProp(String file, Function<String, Properties> props) {
        String name = propsName(file);
        String last = last(name, "\\.");
        String key = kPrefix + last;
        return _equals(file, ".env") ? null : setProp(props.apply(file), key, file);
    }

    /**
     *
     * @param props Function
     * @param key String
     * @param classpath String
     * @return String
     */
    public static String setProp(Function<String, Properties> props, String key, String classpath) {
        return setProp(props.apply(classpath), key, classpath);
    }

    /**
     *
     * @param props Properties
     * @param key String
     * @param classpath String
     * @return String
     */
    public static String setProp(Properties props, String key, String classpath) {
        System.setProperty(key, "classpath:" + classpath);
        key = propsKey(key);
        Map<Object, Object> v = propsMap.get(key);
        if (notNull(v)) props.putAll(v);
        propsMap.put(key, props);
        return System.getProperty(key);
    }

    /**
     *
     * @param classpath String
     * @return Properties
     */
    public static Properties loadProps(String classpath) {
        return loadProps(propsDir, classpath);
    }

    /**
     *
     * @param loadDir String
     * @param classpath String
     * @return Properties
     */
    public static Properties loadProps(String loadDir, String classpath) {
        Properties _props = loadPropsFile(path(loadDir, classpath));
        props.putAll(_props);
        return _props;
    }

    /**
     *
     * @param path String
     * @return Properties
     */
    public static Properties loadPropsFile(String path) {
        return loadPropsFile(props -> props.loadPath(path));
    }

    /**
     *
     * @param load FunctionWithExceptions
     * @return R
     * @param <R> R
     * @param <E> E
     */
    @SuppressWarnings("unchecked")
    public static <R extends Properties, E extends IOException> R loadPropsFile(FunctionWithExceptions<Properties, R, E> load) {
        Properties props = new Properties();
        try { load.apply(props); }
        catch (IOException e) { e.printStackTrace(); }
        return (R) props;
    }

    /**
     *
     * @param classpath String
     * @return String
     */
    public static String propsName(String classpath) {
        return classpath.replace(propsExt, "");
    }

    /**
     *
     * @param key String
     * @return String
     */
    public static String propsKey(String key) {
        return key.replace(kBasePrefixName, "");
    }

    /**
     *
     * @param classpath String
     * @return Properties
     */
    public static Properties loadPropsClasspath(String classpath) {
        return loadProps(Properties.class, classpath, true);
    }

    /**
     *
     * @param clazz Class
     * @param classpath String
     * @return Properties
     */
    public static Properties loadProps(Class<?> clazz, String classpath) {
        return loadProps(clazz, classpath, true);
    }

    /**
     * В случае загрузки ресурсов из текущего класса путь указывается относительно класса.
     * <p>Для загрузки по абсолютным путям от корня classpath используется начальный слеш:
     * <p>Например, {@code InputStream input = ClassName.class.getResourceAsStream("/com/example/file.properties")}.
     * @param clazz Class
     * @param classpath String
     * @param absolute boolean
     * @return Properties
     */
    public static Properties loadProps(Class<?> clazz, String classpath, boolean absolute) {
        return loadPropsFile(props -> tryResWithPrint(getRes(clazz, classpath, absolute), props::loadProps));
    }

    /**
     *
     * @return Map {Object, Object}
     */
    public Map<Object, Object> map() {
        return map;
    }

    /**
     *
     * @param m Map {Object, Object}
     */
    public void map(Map<Object, Object> m) {
        map.putAll(m);
    }

    /**
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

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        map.forEach(action);
    }

    /**
     *
     * @param in InputStream
     * @return Properties
     * @throws IOException throws
     */
    public Properties loadProps(InputStream in) throws IOException {
        load(in);
        return props;
    }

    /**
     *
     * @param p Properties
     * @return Properties
     */
    public Properties putAll(Properties p) {
        super.putAll(p);
        map(p.map());
        return props;
    }

    /**
     *
     * @param path Path
     * @return Properties
     * @throws IOException throws
     */
    public Properties loadPath(Path path) throws IOException {
        return loadPath(path.toString());
    }

    /**
     *
     * @param path String
     * @return Properties
     * @throws IOException throws
     */
    public Properties loadPath(String path) throws IOException {
        load(input(path));
        return props;
    }

    /**
     *
     * @param classpath String
     * @return Properties
     */
    public Properties load(String classpath) {
        return loadProps(this.getClass(), classpath);
    }

    /**
     *
     * @return Map {Object, Object}
     * @throws ReflectiveOperationException throws
     */
    public Map<Object, Object> sortedProps() throws ReflectiveOperationException {
        return sortByK(new HashMap<>(this));
    }

}
