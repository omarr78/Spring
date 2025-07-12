## JPA Class

``` java

@Repository
@Transactional // is an annotation in Spring that manages transactions automatically for you.
public class CourseJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void insert(Course course) {
        entityManager.merge(course);
    }

    public Course findById(long id) {
        return entityManager.find(Course.class, id);
    }

    public void deleteById(long id) {
        Course course = entityManager.find(Course.class, id);
        entityManager.remove(course);
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
public class CourseCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CourseJpaRepository repository;

    @Override
    public void run(String... args) {
        repository.insert(new Course(1,"Java","author1"));
        // entityManager.merge(course);
        // Hibernate: insert into course (author,name,id) values (?,?,?)

        repository.insert(new Course(2,"Spring Boot","author2"));
        repository.insert(new Course(3,"SQL","author3"));
        repository.insert(new Course(4,"JPA","author4"));

        repository.deleteById(4);
        // Course course = entityManager.find(Course.class, id);
        // entityManager.remove(course);
        // Hibernate: select c1_0.id,c1_0.author,c1_0.name from course c1_0 where c1_0.id=?
        // Hibernate: delete from course where id=?


        System.out.println(repository.findById(1));
        // return entityManager.find(Course.class, id);
        // Hibernate: select c1_0.id,c1_0.author,c1_0.name from course c1_0 where c1_0.id=?

        System.out.println(repository.findById(2));
        System.out.println(repository.findById(3));
    }
}

```
### output
    Course{id=1, name='Java', author='author1'}
    Course{id=2, name='Spring Boot', author='author2'}
    Course{id=3, name='SQL', author='author3'}
