# Getting Started  

Spring Boot provides support to QueryDSL library, a Java library that gives the ability to create SQL and JPQL queries 
using a fluent and type-safe API to overcome the challenges of handling complex databases:
<br>
1. Type Safety  
   QueryDSL allows to write queries using Java types, catching errors early in the development process.  
<br>
2. Fluent API  
   QueryDSL’s fluent API allows creating and chaining queries that are not only powerful but also easy to read.  
<br>
3. Code Generation  
   QueryDSL generate query classes based on JPA entities, saving time and effort of writing repetitive code.  
<br>
4. Dynamic Queries for Dynamic Projects  
   With QueryDSL’s queries can be constructed based on runtime conditions.  
<br>
5. Joining Forces with Join Operations  
   With QueryDSL performing joins is effortless, navigation through data models is easy, and complex relationships become simple to manage.  
<br>
6. Readable Code  
   QueryDSL’s expressive syntax ensures easy to read and understand queries.  

### QueryDSL Usage Demonstration ###  

To demonstrate the usage of queryDSL filtering, Student entity is created and corresponding StudentFilter class, 
with all possible properties to filter with.  
this approach is designed to create dynamic and complex queries with ease, and based on client needs, weather
clients choose to filter or not, or choose different properties each time.  
This class `StudentFilter` will bind chosen properties and generate the Predicate to filter with to 
QuerydslPredicateExecutor<Student> repository findAll method.  

`QuerydslPredicateExecutor<T>` interface provides findAll and overloads with Pageable, Sort and more, 
besides count, exists, findOne, getBy and more, all which take a Predicate.  
Repositories in this system extends `QuerydslPredicateExecutor<T>` to use these methods with Predicate and also extends 
`JpaRepository` to use its methods as well like findAll and count without Predicate. Although one true Predicate could be
constructed for these cases as `Expressions.asBoolean(true).isTrue()`, which will evaluate to true for all records.

`InitialTestData` class is provided to initialize database with data, and StudentServiceTest demonstrates some usages for Student entity.  

For demonstration purposes most tests in this repository are made with H2 database, it is preferable to use same 
database both in the project and in the tests, because databases differ from one another.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.5/reference/data/sql.html#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

