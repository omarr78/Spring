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


