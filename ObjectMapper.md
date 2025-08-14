## **1. What is `ObjectMapper`?**

In Java, `ObjectMapper` is a class from the **Jackson** library (`com.fasterxml.jackson.databind.ObjectMapper`).
Its main job:

* **Convert JSON to Java objects** (deserialization)
* **Convert Java objects to JSON** (serialization)

it is as the translator between JSON text and Java objects.

Example:

```java
ObjectMapper mapper = new ObjectMapper();
String json = "{\"name\":\"Omar\", \"age\":25}";

Person person = mapper.readValue(json, Person.class);
```

Here, Jackson reads the JSON and fills a `Person` object.

---

## **2. Mapping from JSON to a List**


If you have JSON like:

```json
[
  { "name": "Omar", "age": 25 },
  { "name": "Sara", "age": 30 }
]
```

You can map it to a `List<Person>` like this:

```java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

ObjectMapper mapper = new ObjectMapper();

String jsonArray = "[{\"name\":\"Omar\",\"age\":25},{\"name\":\"Sara\",\"age\":30}]";

List<Person> people = mapper.readValue(jsonArray, new TypeReference<List<Person>>() {});
```

---

## **3. How Jackson actually decides which JSON field goes into which Java field.**

When you do:

```java
mapper.readValue(json, Person.class);
```

Jackson:

1. **Creates an empty instance** of your class (using the default constructor — unless you tell it otherwise).
2. **Looks at the JSON keys** and tries to find matching **Java properties** (getters/setters or fields) with the same name.

   * `"name"` in JSON → `setName(...)` or `name` field in Java
   * `"age"` in JSON → `setAge(...)` or `age` field in Java
3. **Uses setters first** if they exist, otherwise it writes directly to the field.
4. **Ignores extra JSON fields** unless you configure it to fail on them.
5. **Supports annotations** like `@JsonProperty("full_name")` if your JSON key doesn’t match the Java field name.

For example:

```java
public class Person {
    @JsonProperty("full_name")
    private String name;
    private int age;
    
    // getters & setters
}
```

Now `"full_name": "Omar"` in JSON will set the `name` field.

---

## why we need `TypeReference`


```java
new TypeReference<List<Person>>() {}
```

---

### The root problem: **type erasure**

In Java, generics (like `List<Person>`) are **erased at runtime**.
That means:

```java
List<Person> people = ...
```

is treated at runtime as just:

```java
List people = ...
```

So when you call:

```java
mapper.readValue(json, List.class);
```

Jackson **only** sees “List” — it has no clue that it should fill it with `Person` objects.
It will give you a `List<Map<String,Object>>` instead, because that’s the safest guess.

---


### What `TypeReference` does

`TypeReference<List<Person>>() {}` captures the **full generic type** (including `Person`) in a way Jackson can read **at runtime**.

So when Jackson sees:

```java
new TypeReference<List<Person>>() {}
```

it can say:

* "Ah, this is a `List` *of* `Person` objects."
* "I should deserialize each JSON object into a `Person`, not just a `Map`."

---
