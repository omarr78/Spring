### This code is a Spring Boot REST API example that retrieves a user by their id. 

``` java
@GetMapping("users/{id}")
public User retrieveUser(@PathVariable int id){
    User user = service.findUserById(id);
    if(user == null){
        throw new UserNotFoundException("id:" + id);
    }
    return user;
}
```

### If the user is not found, it throws a custom exception (UserNotFoundException) which is automatically mapped to return an HTTP 404 (Not Found) response.

``` java
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

`@ResponseStatus(HttpStatus.NOT_FOUND)` 

tells Spring:
If this exception is thrown, respond with HTTP `404` (More descriptive and controlled error messages) instead of default `500`.

So when you do:
  
    throw new UserNotFoundException("id:5");

The user gets this:

```
HTTP/1.1 404 Not Found
{
  "timestamp": "...",
  "status": 404,
  "error": "Not Found",
  "message": "id:5",
  "path": "/users/5"
}
```

instead of this

```
"timestamp": "...",
"status": 500,
"error": "Internal Server Error",
"message": "id:5",
"path": "/users/5"
```

