package utils.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;
import static utils.Reflection.*;

public class Model {

    protected static Object builder;

    public static <T extends Model> T getModel(Class<?> clazz, List<List<String>> dataTable, HashMap<Integer, Class<? extends Model>> hashMap)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getModel(clazz, dataTable, hashMap);
    }

    protected static <T extends Model> void _getModel(Class<?> clazz, List<String> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        out.println(clazz);
        out.println(builder);
        out.println(dataTable);
        //return _getModel(clazz, dataTable, null);
    }

    protected static <T extends Model> T _getModel(Class<?> clazz, List<List<String>> dataTable, HashMap<Integer, Class<? extends Model>> hashMap)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        builder = invoke(clazz, "builder");
        out.println(clazz);
        out.println(builder);
        out.println(dataTable);
        out.println(hashMap);

        List<PropertyDescriptor> descList = getPropDescriptors(clazz);

        for (int i = 0; i < dataTable.size(); i++) {
            List<String> row = dataTable.get(i);
            String name = row.get(0);
            row.remove(0);
            row = List.of(row.stream().filter(Objects::nonNull).toArray(String[]::new));
            PropertyDescriptor desc = getPropDescriptor(descList, name);
            Class<?> type = desc.getPropertyType();
            Class<? extends Model> hashEl = hashMap != null ? hashMap.get(i) : null;
            Object value = invokeParse(type, type == List.class || hashEl != null ? row : row.get(0));

            out.println(name);
            out.println(type);
            out.println(row);
            out.println(hashEl);
            out.println(value);
            out.println(value.getClass());
            out.println(getClassSimpleName(value));

            if (name.equals("name") || name.equals("id") || name.equals("status") || name.equals("petId") || name.equals("bool")) {
                builder = invoke(builder, name, value);
            }

            if (hashEl != null && (name.equals("category") || name.equals("tags"))) {
                _getModel(hashEl, row);
            }
        }
        return invoke(builder, "build");
    }

}
