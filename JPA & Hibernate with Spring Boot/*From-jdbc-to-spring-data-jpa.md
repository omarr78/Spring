# From JDBC to Spring Data JPA

<img width="1100" height="150" alt="jpa drawio" src="https://github.com/user-attachments/assets/164f2975-484d-406c-82a0-807c7076ce0d" />

## JDBC (Java Database Connectivity)

* Manual SQL (delete from todo where id=?) + write a lot of boilerplate code
* Manage connections, exceptions, and resources

``` java

public void deleteTodo(int id) {
    PreparedStatement st = null;
    try {
        st = db.conn.prepareStatement("delete from todo where id=?");
        st.setInt(1, id);
        st.execute();
    } catch (SQLException e) {
        logger.fatal("Query Failed : ", e);
    } finally {
        if (st != null) {
            try { st.close(); } catch (SQLException e) {}
        }
    }
}

```

---

## Spring JDBC

* Simplified JDBC
* Less Java Code (boilerplate) using `JdbcTemplate`
* SQL-based and Still tied to raw SQL


``` java

public void deleteTodo(int id) {
    jdbcTemplate.update("delete from todo where id=?", id);
    // update used to insert, update or delete statement
}

``` 

## JPA (Java Persistence API)

* As we saw that when we are using Spring JDBC the Java code is simple and Works fine for simple queries and single-table operations.
* However, the queries might get really complex in real-world applications (Multiple tables, Joins, Subqueries, ...)

* JPA Uses a Different Approach: Instead of writing SQL manually, you:
    
    * Map Java Classes to Database Tables
    * Let the JPA handle queries

### Example: Mapping `Course` to `course` Table & Insert

```java

@Entity
public class Course {
    
    @Id
    private long id;
    private String name;
    private String author;

    // constructors, getters, setters
}


@Repository
public class CourseJpaRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Course insert(Course course) {
        return entityManager.merge(course);
    }

}

```

> This Java class becomes a **JPA Entity**
>
> It directly represents a **row** in the `course` table

### With JPA, You Can:

* **Insert**
* **Update**
* **Delete**
* **Find by ID**

All using **EntityManager** or **Spring Data JPA**
→ No need to write SQL manually


---


