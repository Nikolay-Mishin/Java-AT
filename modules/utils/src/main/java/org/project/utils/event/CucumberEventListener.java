package org.project.utils.event;

import static java.lang.System.out;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.Event;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.EmbedEvent;
import io.cucumber.plugin.event.SnippetsSuggestedEvent;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.WriteEvent;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.forEach;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notNull;
import static org.project.utils.Helper.split;
import static org.project.utils.Helper.trim;
import static org.project.utils.base.HashMap.sort;
import static org.project.utils.exception.UtilException.tryConsumerWithIgnore;
import static org.project.utils.exception.UtilException.tryNoArgsWithIgnore;
import static org.project.utils.reflection.Reflection.getClassName;
import static org.project.utils.reflection.Reflection.getClassSimpleName;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;

import org.project.utils.config.TestBaseConfig;

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
public class CucumberEventListener implements ConcurrentEventListener {
    protected static String pluginSep = ",";
    protected static String argSep = ":";
    protected static String argsSep = ";";
    protected static Map<String, String> pluginMap = new HashMap<>();
    protected File reportDir;
    protected boolean eventHandler = false;
    //private final EventHandler<TestRunStarted> runStartedHandler = event -> startReport(event);
    protected final EventHandler<Event> runEventHandler = this::event;
    protected final EventHandler<TestRunStarted> runStartedHandler = this::runStart;
    protected final EventHandler<TestSourceRead> testSourceReadHandler = this::sourceRead;
    protected final EventHandler<SnippetsSuggestedEvent> snippetsSuggestedHandler = this::snippetsSuggested;
    protected final EventHandler<TestCaseStarted> caseStartedHandler = this::caseStart;
    protected final EventHandler<TestStepStarted> stepStartedHandler = this::stepStart;
    protected final EventHandler<EmbedEvent> embedEventHandler = this::embed;
    protected final EventHandler<WriteEvent> writeEventHandler = this::write;
    protected final EventHandler<TestStepFinished> stepFinishedHandler = this::stepFinish;
    protected final EventHandler<TestCaseFinished> caseFinishedHandler = this::caseFinish;
    protected final EventHandler<TestRunFinished> runFinishedHandler = this::runFinish;

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
    public CucumberEventListener() throws ReflectiveOperationException {
    }

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
    public CucumberEventListener(String arg) throws ReflectiveOperationException {
        init(arg);
    }

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
    public CucumberEventListener(String arg, boolean eventHandler) throws ReflectiveOperationException {
        init(arg, eventHandler);
    }

    public void init(String arg) throws ReflectiveOperationException {
        init(arg, false);
    }

    public void init(String arg, boolean eventHandler) throws ReflectiveOperationException {
        //debug("CucumberEventListener: " + arg);
        String[] args = trim(arg.split(argsSep));
        debug("CucumberEventListener: " + Arrays.toString(args));
        for (String a : args) {
            Class<?> clazz = tryNoArgsWithIgnore(() -> getClazz(a));
            out.println("getClass: " + clazz);
            if (isNull(clazz)) tryConsumerWithIgnore(() -> out.println("getField: " + getField(a)));
        }
        eventHandler(eventHandler);
    }

    public static String getPlugin(String className) {
        try {
            return getPlugin(getClazz(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            return "";
        }
    }

    public static String getPlugin(Class<?> clazz) {
        debug("pluginClass: " + clazz);
        debug("pluginClassName: " + getClassName(clazz));
        debug("pluginName: " + getClassSimpleName(clazz));
        String plugin = pluginMap.get(getClassName(clazz));
        debug("plugin: " + plugin);
        debug("plugin: " + (notNull(plugin) ? plugin : pluginMap.get(getClassSimpleName(clazz))));
        return notNull(plugin) ? plugin : pluginMap.get(getClassSimpleName(clazz));
    }

    public static Map<String, String> getPluginMap(TestBaseConfig config) throws ReflectiveOperationException {
        return getPluginMap(config.getCPlugins());
    }

    public static Map<String, String> getPluginMap(String plugins) throws ReflectiveOperationException {
        String[] keys = split(plugins, pluginSep);
        forEach(keys, p -> {
            String[] _p = split(p, argSep);
            try {
                String plugin = _p[0];
                String pluginClassName = getClassName(plugin);
                String pluginName = getClassSimpleName(plugin);
                pluginMap.put(pluginMap.containsKey(pluginName) ? pluginClassName : pluginName, pluginClassName + (_p.length == 1 ? "" : argSep + _p[1]));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                //throw new RuntimeException(e);
            }
        });
        pluginMap = sort(pluginMap);
        debug("plugins: " + pluginMap);
        return pluginMap;
    }

    public static Collection<String> getPluginsCol() {
        return getPluginsCol(pluginMap);
    }

    public static Collection<String> getPluginsCol(TestBaseConfig config) throws ReflectiveOperationException {
        return getPluginsCol(getPluginMap(config));
    }

    public static Collection<String> getPluginsCol(String plugins) throws ReflectiveOperationException {
        return getPluginsCol(getPluginMap(plugins));
    }

    public static Collection<String> getPluginsCol(Map<String, String> plugins) {
        return plugins.values();
    }

    public static String[] getPlugins() {
        return getPlugins(pluginMap);
    }

    public static String[] getPlugins(TestBaseConfig config) throws ReflectiveOperationException {
        return getPlugins(getPluginMap(config));
    }

    public static String[] getPlugins(String plugins) throws ReflectiveOperationException {
        return getPlugins(getPluginMap(plugins));
    }

    public static String[] getPlugins(Map<String, String> plugins) {
        return getPlugins(getPluginsCol(plugins));
    }

    public static String[] getPlugins(Collection<String> plugins) {
        return plugins.toArray(String[]::new);
    }

    protected boolean eventHandler() {
        return eventHandler;
    }

    protected boolean eventHandler(boolean eventHandler) {
        return this.eventHandler = eventHandler;
    }

    protected boolean setEventHandler() {
        return eventHandler(true);
    }

    /**
     * Нужно зарегистрировать обратные вызовы, чтобы можно было получать соответствующие события. Каждый получатель должен реализовывать интерфейс {@code cucumber.api.event.EventHandler<T extends Event>}.
     * <p>Обработчики регистрируются в методе {@code public void setEventPublisher(EventPublisher publisher)}.
     * <p>Всего существует одиннадцать различных событий, которые можно зарегистрировать:
     * <ul>
     * <li>{@code cucumber.api.event.Event} — все события.</li>
     * <li>{@code cucumber.api.event.TestRunStarted} — отправлено первое событие.</li>
     * <li>{@code cucumber.api.event.TestSourceRead} — отправляется для каждого прочитанного файла с данными, содержит исходный файл с данными.</li>
     * <li>{@code cucumber.api.event.SnippetsSuggestedEvent} - отправляется для каждого шага, который не удалось сопоставить с определением шага, и содержит необработанные фрагменты для этого шага.</li>
     * <li>{@code cucumber.api.event.TestCaseStarted} - отправляется перед началом выполнения тестового сценария (/Pickle/Scenario), содержит тестовый сценарий.</li>
     * <li>{@code cucumber.api.event.TestStepStarted} - отправляется перед началом выполнения тестового шага, содержит тестовый шаг.</li>
     * <li>{@code cucumber.api.event.EmbedEvent} - вызов scenario.embed в хуке запускает это событие.</li>
     * <li>{@code cucumber.api.event.WriteEvent} - вызов scenario.write в хуке запускает это событие.</li>
     * <li>{@code cucumber.api.event.TestStepFinished} - отправляется после выполнения тестового шага, содержит тестовый шаг и его результат.</li>
     * <li>{@code cucumber.api.event.TestCaseFinished} - отправляется после выполнения тестового сценария (/Pickle/Scenario), содержит тестовый сценарий и его результат.</li>
     * <li>{@code cucumber.api.event.TestRunFinished} - последнее отправленное событие.</li>
     * </ul>
     * @param publisher EventPublisher
     */
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        if (eventHandler) publisher.registerHandlerFor(Event.class, runEventHandler);
        publisher.registerHandlerFor(TestRunStarted.class, runStartedHandler);
        publisher.registerHandlerFor(TestSourceRead.class, testSourceReadHandler);
        publisher.registerHandlerFor(SnippetsSuggestedEvent.class, snippetsSuggestedHandler);
        publisher.registerHandlerFor(TestCaseStarted.class, caseStartedHandler);
        publisher.registerHandlerFor(TestStepStarted.class, stepStartedHandler);
        publisher.registerHandlerFor(EmbedEvent.class, embedEventHandler);
        publisher.registerHandlerFor(WriteEvent.class, writeEventHandler);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, caseFinishedHandler);
        publisher.registerHandlerFor(TestRunFinished.class, runFinishedHandler);
    }

    protected EventHandler<TestRunStarted> startReportHandler() {
        return new EventHandler<>() {
            @Override
            public void receive(TestRunStarted event) {
                runStart(event);
            }
        };
    }

    protected void event(Event event) {
        debug("Event: " + event);
    }

    protected void runStart(TestRunStarted event) {
        debug("TestRunStarted: " + event);
    }

    protected void sourceRead(TestSourceRead event) {
        debug("TestSourceRead: " + event);
    }

    protected void snippetsSuggested(SnippetsSuggestedEvent event) {
        debug("SnippetsSuggestedEvent: " + event);
    }

    protected void caseStart(TestCaseStarted event) {
        debug("TestCaseStarted: " + event);
    }

    protected void stepStart(TestStepStarted event) {
        debug("TestStepStarted: " + event);
    }

    protected void embed(EmbedEvent event) {
        debug("EmbedEvent: " + event);
    }

    protected void write(WriteEvent event) {
        debug("WriteEvent: " + event);
    }

    protected void stepFinish(TestStepFinished event) {
        debug("TestStepFinished: " + event);
    }

    protected void caseFinish(TestCaseFinished event) {
        debug("TestCaseFinished: " + event);
    }

    protected void runFinish(TestRunFinished event) {
        debug("TestRunFinished: " + event);
    }

    protected void createOutDir(String outputDir) {
        reportDir = new File(outputDir);
        debug("createOutDir: " + outputDir);
        if (!reportDir.exists()/* && !reportDir.mkdirs()*/) {
            debug("Failed to create dir: " + outputDir);
            //throw new CucumberException("Failed to create dir: " + outputDir);
        }
    }

}
