package org.project.utils.base;

import static java.lang.System.setProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.last;
import static org.project.utils.Helper.notNull;
import static org.project.utils.base.HashMap.keys;
import static org.project.utils.fs.File.path;
import static org.project.utils.fs.Reader.pathList;
import static org.project.utils.reflection.Reflection.getGenericClass;
import static org.project.utils.stream.InputStream.input;

public class Register<K, V> {
    protected static final Map<Class<?>, Register<?, ?>> registerMap = new HashMap<>();
    protected final Map<K, V> register = new HashMap<>();
    protected static final Map<String, Map<Object, Object>> propsMap = new HashMap<>();
    protected static Map<Object, Object> sortedProps = new HashMap<>();
    protected static final SortedProperties props = new SortedProperties();
    protected static String propsDir = "src/main/resources/";
    protected static String kPrefix = "props.";

    protected V register(K key, V value) {
        register.put(key, value);
        return value;
    }

    public Map<K, V> register() {
        return register;
    }

    public V register(K key) {
        return register.get(key);
    }

    public void printRegister() {
        debug(register);
    }

    protected static <K, V> Register<K, V> registerMap(Class<?> clazz, K key, V value) throws ClassNotFoundException {
        debug("registerMap");
        Class<?> t1 = getGenericClass();
        debug("Generic#1: " + t1);
        Class<?> t2 = t1;
        try {
            t2 = getGenericClass(1);
        } catch (Exception ignored) {}
        debug("Generic#2: " + t2);
        Register<K, V> registerValue = registerMap(clazz);
        Register<K, V> register = notNull(registerValue) ? registerValue : new Register<>();
        register.register(key, value);
        registerMap.put(clazz, register);
        return register;
    }

    public static Map<Class<?>, Register<?, ?>> registerMap() {
        return registerMap;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Register<K, V> registerMap(Class<?> key) {
        return (Register<K, V>) registerMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> V register(Class<?> clazz, K key) throws ClassNotFoundException {
        return (V) registerMap(clazz).register(key);
    }

    public static <K, V> V getRegister(K key) throws ClassNotFoundException {
        debug("registerMap");
        Class<?> t1 = getGenericClass();
        debug("Generic#1: " + t1);
        Class<?> t2 = t1;
        try {
            t2 = getGenericClass(1);
            debug("Generic#2: " + t2);
            return register(t2, key);
        } catch (Exception e) {
            debug(e.toString());
        }
        return null;
    }

    public static Map<String, Map<Object, Object>> getPropsMap() {
        return propsMap;
    }

    public static Map<Object, Object> getSortedProps() {
        return sortedProps;
    }

    public static SortedProperties getProps() {
        return props;
    }

    public static SortedProperties setProps() {
        if (props.isEmpty()) {
            try {
                pathList(propsDir).forEach(r -> {
                    String file = r.getFileName().toString();
                    String name = propsName(file);
                    String last = last(name, "\\.");
                    String key = kPrefix + last;
                    setProp(key, file);
                });
                //sortedProps.putAll(props.sortedProps());
                sortedProps = props.sortedProps();
            } catch (IOException | ReflectiveOperationException e) {
                e.printStackTrace();
                //throw new RuntimeException(e);
            }
        }
        return props;
    }

    public static String getPropsDir(String path) {
        return propsDir;
    }

    public static String setPropsDir(String path) {
        return propsDir = path;
    }

    public static String setProp(String key, String classpath) {
        return setProp(propsDir, key, classpath);
    }

    public static String setProp(String loadDir, String key, String classpath) {
        String prop = "";
        try {
            SortedProperties props = loadProps(loadDir, classpath);
            prop = setProperty(key, "classpath:" + classpath);
            propsMap.put(key, props.sortedProps());
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return prop;
    }

    public static SortedProperties loadProps(String classpath) throws IOException {
        return loadProps(propsDir, classpath);
    }

    public static SortedProperties loadProps(String loadDir, String classpath) throws IOException {
        SortedProperties _props = loadPropsFile(path(loadDir, classpath));
        mergeProps(props, _props);
        return _props;
    }

    public static SortedProperties loadPropsFile(String path) throws IOException {
        SortedProperties props = new SortedProperties();
        props.load(input(path));
        return props;
    }

    public static SortedProperties mergeProps(SortedProperties props1, SortedProperties props2) {
        props1.putAll(props2);
        return props1;
    }

    public static String propsName(String classpath) {
        return classpath.replace(".properties", "");
    }

    public static void printRegisterMap() throws ReflectiveOperationException {
        debug(registerMap);
        for (Class<?> key : keys(registerMap, Class<?>[]::new)) {
            registerMap(key).printRegister();
        }
    }

}
