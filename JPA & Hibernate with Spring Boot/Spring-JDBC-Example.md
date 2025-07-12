## Spring JDBC Class

``` java

@Repository
public class CourseJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String INSERT_QUERY  =
            """
            INSERT INTO course(id, name, author)
            VALUES (?,?,?);
            """;

    private static String DELETE_QUERY  =
            """
            DELETE FROM course WHERE id = ?;
            """;

    private static String SELECT_QUERY  =
            """
            SELECT * FROM course WHERE id = ?;
            """;

    public void insert(Course course){
        jdbcTemplate.update(INSERT_QUERY,
                course.getId(), course.getName(), course.getAuthor());
    }
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_QUERY,id);
    }
    public Course findById(long id) {
        
        return jdbcTemplate.queryForObject(SELECT_QUERY,
                new BeanPropertyRowMapper<>(Course.class),id);
        
        /*
        jdbcTemplate.queryForObject

        run a SQL query that returns a single row.
        It maps that row to a Java object.
        If no result is found, it throws an exception.
        If more than one row is returned, it also throws an exception.
        we use it when you expect exactly one result.
    
        new BeanPropertyRowMapper<>(Course.class)

        This is a mapper that converts a result row from the DB into a `Course` object.
        It matches column names in the result set to Java bean property names in the `Course` class.
        Example:
        
        public class Course {
            private long id;
            private String name;
            private String author;
            // getters and setters
        }
        
        If the query returns a row like:

        | id | name       | author     |
        | -- | ---------- | ---------- |
        | 1  | "Math"     | "omar"     |

        It gets mapped to:

        new Course(1, "Math", "omar")

        */
    }

}

```

## CommandLineRunner

### CommandLineRunner is a Spring Boot interface that lets you run specific code after the Spring application has fully started.


* Spring Boot starts the application.
* Spring creates all beans (your services, repos, etc.).
* After the context is ready, it runs all beans that implement CommandLineRunner.
* The run() method is executed automatically.




``` java

@Component
public class CourseJdbcCommandLine implements CommandLineRunner {

    @Autowired
    CourseJdbcRepository repository;

    @Override
    public void run(String... args) {
        repository.insert(new Course(1,"Java","author1"));
        repository.insert(new Course(2,"Spring Boot","author2"));
        repository.insert(new Course(3,"SQL","author3"));
        repository.insert(new Course(4,"JPA","author4"));

        repository.deleteById(4);

        System.out.println(repository.findById(1)); 
        System.out.println(repository.findById(2));
        System.out.println(repository.findById(3));
    }
}

```
### output
    Course{id=1, name='Java', author='author1'}
    Course{id=2, name='Spring Boot', author='author2'}
    Course{id=3, name='SQL', author='author3'}
