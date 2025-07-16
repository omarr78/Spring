## What is HATEOAS?

* A key REST constraint where clients discover resources and actions dynamically through hypermedia controls embedded in responses. 
* Just like navigating a website via links, REST APIs with HATEOAS guide clients through available workflows

## Example 1

### Before Add HATEOAS

``` java

@GetMapping("users/{id}")
public User retrieveUser(@PathVariable int id){
    User user = service.finaUserById(id);
    if(user == null){
        throw new UserNotFoundException("user with id "+ id +" not found");
    }
    return user;
}

```


``` json
"http://localhost:8080/users/1"

{
  "id": 1,
  "name": "Ahmed",
  "birthDate": "2005-07-16"
}

```

### After Add HATEOAS

``` java

@GetMapping("users-links/{id}")
public EntityModel<User> retrieveUserLink(@PathVariable int id){ 
    User user = service.finaUserById(id);
    if(user == null){
        throw new UserNotFoundException("user with id "+ id +" not found");
    }
    EntityModel<User> entityModel = EntityModel.of(user); // Wrap with EntityModel
    WebMvcLinkBuilder linkBuilder =
            linkTo(methodOn(UserResource.class).retrieveAllUsersLinks());
    entityModel.add(linkBuilder.withRel("all-users")); //  Add Hyperlink with HATEOAS

    return entityModel;
}

```


``` json
"http://localhost:8080/users-links/1"

{
  "id": 1,
  "name": "Ahmed",
  "birthDate": "2005-07-16",
  "_links": {
    "all-users": {
      "href": "http://localhost:8080/users"
    }
  }
}

```

---


## Example 2

### Before Add HATEOAS
 
``` java

@GetMapping("users")
public List<User> retrieveAllUsers(){
    return service.finaAll();
}

```


``` json
"http://localhost:8080/users"

[
  {
    "id": 1,
    "name": "Ahmed",
    "birthDate": "2005-07-16"
  },
  {
    "id": 2,
    "name": "Omar",
    "birthDate": "2000-07-16"
  },
  {
    "id": 3,
    "name": "Joo",
    "birthDate": "2007-07-16"
  }
]

```

### After Add HATEOAS

``` java

@GetMapping("users-links")
public List<EntityModel<User>> retrieveAllUsersLinks(){  
    List<User> users = service.finaAll();
    // iterate over users Wrap with EntityModel and then Add Hyperlink with HATEOAS
    List<EntityModel<User>> entityModels = users.stream()
            .map(user ->
                            EntityModel.of(user,linkTo(methodOn(UserResource.class)
                                    .retrieveUserLink(user.getId())).withSelfRel())).toList();
    return entityModels;
}

```


``` json
"http://localhost:8080/users-links"

[
  {
    "id": 1,
    "name": "Ahmed",
    "birthDate": "2005-07-16",
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/users-links/1"
      }
    ]
  },
  {
    "id": 2,
    "name": "Omar",
    "birthDate": "2000-07-16",
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/users-links/2"
      }
    ]
  },
  {
    "id": 3,
    "name": "Joo",
    "birthDate": "2007-07-16",
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/users-links/3"
      }
    ]
  }
]

```


---

## Why we need HATEOAS


* Clients rely only on server-provided links — no hardcoded URIs.
* Servers can evolve URIs without breaking clients.
* Clients simply follow available links rather than replicating business logic.

---

## Hypermedia Formats

### HAL (JSON Hypertext Application Language)

* Lightweight, widely adopted for embedding `_links` and `_embedded` in JSON/XML.

---

## Spring HATEOAS

### What It Offers:

* **EntityModel<T> / CollectionModel<T>** to wrap data + hyperlinks
* **Link builders**: `linkTo(methodOn(...)).withRel(...)`
* Supports HAL by default

---
