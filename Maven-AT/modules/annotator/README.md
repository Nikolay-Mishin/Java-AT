MavenAT
======================

Tests
-----------
To run the code in your development environment:

1. Download and unpack
2. Run once of commands for install module

***********************************************

Настройки
-----

install module
-----
install to .m2 local repo
`npm start` | `npm run build` & `npm run deploy` | `mvn clean install` & `node index.js -deploy -root`

-----
install to .m2 local repo & local module repo (if this need)
`npm start` | `npm run build & npm run deploy` | `mvn clean install & node index.js -deploy -root`

-----
manually deploy to local module repo (if this need)
`mvn deploy:deploy-file -Durl=file:C:/Dev/OSPanel/domains/Java-AT/Maven-AT/modules/annotator/repo -Dfile=target/annotator-3.4.5-SNAPSHOT.jar -DgroupId=org.project -DartifactId=annotator -Dpackaging=jar -Dversion=3.4.5-SNAPSHOT`

***********************************************

add module to dependency in `pom.xml`
-----

```xml
<dependencies>
    <dependency>
        <groupId>org.project</groupId>
        <artifactId>annotator</artifactId>
        <version>3.4.5-SNAPSHOT</version>
    </dependency>
</dependencies>
```

use custom annotator
-----

add this in `configuration` for your `jsonschema2pojo-maven-plugin` for use custom annotator

```xml

<plugin>
    <groupId>org.jsonschema2pojo</groupId>
    <artifactId>jsonschema2pojo-maven-plugin</artifactId>
    <version>1.2.1</version>
    <configuration>
        <customAnnotator>org.project.annotator.lombok.LombokAnnotator</customAnnotator>
    </configuration>
</plugin>
```

add this in `dependencies` for your `jsonschema2pojo-maven-plugin` in `build.plugins`

```xml
<dependencies>
    <dependency>
        <groupId>org.project</groupId>
        <artifactId>annotator</artifactId>
        <version>3.4.5-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Or you also can add this in your `build.plugins` and change values in `configuration` for your individual settings.

```xml

<plugin>
    <groupId>org.jsonschema2pojo</groupId>
    <artifactId>jsonschema2pojo-maven-plugin</artifactId>
    <version>1.2.1</version>
    <configuration>
        <sourceDirectory>${basedir}/src/main/resources/json</sourceDirectory>
        <outputDirectory>${basedir}/src/main/java</outputDirectory>
        <targetPackage>pojo.gen</targetPackage>
        <customAnnotator>org.project.annotator.lombok.LombokAnnotator</customAnnotator>
        <sourceType>json</sourceType>
        <annotationStyle>none</annotationStyle>
        <generateBuilders>false</generateBuilders>
        <useLongIntegers>true</useLongIntegers>
        <includeGetters>false</includeGetters>
        <includeSetters>false</includeSetters>
        <includeHashcodeAndEquals>false</includeHashcodeAndEquals>
        <includeToString>false</includeToString>
        <includeAdditionalProperties>false</includeAdditionalProperties>
        <initializeCollections>false</initializeCollections>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.project</groupId>
            <artifactId>annotator</artifactId>
            <version>3.4.5-SNAPSHOT</version>
        </dependency>
    </dependencies>
</plugin>
```

***********************************************

set `model.schema.json`
-----

You can set annotations:
```java
@Builder
@Data
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Model {
}
```

Add `annotations` in your `json.schema`

```json
{
    "type": "object",
    "additionalProperties": {
        "lombok-builder": true,
        "lombok-data": true,
        "lombok-getter": true,
        "lombok-setter": true,
        "lombok-equals-and-hash-code": true,
        "lombok-no-args-constructor": true,
        "lombok-all-args-constructor": true,
        "lombok-to-string": true
    },
    "properties": {}
}
```

By default set annotations `@Builder` and `@Data`, if no once annotations not set in `additionalProperties` or you use `sourceType = json` in your `configuration` for `jsonschema2pojo-maven-plugin`
```java
@Builder
@Data
public class YourModel {
}
```

***********************************************
