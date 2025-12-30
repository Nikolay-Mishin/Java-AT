**Стэк:** Java 17, Maven.

**Направления тестов:** API, UI, Native.

**Библиотеки:** Cucumber, Lombok, Pojo, Rest-assured, Selenium, Appium

**Инструменты:** WebDriver, WinAppDriver

**GitHub:** GitHub-deploy, GitHub-actions

GitHub-actions пока что так себе настроены.

UI на основе WebDriver пока не проверял - но как знаю для них лучше использовать другие инструменты, поэтому это направление можно не рассматривать.

Собрал основные инструменты в единую библиотеку UTILS - настроен деплой на GitHub.

Частично настроены GitHub-actions, но пока только работают API тесты - еще до конца не разобрался как там завести WinAppDriver.

Если проект упакован в jar-файл, то docker-файл будет выглядеть примерно так:
```
FROM java:17
EXPOSE 8080
ADD /target/demo.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]
```

Если же ты упаковываешь web-приложение, то может понадобиться добавить Tomcat:
```
FROM tomcat8
ADD sample.war ${CATALINA_HOME}/webapps/ROOT.war
CMD ${CATALINA_HOME}/bin/catalina.sh run
```
