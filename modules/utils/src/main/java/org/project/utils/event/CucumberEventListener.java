package org.project.utils.event;

import java.io.File;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

import static org.project.utils.Helper.debug;

public class CucumberEventListener implements ConcurrentEventListener {
    protected File reportDir;
    protected final boolean eventHandler = false;
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

    public CucumberEventListener() {}

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
    public CucumberEventListener(String arg) {
        debug("CucumberEventListener: " + arg);
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
     * <li>{@code cucumber.api.event.TestRunFinished - последнее отправленное событие.</li>
     * </ul>
     * @param publisher
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
