#### spring data jpa
스프링 데이터가 제공하는 마법같은 기능을 알아보자.

#### [쿼리메소드](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods)
`JpaRepository` 인터페이스를 상속하면 놀라운 일이 일어난다.  
규칙을 따르기만 하면 메서드명을 추천해준다.  

![jpa1](img/jpa1.png)

규칙은 간단하다.  
`Repository`를 상속하는 인터페이스를 만들기만 하면 된다.  
우리는 jpa를 사용하기 때문에 `Repository`를 상속한 `JpaRepository`를 사용하면 편하다.  
메서드명 추천은 꽤나 고도화되어있다. 아래의 기능도 전부 가능하다.

~~~java
public interface UserRepositoryV1 extends JpaRepository<UserV1, Long> {
    List<UserV1> findByNameAndAddressCity(String name, String city);
    long countUserByName(String name);
    @Transactional
    void deleteUserByName(String name);
}
~~~

페이징 처리도 간단하다.  
위의 에시에서 `findByNameAndAddressCity`에 페이징기능을 추가하고 싶다면 메서드에 `Pageable` 파라미터를 추가하면 된다.

~~~java
List<UserV1> findByNameAndAddressCity(String name, String city, Pageable pageable);
~~~

여러 레포지토리가 같은 메서드를 사용해야 하는 경우가 있을 수 있다.  [참조](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.definition-tuning)
그럴 때에는 아래와 같이 공통 인터페이스를 만들어서 상속하여 사용할 수 있다.  

~~~java
@NoRepositoryBean
public interface MyBaseRepositoryV2<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findById(ID id);
}

public interface UserRepositoryV2 extends JpaRepository<UserV1, Long> {
}
~~~