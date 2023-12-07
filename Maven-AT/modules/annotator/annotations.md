Settings Annotations
======================

set `model.schema.json`
-----

Add `annotations` in your `model.json.schema`

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

generate your class `model`
-----

You can set annotations:
```java
package pojo.gen;

import javax.annotation.processing.Generated;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Generated("jsonschema2pojo")
public class Model {
}
```

By default set annotations `@Builder` and `@Data`, if no once annotations not set in `additionalProperties` or you use `sourceType = json` in your `configuration` for `jsonschema2pojo-maven-plugin`
```java
package pojo.gen;

import javax.annotation.processing.Generated;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Generated("jsonschema2pojo")
public class Model {
}
```
