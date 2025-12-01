package org.project.utils.base;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.HashMap;

import org.json.JSONObject;

import static org.project.utils.Helper.*;
import static org.project.utils.base.HashMap.keys;
import static org.project.utils.json.JsonSchema.parsePath;
import static org.project.utils.reflection.Reflection.*;

import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.exception.AssertException;
import org.project.utils.json.JsonSchema;

public class Model<T> {

    private T model;
    private Object builder;
    private String[] pathList;
    private JSONObject jsonData;
    private String[] keys;

    public Model() {
        builder = null;
        jsonData = null;
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, hashMap, method, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, null, method, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, hashMap, null, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, null, null, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable)
        throws ReflectiveOperationException, IOException {
        _model(clazz, dataTable, null, null);
    }

    public T get() {
        return model;
    }

    private void _model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws ReflectiveOperationException, IOException {
            jsonData = method != null ? getJsonData(method, clazz, jsonSchemaPathList) : getJsonData(clazz, jsonSchemaPathList);
            builder = builder(clazz);
            model(clazz, dataTable, hashMap);
    }

    private JSONObject getJsonData(METHOD_LOWER_CASE method, Class<T> clazz, Object... jsonSchemaPathList) throws IOException {
        return new JsonSchema().path(method, clazz, jsonSchemaPathList).data();
    }

    private JSONObject getJsonData(Class<T> clazz, Object... jsonSchemaPathList) throws IOException {
        return new JsonSchema().path(clazz, jsonSchemaPathList).data();
    }

    private T model(Class<T> clazz, List<String> dataTable) throws ReflectiveOperationException {
        return model(clazz, dataTable, null);
    }

    private Object builder(Class<T> clazz) throws ReflectiveOperationException {
        return invoke(clazz, "builder");
    }

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

    @SuppressWarnings("unchecked")
    private T model(Class<T> clazz, List<?> dataTable, HashMap<String, Class<?>> hashMap)
        throws ReflectiveOperationException {
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
