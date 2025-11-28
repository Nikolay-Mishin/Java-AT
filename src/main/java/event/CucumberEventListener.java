package event;

import static java.lang.System.out;
import static org.project.utils.exception.UtilException.tryConsumerWithPrint;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;

import org.project.utils.event.CucumberBaseEventListener;

/**
 * Создайте конструктор по умолчанию или конструктор, принимающий аргумент. Конструктор, принимающий аргумент, используется для настройки плагина.
 * <p>Возможны следующие типы:
 * <ul>
 * <li>{@code java.net.URI}</li>
 * <li>{@code java.net.URL}</li>
 * <li>{@code java.io.File}</li>
 * <li>{@code java.lang.String}</li>
 * <li>{@code java.lang.Appendable}</li>
 * </ul>
 */
public class CucumberEventListener extends CucumberBaseEventListener {
    public CucumberEventListener(String arg) throws ReflectiveOperationException {
        super(arg);
        //setEventHandler();
        tryConsumerWithPrint(t -> out.println("getClass: " + getClazz(arg)));
        tryConsumerWithPrint(() -> out.println("getField: " + getField(arg)));
    }
}
