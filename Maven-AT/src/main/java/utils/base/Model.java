package utils.base;

import models.pet.Category;
import models.pet.Pet;
import models.pet.TagsItem;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.out;
import static utils.Helper.isParseType;
import static utils.Reflection.*;

public class Model {

    //@SafeVarargs
    public static <T extends Model> T getModel(Class<?> clazz, Object builder, List<List<String>> dataTable, HashMap<Integer, List<Object>> hashMap)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        out.println(clazz);
        out.println(builder);
        out.println(hashMap);

        List<PropertyDescriptor> descList = getPropDescriptors(clazz);

        for (int i = 0; i < dataTable.size(); i++) {
            List<String> row = dataTable.get(i);
            List<Object> hashEl = hashMap != null ? hashMap.get(i) : null;
            String name = row.get(0);
            row.remove(0);
            row = List.of(row.stream().filter(Objects::nonNull).toArray(String[]::new));
            PropertyDescriptor desc = getPropDescriptor(descList, name);
            Class<?> type = desc.getPropertyType();
            Object value = type == List.class || hashEl != null ? row : row.get(0);
            out.println(name);
            out.println(type);
            out.println(row);
            out.println(hashEl);

            if (isParseType(type)) {
                value = invokeParse(type, value);
            }

            out.println(value);
            out.println(value.getClass());
            out.println(getClassSimpleName(value));

            if (name.equals("name") || name.equals("id") || name.equals("status") || name.equals("petId") || name.equals("bool")) {
                getMethod(builder, name, value);
            }

            if (hashEl != null && (name.equals("category") || name.equals("tags"))) {
                getModel((Class<?>) hashEl.get(0), hashEl.get(1), row);
            }
        }

        T _model = (T) Pet.builder()
            .photoUrls(List.of(dataTable.get(0).get(0)))
            .name(dataTable.get(1).get(0))
            .id(parseLong(dataTable.get(2).get(0)))
            .category(Category.builder()
                .name(dataTable.get(3).get(0))
                .id(parseInt(dataTable.get(3).get(1)))
                .build())
            .tags(List.of(TagsItem.builder()
                .name(dataTable.get(4).get(0))
                .id(parseInt(dataTable.get(4).get(1)))
                .build()))
            .status(dataTable.get(5).get(0))
            .petId(parseInt(dataTable.get(6).get(0)))
            .bool(parseBoolean(dataTable.get(7).get(0)))
            .build();
        out.println(_model);
        return _model;
    }

    protected static void getModel(Class<?> clazz, Object builder, List<String> row) {
        out.println(clazz);
        out.println(builder);
        out.println(row);
    }

}
