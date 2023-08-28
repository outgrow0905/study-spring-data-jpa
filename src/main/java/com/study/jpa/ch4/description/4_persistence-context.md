#### 영속성 컨텍스트와 엔티티 비교
영속성 컨텍스트 내에서 엔티티 비교를 심화적으로 알아보자.  
예시에 사용할 간단한 엔티티는 아래와 같다.

~~~java
@Entity
public class EComputerV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
~~~


##### 영속성 컨텍스트가 같을 때 엔티티 비교
컴퓨터 엔티티의 서비스 코드와 테스트코드를 같이보자.  
`saveComputer`에 `@Transactional` 어노테이션을 설정하였다.  

~~~java
@Service
@RequiredArgsConstructor
public class EComputerServiceV1 {
    private final EComputerRepositoryV1 eComputerRepositoryV1;

    @Transactional // *
    public Long saveComputer(EComputerV1 computer) {
        return eComputerRepositoryV1.save(computer).getId();
    }
}
~~~
~~~java
@Test
@Transactional // *
void compareEntity4() {
    // given
    EComputerV1 computer1 = new EComputerV1();
    computer1.setName("computer name1");

    // when
    eComputerServiceV1.saveComputer(computer1);

    // then
    EComputerV1 findComputer = eComputerRepositoryV1.findById(computer1.getId()).get();
    assertSame(computer1, findComputer); // same
}
~~~

트렌젝션의 흐름은 테스트코드에 `@Transactional`가 붙어있으므로,  
테스트코드를 실행할 때에 트렌젝션을 열고 시작을 한다.  
그리고, 서비스단에 `@Transactional`가 붙어있는데 이미 트렌젝션 시작시점에 트렌젝션이 열려있으므로 이를 그대로 사용하게 된다.  
같은 트렌젝션을 이용한다는 의미이다.  
그리고 나머지 로직을 수행하고 트렌젝션을 닫고 코드는 종료된다.  
스프링에서 같은 트렌젝션을 이용한다는 의미는 같은 영속성 컨텍스트를 이용한다는 의미이기도 하다.  
따라서 위의 테스트코드는 최종적으로 성공한다.  
(사실 서비스코드에서 `@Transactional`을 제거해도 성공하지만 예시를 위해 추가하였다.)  

아래의 예시로 바로 넘어가서 비교해보자.  

##### 영속성 컨텍스트가 다를 때 엔티티 비교  
위의 설정과 한가지만 다르게 하고 테스트코드를 수행할 것이다.  
테스트코드에서 `@Transactional`를 주석처리하는 것이다.  
그리고 테스트코드 성공을 위해 비교함수를 `assertNotSame`으로 변경하였다.  

~~~java
@Test
//@Transactional // *
void compareEntity5() {
    // given
    EComputerV1 computer1 = new EComputerV1();
    computer1.setName("computer name1");

    // when
    // tx1 start
    eComputerServiceV1.saveComputer(computer1);
    // tx1 end
        
    // then
    // tx2 start
    EComputerV1 findComputer = eComputerRepositoryV1.findById(computer1.getId()).get(); // tx2
    // tx2 end
    assertNotSame(computer1, findComputer); // not same
}
~~~

테스트코드에서 `@Transactional`만 변경했을 뿐인데 엔티티비교의 결과가 달라지는 큰 변화가 생겼다.  
이유는 트렌젝션과 영속성 컨텍스트의 관계에 있다.  
 
위의 테스트코드에서 트렌젝션은 언제 열리고 닫히는지 생각해보자.    
`compareEntity4` 테스트에서와 달리 `compareEntity5` 테스트에서는 시작시에 트렌젝션이 열리지는 않는다.  
그리고 `saveComputer()` 시작시에 트렌젝션(`tx1`)이 처음으로 새로 생성될 것이다. 중요한 점은 `saveComputer()`가 끝나고 바로 트렌젝션이 닫힌다는 것이다.  
`compareEntity4`는 테스트 코드 시작시에 트렌젝션을 생성하고 `saveComputer()`시점에는 이미 열려있는 트렌젝션을 재활용하니 `saveComputer()`가 끝나더라도 트렌젝션을 닫지 않았다.  

그리고 `findById()`시점에는 조회를 위해 새로운 트렌젝션(`tx2`)을 생성하고 또 조회가 끝나면 트렌젝션을 닫을 것이다.  

이것은 큰 차이를 낳는다.  
트렌젝션의 시작과 끝이 곧 영속성 컨텍스트의 시작과 끝이기 때문이다.  
영속성 컨텍스트가 다르다면 같은 키 값을 가진 엔티티라도 결코 동등성비교에서 성공할 수 없다.  
마치 서로다른 `map`에 같은 `키`값으로 객체를 보관하고 동등성비교를 하는것과 같은 꼴이다.  
따라서 `compareEntity5` 테스트코드는 `not same`하게 되는 것이다.

