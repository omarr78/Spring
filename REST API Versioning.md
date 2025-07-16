## Introduction to API Versioning

* When you build a successful REST API with a large consumer base, introducing breaking changes (like splitting a `name` field into `firstName` and `lastName`) can be confusion.
* To manage these changes smoothly, API versioning is essential.
It allows you to offer different versions of your API simultaneously, giving consumers time to migrate.

* There's no single "best" approach to API versioning; each method comes with its own set of trade-offs.
* Let's explore the common options and the factors to consider when choosing one.

---

## Common API Versioning Options

Here are the four primary methods for versioning REST APIs, along with examples:

### 1. URI Versioning (Path Versioning)

* Description: The API version is included directly in the URL path.

* Examples:

      http://localhost:8080/v1/person
      
      http://localhost:8080/v2/person

* Example Code

```java

@GetMapping("v1/person")
public PersonV1 getFirstVersionPersonUri(){
    return new PersonV1("omar");
}

@GetMapping("v2/person")
public PersonV2 getSecondVersionPersonUri(){
    return new PersonV2("omar","nabil");
}

```

* Real-world example: Twitter API

---

### 2. Request Parameter Versioning

* Description: The API version is passed as a query parameter in the URL.

* Examples:

      http://localhost:8080/person?version=1
      
      http://localhost:8080/person?version=2


* Example Code

```java

@GetMapping(path = "person", params = "version=v1")
public PersonV1 getFirstVersionPersonRequestParam(){
    return new PersonV1("omar");
}
@GetMapping(path = "person", params = "version=v2")
public PersonV2 getSecondVersionPersonRequestParam(){
    return new PersonV2("omar","nabil");
}

```


* Real-world example: Amazon API


---


### 3. Custom Headers Versioning

* Description: The API version is specified in a custom HTTP header.

* Examples:

      http://localhost:8080/person/header with X-API-VERSION: 1
      
      http://localhost:8080/person/header with X-API-VERSION: 2


* Example Code

```java

 @GetMapping(path = "person/header", headers = "X-API-VERSION=1")
public PersonV1 getFirstVersionPersonRequestHeader(){
    return new PersonV1("omar");
}
@GetMapping(path = "person/header", headers = "X-API-VERSION=2")
public PersonV2 getSecondVersionPersonRequestHeader(){
    return new PersonV2("omar","nabil");
}

```


* Real-world example: Microsoft APIs


---

4. Media Type Versioning (Accept Header Versioning)

* Description: The API version is included in the Accept header of the HTTP request, often using a custom media type.

* Examples:

      http://localhost:8080/person/accept with Accept: application/vnd.company.app-v1+json
      
      http://localhost:8080/person/accept with Accept: application/vnd.company.app-v2+json


* Example Code

```java

 @GetMapping(path = "person/accept", produces = "application/vnd.company.app-v1+json")
public PersonV1 getFirstVersionPersonAcceptHeader(){
    return new PersonV1("omar");
}
@GetMapping(path = "person/accept", produces = "application/vnd.company.app-v2+json")
public PersonV2 getSecondVersionPersonAcceptHeader(){
    return new PersonV2("omar","nabil");
}

```

--- 

## Versioning REST API - Factors - Factors to consider

## 1. URI Pollution

* **URI & Query Parameter** methods embed version info directly in the URL (`/v1/person`, `?version=2`), which pollutes the resource namespace—clients see multiple URIs for the "same" concept.
* **Header** & **Media Type** approaches keep URIs clean and free of version clutter.

---

## 2. Misuse of HTTP Headers

* Header or Accept-based versioning repurposes HTTP headers beyond their intended roles; pure REST purists consider it improper use.
* URI/path and param-based versioning use the protocol as designed, avoiding header misuse.

---

## 3. Caching

* URI-based versioning (and query params) work smoothly with HTTP caches—each versioned URL is cached separately.
* Header/media versioning requires cache layers to key on headers, which is less common and needs explicit cache configuration.

---

## 4. Browser Executability

* With URI or query param versioning, any browser can access and test endpoints (`/v2/person`, `?version=1`).
* Browsers don't allow you to easily set custom HTTP headers or media type approaches from the address bar. Requires tools like Postman.

---

## 5. API Documentation

* Version-in-URI or param is immediately visible and intuitive—easy to document and understand.
* Header or media type versioning needs extra documentation effort, as versioning is hidden in headers.

---

## Summary: No Perfect Solution

| Factor          | URI / Param  | Header / Media Type |
| --------------- | -----------  | ------------------- | 
| URI Pollution   | 🔴 Yes       | ✅ No              | 
| Header Misuse   | ✅ No        | 🔴 Yes             |
| Caching         | ✅ Excellent | ⚠️ Needs setup     | 
| Browser Testing | ✅ Easy      | ❌ Needs tools     |
| Documentation   | ✅ Simple    | ⚠️ Extra effort    | 

No one-size-fits-all answer exists. Choose based on your needs:

* Want simplicity, visibility, and caching? → **URI or query param**
* Want clean URLs and you're okay with complexity? → **Header or media type**


---






