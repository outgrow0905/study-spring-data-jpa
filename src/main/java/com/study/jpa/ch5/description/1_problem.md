#### 성능최적화
jpa를 사용하면서 직면하게되는 다양한 문제들을 미리 학습해보자.  



##### N+1 문제
N+1 문제는 성능상 가장 주의해야하는 문제중 하나이다.  
예시로 먼저 살펴보자.  

~~~java
@Entity
public class FMemberV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_NO")
    private Long id;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<FOrderV1> orders;
}

@Entity
public class FOrderV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO")
    private FMemberV1 member;
}
~~~

`회원`과 `주문` 엔티티이고 `1:N`의 평범한 관계이다.  
이미 공부했듯이 `@OneToMany`는 `지연로딩`이고, `@ManyToOne`은 `즉시로딩`이 jpa 기본정책이다.  
그리고 테스트를 위해 `회원` 엔티티의 `@OneToMany`에 `즉시로딩`을 설정하였다.

그리고 아래와 같이 jpql 쿼리를 만들어보자.  

~~~java
public interface FMemberRepositoryV1 extends JpaRepository<FMemberV1, Long> {
    @Query("select m from FMemberV1 m")
    List<FMemberV1> findMembers();
}
~~~

이제 위의 조건을 수행해보자.

~~~java
@Test
void problem1() {
    List<FMemberV1> members = fMemberRepositoryV1.findMembers();
}
~~~

위의 테스트코드에서는 몇 번의 `select` 문이 실행될까?  
`회원` 테이블에 2개의 데이터가 들어있다고 가정해보고 생각해보라.  

![problem1](img/problem1.png)

총 3번이 수행된다.    
jpql에서 `m` 값만을 조회했으니 첫번째 `select`에서는 회원만 전체조회를 하게 된다.  
그리고 두번째 `select`에서는 첫번쨰 쿼리에서 얻은 `첫번째 회원`의 키값으로 `주문`을 하나 조회한다. `(MEMBER_NO=203)`  
그리고 세번째 `select`에서는 첫번째 쿼리에서 얻은 `두번째 회원`의 키값으로 `주문`을 하나 조회한다. `(MEMBER_NO=202)`  

어차피 키값으로 조회하는 것이고 그래봐야 세번의 조회이니 별 문제가 없어보일 수 있다.  
하지만 `회원테이블`에 `수천, 수만개`의 데이터가 들어있다면 `수천+1, 수만+1 개`의 `select`가 수행될 수 있다.  

이처럼 `즉시로딩`과 jpql를 같이 하게 될때에 N+1의 문제가 발생할 수 있다.  

그러면 `지연로딩`으로 바꾸면 해결되는가?  
그런것처럼 보인다. `회원`만 전체조회하고 `주문`은 실제 조회가 있을떄에 `select`가 수행될 것이기 때문이다.  

그렇다면 아래와 같은 로직이 있다고 가정해보자.  

~~~java
@Test
@Transactional
void problem1() {
    List<GMemberV1> members = gMemberRepositoryV1.findMembers();
    members.forEach(member -> {
        List<GOrderV1> orders = member.getOrders();
        log.info("orders size: {}", orders.size());
    });
}
~~~

똑같이 `회원` 테이블에 `2`개의 데이터가 들어있었다고 가정하면 `3`개의 `select`가 수행된다.  

![problem2](img/problem2.png)

똑같은 수의 쿼리가 수행되나(`N+1` 문제가 그대로 유지되지만) 그래도 이 상황은 `즉시로딩` 상황보다는 낫다고 생각한다.    
적어도 개발자가 `N+1` 문제가 발생할 것이라고 생각할 가능성이 높기 때문이다.  

#### 해결방안
`N+1 문제`가 발생했다고 어떻게 기술적으로 해결할 지 생각하기보다는,  
`특정 엔티티(1)`로부터 `연관 엔티티(N)`의 조회로직이 얼마나 빈번하게 발생하는지 생각해보라.  
`특정 엔티티`로부터 `연관 엔티티`를 매번 조회해야 한다면 그냥 `즉시로딩`을 설정하면 된다.   
`즉시로딩`을 지양하라고 해서 쓰지 말라는 것이 아니다. 바로 이럴때에 `즉시로딩`을 설정하라는 것이다.  

`특정 엔티티`로부터 `연관 엔티티`로의 조회가 반반의 확률이라면 어떨까?  
그러면 `지연로딩`으로 설정하고, 다른 방법을 찾자.  
가장 많이 쓰는 방법은 jpql에서 `fetch`를 사용하도록 변경하는 것이다.  

`특정 엔티티`로부터 `연관 엔티티`를 조회하지 않는 로직이면 `지연로딩`을 사용하면 되고,  
`연관 엔티티`를 조회해야하는 로직이면 `fetch`를 사용한 jpql을 사용하면 된다.  
`같은 엔티티`의 조회이지만 `2개의 메서드`가 있는것이 껄끄럽지만 성능최적화를 위해 감수할만한 상황이 있을 것이다.  
