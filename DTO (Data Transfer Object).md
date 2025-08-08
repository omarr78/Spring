## what is DTO (Data Transfer Object)

An object specifically used to send data over a network

---


## Advantage of DTOs

### 1. Less data

Send less data over the network

### 2. Hide Data

Don't send data that could be sensitive

### 3. Hide implementation 

Don't expose the internal structure of your database

### 4. Access Control 

Some database data sent different responses depending on the user's role (paid membership vs free tier)

---

## Example User vs UserDTO

| User Class (Entity) | UserDTO class |
| ------------------- | ------------- |
| id                  | username      | 
| username            | email         |
| email               |               |
| password            |               |

---

## code Example

### user class (Entity)

``` java

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String username;
    private String email;
    private String password; // Sensitive, not sent in DTO

    // Constructors
    // Getters & Setters
}
```

### userDTO class

``` java

public class UserDTO {

    private String username;
    private String email;

    public UserDTO(User user) {
        this.username = username;
        this.email = email;
    }

    // Getters & Setters
}

```

### Then mapping from `User` → `UserDTO`

``` java

@GetMapping
public List<UserDTO> retrieveAllUsers() {
    return userRepository.findAll().stream().map(u -> new UserDTO(u)).toList();
}

``` 


