Settings Annotations
======================

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

By default set annotations `@Builder` and `@Data`, if no once annotations not set in `additionalProperties` or you use `sourceType = json` in your `configuration` for `jsonschema2pojo-maven-plugin`
```java
@Builder
@Data
public class YourModel {
}
```
