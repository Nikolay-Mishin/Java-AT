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
