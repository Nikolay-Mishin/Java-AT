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
`mvn deploy:deploy-file -Durl=file:C:/Dev/OSPanel/domains/Java-AT/Maven-AT/modules/pojo/repo -Dfile=target/pojo-3.4.5-SNAPSHOT.jar -DgroupId=org.project -DartifactId=pojo -Dpackaging=jar -Dversion=3.4.5-SNAPSHOT`

***********************************************

Настройка аннотаций
-----

- [annotations](../annotator/annotations.md)

***********************************************

add module to dependency in `pom.xml`
-----

```xml
<dependencies>
    <dependency>
        <groupId>org.project</groupId>
        <artifactId>pojo</artifactId>
        <version>3.4.5-SNAPSHOT</version>
    </dependency>
</dependencies>
```

use Pojo generator
-----

create your `PojoGenerator` or add this class and launch use `main`

generator provided in args your `webConfig`

```java
package utils.pojo;

import org.project.pojo.JsonSchemaToClass;
import org.project.pojo.JsonToClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;

public class PojoGenerator {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        new JsonSchemaToClass(BASE_CONFIG);
        new JsonToClass(BASE_CONFIG);
    }
}
```
