package org.project.utils.base;

import static java.util.Collections.enumeration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;

import static org.project.utils.Helper.last;
import static org.project.utils.base.HashMap.newTreeMap;
import static org.project.utils.base.HashMap.newTreeSet;
import static org.project.utils.base.HashMap.sortByK;
import static org.project.utils.fs.File.path;
import static org.project.utils.fs.Reader.pathList;
import static org.project.utils.stream.InputStream.input;

public class Properties extends java.util.Properties {
    protected static final Map<String, Map<Object, Object>> propsMap = new HashMap<>();
    protected static Map<Object, Object> sortedProps = new HashMap<>();
    protected static final Properties props = new Properties();
    protected static String propsDir = "src/main/resources/";
    protected static String kPrefix = "props.";
    protected Map<Object, Object> map = newTreeMap();

    public static Map<String, Map<Object, Object>> getPropsMap() {
        return propsMap;
    }

    public static Map<Object, Object> getSortedProps() {
        return sortedProps;
    }

    public static Properties getProps() {
        return props;
    }

    public static Properties setProps() {
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
            Properties props = loadProps(loadDir, classpath);
            prop = System.setProperty(key, "classpath:" + classpath);
            propsMap.put(key, props.sortedProps());
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return prop;
    }

    public static Properties loadProps(String classpath) throws IOException {
        return loadProps(propsDir, classpath);
    }

    public static Properties loadProps(String loadDir, String classpath) throws IOException {
        Properties _props = loadPropsFile(path(loadDir, classpath));
        mergeProps(props, _props);
        return _props;
    }

    public static Properties loadPropsFile(String path) throws IOException {
        Properties props = new Properties();
        props.load(input(path));
        return props;
    }

    public static Properties mergeProps(Properties props1, Properties props2) {
        props1.putAll(props2);
        return props1;
    }

    public static String propsName(String classpath) {
        return classpath.replace(".properties", "");
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

    public void putAll(Properties p) {
        super.putAll(p);
        map(p.map());
    }

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        map.forEach(action);
    }

    public Map<Object, Object> map() {
        return map;
    }

    protected void map(Map<Object, Object> m) {
        map.putAll(m);
    }

    public Map<Object, Object> sortedProps() throws ReflectiveOperationException {
        return sortByK(new HashMap<>(this));
    }

}
