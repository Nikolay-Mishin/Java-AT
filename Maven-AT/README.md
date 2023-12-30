MavenAT
======================

***********************************************

Stack

- Java
- Maven
- Cucumber
- Allure
- Lombok
- Rest-Assured

***********************************************

Изменения
-----

- [Notes](NOTES.md)

***********************************************

Tests
-----------
To run the code in your development environment:

1. Download and unpack
2. Run `npm install`
3. Run `npm run build` | `mvn clean install`, if this need
4. Start the cucumber tests and run allure report with `npm start` | `npm run tests` | `mvn clean test -Denv=stand`

***********************************************

Настройка модулей
-----

- [annotator](modules/annotator/README.md)
- [pojo](modules/pojo/README.md)

***********************************************

Настройки
-----

Окружение {UPSTREAM_BRANCH}. Тесты можно запустить на окружениях:
- stand (development) - https://petstore.swagger.io

run tests
-----
run cucumber tests on development
`npm start` | `npm run tests` | `mvn clean test -Denv=stand`

-----
run cucumber tests with environment
`mvn clean test -Denv=${UPSTREAM_BRANCH}`

-----
run allure report
`npm test` | `npm run tests & npm run allure` | `mvn clean test -Denv=stand & mvn allure:serve`

-----
build allure report
`npm run report` | `mvn allure:report`

***********************************************

resources
---------
- [API Docs](https://petstore.swagger.io)

additional resources
---------
- [REST API](https://cloud.yandex.ru/docs/glossary/rest-api)
- [HTTP протокол](https://ru.wikipedia.org/wiki/HTTP)
- [Swagger PetStore](https://petstore.swagger.io)
- [Postman](https://www.postman.com)
- [Переменная окружения JAVA_HOME](https://java-lessons.ru/first-steps/java-home)

***********************************************

install JAVA
-----

install
-----

1. Найстройки Windows > Система > О программе > Дополнительные параметры системы > Переменные среды
2. Создать системные переменные
```
ALLURE_HOME = C:\Dev\allure-2.23.0
JAVA_HOME = C:\Program Files\Java\jdk-17
MAVEN_HOME = C:\Dev\apache-maven-3.9.4
```
3. После установки IDEA Проверить наличие данных локальных переменных пользователя
```
IntelliJ IDEA Community Edition = C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.2.2\bin
Path: %IntelliJ IDEA Community Edition%
```
4. Создать в Path системные переменные
```
%JAVA_HOME%
%ALLURE_HOME%
%MAVEN_HOME%
%JAVA_HOME%\bin
%ALLURE_HOME%\bin
%MAVEN_HOME%\bin
```
5. Перезагрузить компьютер и проверить установленные инструменты в консоли
```
echo %JAVA_HOME%
java --version
allure --version
mvn -v
```

resources
---------
- [CMD команды](https://cmd-command.ru/komandy-cmd.html)
- [JDK](https://www.oracle.com/java/technologies/downloads/#java17)
- [Allure](https://docs.qameta.io/allure-report)
- [Maven](https://maven.apache.org/download.cgi)
- [Intellij Idea](https://www.jetbrains.com/idea/download/?section=mac)
- [GitHub](https://github.com)
- [Git](https://git-scm.com)

***********************************************

courses
---------
- [Курс по тестирование rest spi в Postman](https://qamari.getcourse.ru/postman)
- [Java для начинающих](https://stepik.org/course/115662/promo)
- [Java для продвинутых](https://stepik.org/course/115517/promo)
