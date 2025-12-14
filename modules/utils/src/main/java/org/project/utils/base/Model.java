package org.project.utils.base;

import java.beans.ConstructorProperties;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;

import org.json.JSONObject;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isList;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notNull;
import static org.project.utils.Helper.shift;
import static org.project.utils.base.HashMap.keys;
import static org.project.utils.json.JsonSchema.parsePath;
import static org.project.utils.reflection.Reflection.getPropDescriptor;
import static org.project.utils.reflection.Reflection.getPropDescriptors;
import static org.project.utils.reflection.Reflection.getClassSimpleName;
import static org.project.utils.reflection.Reflection.invoke;
import static org.project.utils.reflection.Reflection.invokeParse;

import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.exception.AssertException;
import org.project.utils.json.JsonSchema;

/**
 *
 * @param <T> T
 */
public class Model<T> {
    /**
     *
     */
    private T model;
    /**
     *
     */
    private Object builder;
    /**
     *
     */
    private String[] pathList;
    /**
     *
     */
    private JSONObject jsonData;
    /**
     *
     */
    private String[] keys;

    /**
     *
     */
    @ConstructorProperties({})
    public Model() {
        builder = null;
        jsonData = null;
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @param hashMap HashMap
     * @param method METHOD_LOWER_CASE
     * @param jsonSchemaPathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"clazz", "dataTable", "hashMap", "method", "jsonSchemaPathList"})
    public Model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException
    {
        _model(clazz, dataTable, hashMap, method, jsonSchemaPathList);
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @param method METHOD_LOWER_CASE
     * @param jsonSchemaPathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"clazz", "dataTable", "method", "jsonSchemaPathList"})
    public Model(Class<T> clazz, List<List<String>> dataTable, METHOD_LOWER_CASE method, Object... jsonSchemaPathList) throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, null, method, jsonSchemaPathList);
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @param hashMap HashMap
     * @param jsonSchemaPathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"clazz", "dataTable", "hashMap", "jsonSchemaPathList"})
    public Model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, Object... jsonSchemaPathList) throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, hashMap, null, jsonSchemaPathList);
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @param jsonSchemaPathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"clazz", "dataTable", "jsonSchemaPathList"})
    public Model(Class<T> clazz, List<List<String>> dataTable, Object... jsonSchemaPathList) throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, null, null, jsonSchemaPathList);
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"clazz", "dataTable"})
    public Model(Class<T> clazz, List<List<String>> dataTable) throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, null, null);
    }

    /**
     *
     * @return T
     */
    public T get() {
        return model;
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @param hashMap HashMap
     * @param method METHOD_LOWER_CASE
     * @param jsonSchemaPathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    private void _model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException {
            jsonData = method != null ? getJsonData(method, clazz, jsonSchemaPathList) : getJsonData(clazz, jsonSchemaPathList);
            builder = builder(clazz);
            model(clazz, dataTable, hashMap);
    }

    /**
     *
     * @param method METHOD_LOWER_CASE
     * @param clazz Class T
     * @param jsonSchemaPathList Object[]
     * @return JSONObject
     * @throws IOException throws
     */
    private JSONObject getJsonData(METHOD_LOWER_CASE method, Class<T> clazz, Object... jsonSchemaPathList) throws IOException {
        return new JsonSchema().path(method, clazz, jsonSchemaPathList).data();
    }

    /**
     *
     * @param clazz Class T
     * @param jsonSchemaPathList Object[]
     * @return JSONObject
     * @throws IOException throws
     */
    private JSONObject getJsonData(Class<T> clazz, Object... jsonSchemaPathList) throws IOException {
        return new JsonSchema().path(clazz, jsonSchemaPathList).data();
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List {List {String}}
     * @return T
     * @throws ReflectiveOperationException throws
     */
    private T model(Class<T> clazz, List<String> dataTable) throws ReflectiveOperationException {
        return model(clazz, dataTable, null);
    }

    /**
     *
     * @param clazz Class T
     * @return Object
     * @throws ReflectiveOperationException throws
     */
    private Object builder(Class<T> clazz) throws ReflectiveOperationException {
        return invoke(clazz, "builder");
    }

    /**
     *
     * @param key String
     * @param isList boolean
     * @throws ReflectiveOperationException throws
     */
    private void setKeys(String key, boolean isList) throws ReflectiveOperationException {
        debug("setData");
        debug(jsonData);
        if (isNull(jsonData)) {
            keys = shift(pathList);
            return;
        }
        JSONObject obj = !isList ? jsonData.getJSONObject(key) : jsonData.getJSONArray(key).getJSONObject(0);
        keys = keys(obj, String[]::new);
        debug(obj);
        debug(Arrays.toString(keys));
    }

    /**
     *
     * @param clazz Class T
     * @param dataTable List
     * @param hashMap HashMap
     * @return T
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    private T model(Class<T> clazz, List<?> dataTable, HashMap<String, Class<?>> hashMap) throws ReflectiveOperationException {
        debug("model");

        Object _builder = builder(clazz);

        debug(clazz);
        debug(_builder);
        debug(dataTable);
        debug(hashMap);

        List<PropertyDescriptor> descList = getPropDescriptors(clazz);

        for (int i = 0; i < dataTable.size(); i++) {
            debug("parseRow");

            boolean isTable = isList(dataTable.get(i));
            debug(isTable);

            List<String> row = (List<String>) (isTable ? dataTable.get(i) : dataTable);
            String rowName = row.get(0);
            pathList = parsePath(rowName);
            String name = isTable ? pathList[0] : keys[i];
            debug(name);

            new AssertException(name).notNull();

            PropertyDescriptor desc = getPropDescriptor(descList, name);
            new AssertException(desc).notNull();

            Class<?> type = desc.getPropertyType();
            boolean isList = type == List.class;
            debug(type);

            Class<T> hashEl = isNull(hashMap) ? null : (Class<T>) hashMap.get(name);
            boolean isModel = notNull(hashEl);
            debug(hashEl);

            if (isTable) {
                row.remove(0);
                if (isModel) setKeys(name, isList);
            }

            row = row.stream().filter(Objects::nonNull).toList();
            Object value = invokeParse(type, isList || isModel ? row : row.get(isTable ? 0 : i));

            if (isModel) {
                value = model(hashEl, row);
                //if (isList) value = singletonList(value);
                if (isList) value = List.of(value);
            }
            debug(value);
            getClassSimpleName(value);

            _builder = invoke(isTable ? builder : _builder, name, value);
            debug(_builder);
        }

        model = invoke(_builder, "build");
        debug(model);
        return model;
    }

}
