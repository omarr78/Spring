## Custom Structure for Exception Response in Spring Boot

Spring Boot provides a default mechanism for handling exceptions, but for REST APIs, it's best to **return meaningful error responses**.
This is achieved using:

* `@ControllerAdvice` – Global exception handler.
* `ResponseEntityExceptionHandler` – Base class for handling Spring MVC exceptions by returning ResponseEntity
with formatted error details.
* Custom `ErrorDetails` POJO – For structuring the error response.


## We will Create a **custom error response structure** to handle:

* Validation errors (`@Valid`)
* Custom exceptions (e.g., `UserNotFoundException`)
* Generic unexpected exceptions

---


## 1. `ErrorDetails.java`

A simple POJO to hold error response structure.

```java
import java.time.LocalDateTime;

public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    // Constructor
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters only (immutability is preferred for response objects)
}
```

### Sample JSON Output:

```json
{
  "timestamp": "...",
  "message": "User with id 5 not found",
  "details": "uri=/users/5"
}
```

---


##  2. `CustomizedResponseEntityExceptionHandler.java`

This class is a global exception handler for the whole application using `@ControllerAdvice`.

```java


@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //  Handler for UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false) // e.g., uri=/users/5
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404
    }

    //  Handler for validation errors (e.g., @Valid fails)
    //  Overrides default behavior to provide custom message.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String message = "Total Errors: " + ex.getErrorCount() +
                         ", First Error: " + ex.getFieldError().getDefaultMessage();

        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

    //  Handler for all other (generic) exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
```

---



## 3. `UserNotFoundException.java`

* Custom exception thrown when a user is not found in the system

```java

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

---

## 4. Example usage in a controller/service

```java
User user = userRepository.findById(id).orElse(null);

if (user == null) {
    throw new UserNotFoundException("User with id " + id + " not found");
}
```

---

