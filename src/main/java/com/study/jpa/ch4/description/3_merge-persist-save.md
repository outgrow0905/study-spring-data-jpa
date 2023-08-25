#### merge, persist, save
jpa에서 제공하는 `merge()`, `persist()`와 spring jpa에서 제공하는 `save()`를 심층적으로 알아보자.

~~~java
@Entity
public class ETeamV1 {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;
}
~~~

##### merge(), persist()
영속성 컨텍스트의 관점에서 `merge()`와 `persist()`의 차이를 알아보자.
`merge()`와 `persist()`는 모두 `input`으로 엔티티를 받지만,  
`persist()`는 반환타입이 없는 `void`이고, `merge()`는 엔티티를 반환한다.

그렇다면 `merge()`는 `input`, `output`으로 둘 다 엔티티를 처리하는데, 두 엔티티는 같을까?  
처음에는 같다고 생각했다.  
jpa를 처음 공부할떄에 영속성 컨텍스트에서 관리하는 엔티티는 동일성을 보장한다고 했기 때문이다. [참조](https://github.com/outgrow0905/study-jpa/blob/main/src/main/java/com/study/jpa/ch1/description/2_persistence-context.md#%EB%8F%99%EC%9D%BC%EC%84%B1-%EB%B3%B4%EC%9E%A5)  

그러나 테스트코드를 돌려보면 둘은 명백히 메모리주소가 다르다.

~~~java
@Test
@Transactional
void compareEntity1() {
    ETeamV1 team1 = new ETeamV1(1L, "team name1");
    ETeamV1 merge1 = entityManager.merge(team1);
        
    assertNotSame(merge1, team1); // not same pass
}
~~~

`team1`, `merge1` 둘은 모든 필드값이 같은데 메모리주소는 왜 다를까?

`merge()`는 말 그대로 병합작업이다.  
기존에 `1L`의 키값을 가진 다른 엔티티가 이미 영속성 컨텍스트에 있었다면, `name` 필드인 `team name1`의 값을 덮어쓸 것이다.  
혹은 `address`필드가 채워져 있었다면, 그대로 남겨두고 `name` 필드만 비교해서 넣거나 덮어쓸 것이다.  

예를 들어, `merge()`이전에 영속성 컨텍스트에, `id, name, address` 순서로 `1L, team name0, team address1`이 들어있었다면,  
`merge()`에 `1L, team name1, null`의 값을 넣었으니,  
`merge()`이후에 영속성 컨텍스트에는 `1L, team name1, team address1`이 들어있을 것이다.
그리고, `merge()`의 `output`은 영속성 컨텍스트에 현재값인 `1L, team name1, team address1`이 반환될 것이다.  

따라서, `merge()`의 `input, output`은 다르다.  
그렇다면 `input`으로 받은 파라미터에 값을 변경해서 반환하면 되지않는가?  
라고 생각할 수 있지만, jpa에서는 그런 방법을 택하지 않은 것으로 보인다.  

`persist()`는 어떨까?  
아래의 예시를 보자.

~~~java
@Test
@Transactional
void compareEntity2() {
    // given
    ETeamV1 team1 = new ETeamV1(1L, "team name1");

    // when
    entityManager.persist(team1);

    // then
    ETeamV1 findTeam = eTeamRepositoryV1.findById(1L).get();
    assertSame(team1, findTeam); // same pass
}
~~~

`persist()`는 무조건 `insert`를 수행한다.  
영속성 컨텍스트에 엔티티가 있는지 확인하거나, 데이터베이스에 같은 키값으로 데이터가 있는지 확인하지 않는다.  
영속성 컨텍스트에 `1L`의 키값으로 엔티티가 등록되어있다면 오류가 난다.    
만약 데이터베이스에 키값 `1L`으로 이미 데이터가 있어도 아래와 같은 오류가 나게 된다.  

`org.springframework.dao.DataIntegrityViolationException: could not execute statement [Duplicate entry '1' for key 'ETeamV1.PRIMARY']`

`persist()`가 성공했다면 그것은 영속성 컨텍스트에 해당 `key`값으로 엔티티가 없었고, 데이터베이스에도 해당 `key`값으로 데이터가 없었다는 의미이다.  
그리고, `input`으로 넣은값이 그대로 영속성 컨텍스트에 들어있다는 의미이다.    
(`merge()`와 비교해보라. `merge()`는 `input`으로 넣은 값이 그대로 영속성 컨텍스트에 있다는 보장이 없다.)
그렇다면 `input, output`이 무조건 같은데 `output`을 반환하는 것은 더 혼란이 있을 수 있으니, `void`로 `input` 값을 그대로 사용하도록 정책을 정한것으로 보인다.  

이 정도로 `persist()`와 `merge()`의 차이를 정리하자.  
이 개념을 가지고 spring jpa의 `save()`기능을 테스트해보자.  




##### save()
`save()`는 spring jpa에서 제공하는 기능이다.  
기존에 `save()`메서드 안에있는 `isNew()`를 복습해보자. [참고](https://github.com/outgrow0905/study-spring-data-jpa/blob/main/src/main/java/com/study/jpa/ch1/description/1_spring-data-jpa.md#isnew)

~~~java
@Transactional
@Override
public <S extends T> S save(S entity) {
    Assert.notNull(entity, "Entity must not be null");
    
    if (entityInformation.isNew(entity)) {
        em.persist(entity);
        return entity;
    } else {
        return em.merge(entity);
    }
}
~~~

코드를 다시 보니 `isNew()`가 참이면 `persist()`를 호출하고, 반대라면 `merge()`가 호출된다.  
그리고 `isNew()`의 기본적인 로직은 `key`값으로 영속성 컨텍스트에 엔티티가 존재하는지 여부 정도로 알고 있으면 된다.  

이제 아래의 테스트코드는 성공한다.

~~~java
@Test
@Transactional
void compareEntity3() {
    // given
    ETeamV1 team1 = new ETeamV1(1L, "team name1");

    // when
    ETeamV1 savedTeam = eTeamRepositoryV1.save(team1); // spring jpa의 save() 메서드를 사용한다.

    // then
    ETeamV1 findTeam = eTeamRepositoryV1.findById(savedTeam.getId()).get();
    assertNotSame(team1, findTeam); // not same
}
~~~

왜 `team1`과 `findTeam`은 다를까?  
그 이유는 맨 위의 `ETeamV1` 클래스의 `@GeneratedValue(strategy = GenerationType.IDENTITY)` 속성을 주석처리 헀기 떄문이다.  
`save()`의 `isNew()`에서 `input`으로 받은 `team1`은 `key` 값을 가지고 있다.  
따라서 `persist()`가 아닌 `merge()`가 호출된다.  
그리고 `merge()`의 `output`으로는 `input`으로 받은 객체(`team1`)가 아닌 영속성 컨텍스트의 데이터와 대조한 후 병합된 엔티티를 반환한다.  

`ETeamV1` 클래스의 `@GeneratedValue(strategy = GenerationType.IDENTITY)`의 주석처리를 해제하고 테스트를 해보자.  
`key`값으로 `null`을 넣어야 하니 해당부분만 변경하고 아래와 같이 테스트코드를 수행해보자.  

~~~java
@Test
@Transactional
void compareEntity3() {
    // given
    ETeamV1 team1 = new ETeamV1( "team name1"); // id null

    // when
    ETeamV1 savedTeam = eTeamRepositoryV1.save(team1);

    // then
    ETeamV1 findTeam = eTeamRepositoryV1.findById(savedTeam.getId()).get();
    assertSame(team1, findTeam); // same
}
~~~

성공하는 이유를 `merge()`가 아닌 `persist()`가 호출되었기 때문이다.  
(혹시 실패한다면 테이블을 `drop` 하고 `key`값에 `auto_increment`을 설정하여 다시 생성하면 해결될 수 있다.)
`key`값이 없는채로 `save()`가 호출되었으니 `isNew()`는 `true`이고 `persist()`가 호출된다.  
그리고 spring jpa는 반환타입이 없던 pure jpa와 달리 `input`으로 받은 파라미터를 그대로 리턴한다.  
따라서 위의 테스트는 성공한다.  