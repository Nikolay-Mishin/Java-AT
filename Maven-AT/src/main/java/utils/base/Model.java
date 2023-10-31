package utils.base;

import org.json.JSONObject;
import utils.exceptions.AssertException;
import utils.fs.JsonParser;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;
import static utils.Helper.isInstance;
import static utils.Helper.toLowerCaseFirst;
import static utils.Reflection.*;

public class Model<T> {

    private T model;
    private Object builder;
    private final JSONObject jsonData;
    private JSONObject obj;
    private String[] keys;

    public Model(Class<T> clazz, List<List<String>> dataTable, HashMap<Integer, Class<?>> hashMap)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        jsonData = new JsonParser().path("src/test/resources/jsonSchema/" + getClassSimpleName(clazz) + ".json");
        setModel(clazz, dataTable, hashMap);
    }

    public T get() {
        return model;
    }

    private T setModel(Class<?> clazz, List<String> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return setModel(clazz, dataTable, null);
    }

    private void setData(String key, boolean isList) {
        obj = isList ? jsonData.getJSONArray(key).getJSONObject(0) : jsonData.getJSONObject(key);
        keys = obj.keySet().toArray(String[]::new);
        out.println(obj);
        out.println(Arrays.toString(keys));
    }

    private T setModel(Class<?> clazz, List<?> dataTable, HashMap<Integer, Class<?>> hashMap)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        out.println("_getModel");

        builder = invoke(clazz, "builder");

        out.println(clazz);
        out.println(builder);
        out.println(dataTable);
        out.println(hashMap);

        List<PropertyDescriptor> descList = getPropDescriptors(clazz);

        for (int i = 0; i < dataTable.size(); i++) {
            out.println("parseRow");

            boolean isTable = dataTable.get(i) instanceof List;
            Class<?> hashEl = hashMap != null ? hashMap.get(i) : null;
            out.println(hashEl);

            List<String> row = (List<String>) (isTable ? dataTable.get(i) : dataTable);
            String name = isTable ? row.get(0) : keys[i];
            out.println(name);

            new AssertException(name).notNull();

            PropertyDescriptor desc = getPropDescriptor(descList, name);
            new AssertException(desc).notNull();

            Class<?> type = desc.getPropertyType();
            boolean isList = type == List.class;
            out.println(type);

            if (isTable) {
                row.remove(0);
                if (hashEl != null) setData(name, isList);
            }

            row = row.stream().filter(Objects::nonNull).toList();
            out.println(row);
            out.println(row.get(0));

            Object value = invokeParse(type, isList || hashEl != null ? row : row.get(isTable ? 0 : i));

            out.println(value);
            out.println(value.getClass());
            out.println(getClassSimpleName(value));

            builder = hashEl == null ? invoke(builder, name, value) : setModel(hashEl, row);
        }

        return model = invoke(builder, "build");
    }

}
