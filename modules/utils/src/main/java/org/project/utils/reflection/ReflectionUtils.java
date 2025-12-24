package org.project.utils.reflection;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Stack;

import static org.project.utils.Helper.notNull;
import static org.project.utils.reflection.Reflection.isExtends;
import static org.project.utils.reflection.Reflection.rawType;
import static org.project.utils.reflection.Reflection.typeArg;
import static org.project.utils.reflection.Reflection.typeBound;

/**
 * Alex Tracer (c) 2009
 */
public class ReflectionUtils {

    /**
     * Для некоторого класса определяет каким классом был параметризован один из его предков с generic-параметрами.
     *
     * @param actual   анализируемый класс
     * @param generic  класс, для которого определяется значение параметра
     * @param parameterIndex номер параметра
     * @return класс, являющийся параметром с индексом parameterIndex в genericClass
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericParameterClass(final Class<?> actual, final Class<?> generic, final int parameterIndex) {
        boolean isClass = !actual.isInterface();
        final Class<?> actualClass = isClass ? actual : generic;
        final Class<?> genericClass = isClass ? generic : actual;

        boolean isSuperclass = isExtends(actualClass.getSuperclass(), genericClass) || isExtends(actualClass, genericClass);

        // Прекращаем работу если genericClass не является предком actualClass.
        if (!isSuperclass) {
            throw new IllegalArgumentException("Class " + genericClass.getName() + " is not a superclass of " + actualClass.getName() + ".");
        }

        // Нам нужно найти класс, для которого непосредственным родителем будет genericClass.
        // Мы будем подниматься вверх по иерархии, пока не найдем интересующий нас класс.
        // В процессе поднятия мы будем сохранять в genericClasses все классы - они нам понадобятся при спуске вниз.

        // Пройденные классы - используются для спуска вниз.
        Stack<ParameterizedType> genericClasses = getGenericClasses(actualClass, genericClass, parameterIndex);

        // Нужный класс найден. Теперь мы можем узнать, какими типами он параметризован.
        Type result = genericClasses.empty() ? null : typeArg(genericClasses.pop(), parameterIndex);

        // Похоже наш параметр задан где-то ниже по иерархии, спускаемся вниз.
        while (result instanceof TypeVariable && !genericClasses.empty()) {
            // Берем соответствующий класс, содержащий метаинформацию о нашем параметре.
            // Получаем индекс параметра в том классе, в котором он задан.
            // Получаем информацию о значении параметра.
            result = typeArg(genericClasses.pop(), getParameterTypeDeclarationIndex((TypeVariable<?>) result));
        }

        if (result instanceof TypeVariable) {
            // Мы спустились до самого низа, но даже там нужный параметр не имеет явного задания.
            // Следовательно из-за "Type erasure" узнать класс для параметра невозможно.
            throw new IllegalStateException("Unable to resolve type variable " + result + "." + " Try to replace instances of parametrized class with its non-parameterized subtype.");
        }

        // Сам параметр оказался параметризованным.
        // Отбросим информацию о его параметрах, она нам не нужна.
        result = rawType(result);

        if (result == null) {
            // Should never happen. :)
            throw new IllegalStateException("Unable to determine actual parameter type for " + actualClass.getName() + ".");
        }

        if (!(result instanceof Class)) {
            // Похоже, что параметр - массив или что-то еще, что не является классом.
            throw new IllegalStateException("Actual parameter type for " + actualClass.getName() + " is not a Class.");
        }

        return (Class<T>) result;
    }

    /**
     * Пройденные классы - используются для спуска вниз.
     *
     * @param actualClass   анализируемый класс
     * @param genericClass  класс, для которого определяется значение параметра
     * @param parameterIndex номер параметра
     * @return Stack {ParameterizedType}
     */
    public static Stack<ParameterizedType> getGenericClasses(final Class<?> actualClass, final Class<?> genericClass, final int parameterIndex) {
        boolean isClass = !genericClass.isInterface();
        Stack<ParameterizedType> genericClasses = new Stack<>();

        // clazz - текущий рассматриваемый класс
        Class<?> clazz = isClass ? actualClass : genericClass;

        while (notNull(clazz)) {
            Type genericSuperclass = isClass ? clazz.getGenericSuperclass() : typeBound(clazz, parameterIndex);

            if (genericSuperclass instanceof ParameterizedType) {
                // Если предок - параметризованный класс, то запоминаем его - возможно он пригодится при спуске вниз.
                genericClasses.push((ParameterizedType) genericSuperclass);
            } else {
                // В иерархии встретился непараметризованный класс. Все ранее сохраненные параметризованные классы будут бесполезны.
                genericClasses.clear();
            }

            // Проверяем, дошли мы до нужного предка или нет.
            if (!rawTypeGeneric(rawType(genericSuperclass), genericClass)) {
                // genericClass не является непосредственным родителем для текущего класса.
                // Поднимаемся по иерархии дальше.
                clazz = clazz.getSuperclass();
            } else {
                // Мы поднялись до нужного класса. Останавливаемся.
                break;
            }
        }

        return genericClasses;
    }

    /**
     *
     * @param rawType       Type
     * @param genericClass  класс, для которого определяется значение параметра
     * @return boolean
     */
    public static boolean rawTypeGeneric(final Type rawType, final Class<?> genericClass) {
        return notNull(rawType) && rawType.equals(genericClass);
    }

    /**
     *
     * @param typeVariable TypeVariable
     * @return int
     */
    public static int getParameterTypeDeclarationIndex(final TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        // Ищем наш параметр среди всех параметров того класса, где определен нужный нам параметр.
        TypeVariable<?>[] typeVariables = genericDeclaration.getTypeParameters();
        Integer actualArgumentIndex = null;
        for (int i = 0; i < typeVariables.length; i++) {
            if (typeVariables[i].equals(typeVariable)) {
                actualArgumentIndex = i;
                break;
            }
        }
        if (actualArgumentIndex != null) {
            return actualArgumentIndex;
        } else {
            throw new IllegalStateException("Argument " + typeVariable + " is not found in " + genericDeclaration + ".");
        }
    }
}
