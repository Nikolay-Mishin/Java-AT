package utils.base;

import org.json.JSONObject;
import utils.constant.RequestConstants.METHOD_LOWER_CASE;
import utils.exception.AssertException;
import utils.json.JsonSchema;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;
import static org.project.utils.Helper.*;
import static utils.base.HashMap.keys;
import static utils.json.JsonSchema.parsePath;
import static utils.reflection.Reflection.*;

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
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        _setModel(clazz, dataTable, hashMap, method, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        _setModel(clazz, dataTable, null, method, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, Object... jsonSchemaPathList)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        _setModel(clazz, dataTable, hashMap, null, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable, Object... jsonSchemaPathList)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        _setModel(clazz, dataTable, null, null, jsonSchemaPathList);
    }

    public Model(Class<T> clazz, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        _setModel(clazz, dataTable, null, null);
    }

    public T get() {
        return model;
    }

    private void _setModel(Class<T> clazz, List<List<String>> dataTable, HashMap<String, Class<?>> hashMap, METHOD_LOWER_CASE method, Object... jsonSchemaPathList)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
            jsonData = method != null ? setJsonData(method, clazz, jsonSchemaPathList) : setJsonData(clazz, jsonSchemaPathList);
            builder = getBuilder(clazz);
            setModel(clazz, dataTable, hashMap);
    }

    private JSONObject setJsonData(METHOD_LOWER_CASE method, Class<T> clazz, Object... jsonSchemaPathList) throws IOException {
        return new JsonSchema().path(method, clazz, jsonSchemaPathList).data();
    }

    private JSONObject setJsonData(Class<T> clazz, Object... jsonSchemaPathList) throws IOException {
        return new JsonSchema().path(clazz, jsonSchemaPathList).data();
    }

    private T setModel(Class<T> clazz, List<String> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return setModel(clazz, dataTable, null);
    }

    private Object getBuilder(Class<T> clazz) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return invoke(clazz, "builder");
    }

    private void setKeys(String key, boolean isList) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        out.println("setData");
        out.println(jsonData);
        if (isNull(jsonData)) {
            keys = removeFirst(pathList);
            return;
        }
        JSONObject obj = !isList ? jsonData.getJSONObject(key) : jsonData.getJSONArray(key).getJSONObject(0);
        keys = keys(obj, String[]::new);
        out.println(obj);
        out.println(Arrays.toString(keys));
    }

    @SuppressWarnings("unchecked")
    private T setModel(Class<T> clazz, List<?> dataTable, HashMap<String, Class<?>> hashMap)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        out.println("setModel");

        Object _builder = getBuilder(clazz);

        out.println(clazz);
        out.println(_builder);
        out.println(dataTable);
        out.println(hashMap);

        List<PropertyDescriptor> descList = getPropDescriptors(clazz);

        for (int i = 0; i < dataTable.size(); i++) {
            out.println("parseRow");

            boolean isTable = isList(dataTable.get(i));
            List<String> row = (List<String>) (isTable ? dataTable.get(i) : dataTable);
            String rowName = row.get(0);
            pathList = parsePath(rowName);
            String name = isTable ? pathList[0] : keys[i];
            out.println(name);

            new AssertException(name).notNull();

            PropertyDescriptor desc = getPropDescriptor(descList, name);
            new AssertException(desc).notNull();

            Class<?> type = desc.getPropertyType();
            boolean isList = type == List.class;
            out.println(type);

            Class<T> hashEl = notNull(hashMap) ? (Class<T>) hashMap.get(name) : null;
            boolean isModel = notNull(hashEl);
            out.println(hashEl);

            if (isTable) {
                row.remove(0);
                if (isModel) setKeys(name, isList);
            }

            row = row.stream().filter(Objects::nonNull).toList();
            Object value = invokeParse(type, isList || isModel ? row : row.get(isTable ? 0 : i));

            if (isModel) {
                value = setModel(hashEl, row);
                //if (isList) value = singletonList(value);
                if (isList) value = List.of(value);
            }
            out.println(value);
            getClassSimpleName(value);

            _builder = invoke(isTable ? builder : _builder, name, value);
            out.println(_builder);
        }

        model = invoke(_builder, "build");
        out.println(model);
        return model;
    }

}
