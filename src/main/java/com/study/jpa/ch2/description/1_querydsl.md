#### querydsl
querydsl을 사용해보자.  

##### 기본사용 
~~~java
@Entity
public class ContentV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long id;

    private String name;
}
~~~
`content` 엔티티를 querydsl을 통해서 조회해보자.  
querydsl을 사용하는 방법은 여러가지가 있지만 기존에 공부한 [사용자 정의 레포지토리](https://github.com/outgrow0905/study-spring-data-jpa/blob/main/src/main/java/com/study/jpa/ch1/description/1_spring-data-jpa.md#%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EC%9D%98-%EB%A0%88%ED%8F%AC%EC%A7%80%ED%86%A0%EB%A6%AC)를 이용하는것이 범용적인것 같다.  

##### step0
기본적으로 querydsl은 `JPAQueryFactory`를 이용하여 사용한다.  
빈 먼저 생성해두자. 
~~~java
@Configuration
public class QueryDslConfiguration {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
~~~

##### step1
먼저 기본적으로 아래와 같이 `JpaRepository`를 상속하여 인터페이스를 생성한다.  
여기까지는 querydsl과 아무런 상관이 없는 기본적인 jpa 사용방식이다.
~~~java
public interface ContentRepositoryV1 extends JpaRepository<ContentV1, Long> {
}
~~~

##### step2
사용자 정의 레포지토리 사용을 위해 사용자 정의 레포지토리 인터페이스를 하나 생성한다.  

~~~java
public interface ContentRepositoryCustomV1 {
    List<ContentV1> findContentsByName(String name);
}
~~~


##### step3
사용자 정의 레포지토리 인터페이스를 상속한다.  
그리고 거기에 `step0`에서 생성한 `jpaQueryFactory` 빈을 이용하여 자유롭게 조회로직을 작성한다.  
여기서 `selectFrom(contentV1)`에 `contentV1`이 없어서 컴파일 오류가 날 것이다.  
querydsl은 기본적으로 `Q클래스`를 만들고 이를 이용하여 조회한다.  
그리고 범용적인 사용에서는 `Q클래스`를 `static import`하여 아래와 같이 사용한다.

~~~java
import static com.study.jpa.ch2.v1.entity.QContentV1.contentV1;

@RequiredArgsConstructor
@Repository
public class ContentRepositoryCustomV1Impl implements ContentRepositoryCustomV1{

    private final JPAQueryFactory factory;

    @Override
    public List<ContentV1> findContentsByName(String name) {
        return factory.selectFrom(contentV1)
                .where(contentV1.name.eq(name))
                .fetch();
    }
}
~~~

##### step4
`step1`에서 생성한 `ContentRepositoryV1` 에서 `step3`에서 생성한 클래스를 상속하도록한다.  
~~~java
public interface ContentRepositoryV1 extends JpaRepository<ContentV1, Long>, ContentRepositoryCustomV1 {
}
~~~

그리고 자유롭게 사용하면 된다.  
기본적인 테스트코드는 아래와 같다.  

~~~java
@Test
void querydsl1() {
    // save
    ContentV1 content = new ContentV1();
    content.setName("name1");
    contentRepositoryV1.save(content);

    // select
    List<ContentV1> contents = contentRepositoryV1.findContentsByName("name1");
    assertEquals(1, contents.size());

    // delete
    contentRepositoryV1.deleteAll();
}
~~~