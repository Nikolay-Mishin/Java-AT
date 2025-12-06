package org.project.utils.base;

import static java.util.Collections.enumeration;
import static java.util.Comparator.comparing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import static org.project.utils.base.HashMap.sortByK;

public class SortedProperties extends Properties {

    /*
     * Using comparator to avoid the following exception on jdk >=9:
     * java.lang.ClassCastException: java.base/java.util.concurrent.ConcurrentHashMap$MapEntry cannot be cast to java.base/java.lang.Comparable
     */
    @Override
    public Set<Entry<Object, Object>> entrySet() {
        /*Set<Entry<Object, Object>> sortedSet = new TreeSet<>(new Comparator<>() {
            @Override
            public int compare(Entry<Object, Object> o1, Entry<Object, Object> o2) {
                debug("o1: " + o1);
                debug("o2: " + o2);
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
        });*/
        Set<Entry<Object, Object>> sortedSet = new TreeSet<>(comparing(o -> o.getKey().toString()));
        sortedSet.addAll(super.entrySet());
        return sortedSet;
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
        putAll(this);
        super.load(in);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        putAll(this);
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

    public void loadSorted(InputStream in) throws IOException {
        load(in);
    }

    public void storeSorted(OutputStream out, String comments) throws IOException {
        store(out, comments);
    }

    public Map<Object, Object> sortedProps() throws ReflectiveOperationException {
        return sortByK(new HashMap<>(this));
    }

}
