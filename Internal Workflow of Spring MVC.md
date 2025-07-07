## Understanding the Internal Workflow of Spring MVC

### Web Request Lifecycle (Big Picture)

1. **Browser sends a request** (e.g., to `http://localhost:8080/login`)
2. **Spring Boot Application receives it**, which internally uses **Spring MVC**
3. **DispatcherServlet** (the *Front Controller*) handles all incoming requests
4. It routes to the **right Controller**
5. The **Controller executes business logic** and returns:

   * A **Model** (data to display)
   * A **View name** (e.g., "login")
6. **ViewResolver** maps the view name to a JSP file like `/WEB-INF/jsp/login.jsp`
7. **View is rendered** with the Model data
8. Final **HTML is returned to the browser**

---
## Evolution of Web Architecture in Java

### 1- Model 1 Architecture (All Logic in JSP)

![struts_model1](https://github.com/user-attachments/assets/9ac175c4-59fb-49a7-b659-e9b83601c35c)


the JSP page not only contains the display elements to output HTML,
but is also responsible for extracting HTTP request parameters,
call the business logic (implemented in JavaBeans, if not directly in the JSP),
and handle the HTTP session.

* Everything — page rendering, database access, logic — was done inside **JSP files**
* Example: `sayHello.jsp`, `welcome.jsp`
* **No separation of concerns**, hard to maintain , very Complex JSP 

---

### 2- Model 2 Architecture  (MVC without Front Controller)

![struts_model2](https://github.com/user-attachments/assets/d1947484-fdef-496e-acfe-90f3ecaf71cb)


* Introduced:

  * **Model** (data & business logic)
  * **View** (UI - JSP files)
  * **Controller** (Servlets to handle requests & route)
* Better separation of concerns
* If you want to build security, and it is similar across all servlets - Still difficult to handle like security across all servlets

---

### 3- Model 2 with Front Controller

![FrontController drawio](https://github.com/user-attachments/assets/3003ec80-12ff-4c87-bde5-fe39bfa52bd6)


* All requests first go through **one central servlet** (Dispatcher)
* Makes things like authentication, logging, etc., easier and consistent
* **Spring MVC uses this pattern**

---

## 4- Spring MVC Internals — What Really Happens

![mvc drawio](https://github.com/user-attachments/assets/5ab4bed8-b530-4010-be69-7be0f0f9a162)


### Example: Request to `/login`

* **Request → DispatcherServlet (the Front Controller)**

   * Spring boot auto-registers it
   * It receives all requests (e.g., `/login`)

* **DispatcherServlet (the Front Controller) → Find Matching Controller**

   * Finds the controller method that maps to `/login`
   * Executes it

   ```java
    @RequestMapping("login")
    public String login(@RequestParam String name, ModelMap model) {
        model.put("name", name);
        return "login";
    }
   ```

* **Controller returns a View name** (`"login"`) + Model data

* **DispatcherServlet → ViewResolver**

   * Adds configured prefix & suffix (e.g., `/WEB-INF/jsp/` + `login` + `.jsp`)
   * Resolves final path: `/WEB-INF/jsp/login.jsp`

* **DispatcherServlet → Executes the View**

   * JSP renders HTML using Model data
   * Final HTML is returned as HTTP response

---

## Key Concepts Recap

| Concept             | Role                                                               |
| ------------------- | ------------------------------------------------------------------ |
| DispatcherServlet   | Central controller (Front Controller) that routes all requests     |
| Controller          | Receives user input, processes it, returns view name + model       |
| View (JSP)          | UI that displays model data                                        |
| ViewResolver        | Resolves a logical view name (e.g., "login") to an actual JSP file |
| Model               | A map of attributes that you pass to the view                      |

---

## Configuration

### `application.properties` (for ViewResolver)

```properties
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

---


