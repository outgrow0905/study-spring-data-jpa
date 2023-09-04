#### 상속관계와 프록시
프록시와 엔티티 상속에 대해서 알아보자.  
jpa 엔티티 구성에서 상속을 이용할때에는 아래의 부분을 숙지하고 사용해야 한다.

~~~java
@Test
@Transactional
void inheritance1() {
    // save
    BookV1 book1 = new BookV1();
    book1.setName("book name1");
    book1.setAuthor("book author1");
    bookRepositoryV1.save(book1);

    // clear persistence context
    entityManager.clear();

    // test
    ItemV1 proxyItem = entityManager.getReference(ItemV1.class, book1.getId());
//        ItemV1 proxyItem = bookRepositoryV1.getReferenceById(book1.getId());
    log.info("proxyItem class: {}", proxyItem.getClass());

    if (proxyItem instanceof BookV1) { // step1
        BookV1 book = (BookV1) proxyItem; // step2
        log.info("book: {}", book);
    }

    assertTrue(proxyItem instanceof ItemV1); // step3
    assertFalse(proxyItem instanceof BookV1); // step4
    assertFalse(proxyItem.getClass() == BookV1.class); // step5
}
~~~

위의 `step1`에서는 `proxyItem`이 `BookV1`의 타입이 아닌 `ItemV1`의 타입이기 때문에 조건에 걸리지 않게 된다.  
`step3, step4, step5`의 테스트가 모두 이를 검증해준다.    
추가로 만약 `if`문을 제거한다 하더라도 `step2`에서 타입캐스트 오류가 발생한다.  
`ItemV1`을 상속한 프록시와 `BookV1`은 아무런 관계가 없기 때문이다.

위의 문제가 발생했을 떄 해결할 수 있는 방법들을 알아보자.

##### Hibernate.unproxy()
위의 문제가 발생하는 시나리오를 아래와 같이 새로 작성해보자.  
`orderItem.getItem()`시점에서 `지연로딩`이 되고 위에서의 오류상황이 그대로 발생한다.  
당연히 `(BookV1)orderItem.getItem()` 으로 타입캐스팅을 해도 오류가 난다.  
`ItemV1`을 상속받은 프록시와 `BookV1` 클래스는 아무런 상관이 없기 때문이다.  

~~~java
@Test
@Transactional
void inheritance2() {
    // save
    BookV1 saveBook = new BookV1();
    saveBook.setName("book name1");
    saveBook.setAuthor("book author1");
    bookRepositoryV1.save(saveBook);

    OrderItemV1 saveOrderItem = new OrderItemV1();
    saveOrderItem.setItem(saveBook);
    orderItemRepositoryV1.save(saveOrderItem);

    // clear persistence context
    entityManager.clear();

    OrderItemV1 orderItem = orderItemRepositoryV1.findById(saveOrderItem.getId()).get();
    ItemV1 item = orderItem.getItem(); // lazy load

    log.info("item: {}", item.getClass());

    assertNotSame(item.getClass(), BookV1.class); // not same
    assertFalse(item instanceof BookV1);
    assertTrue(item instanceof ItemV1);
}
~~~

가장 쉽게 원본 클래스로 변환할 수 있는 방법은 하이버네이트에서 제공하는 `unproxy` 메서드이다.  

~~~java
@Test
@Transactional
void inheritance3() {
    /// ...

    OrderItemV1 orderItem = orderItemRepositoryV1.findById(saveOrderItem.getId()).get();
    ItemV1 item = orderItem.getItem(); // lazy load
        
    // unproxy
    BookV1 book = (BookV1) Hibernate.unproxy(item);
    log.info("book: {}", book.getClass());

    assertSame(book.getClass(), BookV1.class);
    assertTrue(book instanceof BookV1);
    assertTrue(book instanceof ItemV1);
    assertNotSame(item, book); // not same
}
~~~

가장 쉽게 꺼낼 수 있지만 이 방법의 주의할 점은 `assertNotSame(item, book);` 이 통과한다는 것이다.  
이 의미는 영속성 컨텍스트에 `item, book`이 둘 다 등록되어있는 상태를 의미하기도 한다.  
(실제로 `item, book`의 데이터를 변경하면 jpa의 `변경감지기능`이 둘 다 각각 동작한다.)  
같은 키값을 가지고 있는데 다른 객체이니 영속성컨텍스트에 불안정하게 둘 다 등록될 수 있는 것이다.  
영속성 컨텍스트 입장에서는 키값이 같은 (ex. `id=1`)의 서로다른 두 객체를 보관하고 있을 뿐이다.  
따라서 `Hibernate.unproxy()` 기능은 원본 엔티티가 꼭 필요한 곳에서만 잠깐 사용하도록 하는것이 좋다.


##### 별도 인터페이스 제공
상속한 구체적인 엔티티로 형변환해야 할 필요는 상속한 엔티티만 가지고 있는 정보를 조회해야 할 경우가 많을 것이다.  
그렇다면 인터페이스를 이용해볼 수 있다.  

~~~java
public interface TitleView {
    String getView();
}

public abstract class ItemV2 implements TitleView{
    ...
}

@Entity
@DiscriminatorValue("A")
public class AlbumV2 extends ItemV2 {
    ...
    
    @Override
    public String getView() {
        return "AlbumV2{" +
                "artist='" + artist + '\'' +
                ", etc='" + etc + '\'' +
                '}';
    }
}

@Entity
public class OrderItemV2 extends BaseEntityV1 {
    ...
    
    public void printTitleView() {
        log.info("view: {}", item.getView());
    }
}
~~~

`Item`은 `TitleView`를 구현하였고, `Item`을 상속한 세 클래스는 각각의 클래스만 가지고 있는 고유한 정보들을 조회하도록 `getView()`를 구현하였다.  
그리고 중요한 부분은 `OrderItem`의 ```printTitleView()```에서 인터페이스를 통해 사용하는 부분이다.  
이렇게 된다면 구체클래스 예를 들어, `Album`에서 새로운 데이터가 추가되어도 `OrderItem`의 `printTitleView()`를 전혀 변경하지 않아도 되는 좋은 확장성을 가져갈 수 있다.



##### visitor pattern
위의 방법에서 `Item`이 특정 인터페이스를 상속하지 않게 하면서 문제를 해결해보자.  
`visitor pattern`을 이용해볼 것이다.

`visitor` 부터 만들어보자.
~~~java
public interface Visitor {
    void visit(AlbumV3 album);
    void visit(BookV3 book);
    void visit(MovieV3 movie);
}

public class VisitorImpl1 implements Visitor{
    @Override
    public void visit(AlbumV3 album) {
        // album logic1...
    }

    @Override
    public void visit(BookV3 book) {
        // book logic1...
    }

    @Override
    public void visit(MovieV3 movie) {
        // movie logic1...
    }
}

public class VisitorImpl2 implements Visitor{
    @Override
    public void visit(AlbumV3 album) {
        // album logic2...
    }

    @Override
    public void visit(BookV3 book) {
        // book logic2...
    }

    @Override
    public void visit(MovieV3 movie) {
        // movie logic2...
    }
}
~~~

이제 `visitor`를 받을수 있도록 기존 엔티티들에 반영해보자.

~~~java
@Entity
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ItemV3 implements TitleView {
    ...
    abstract void visit(Visitor visitor);
}

@Entity
@DiscriminatorValue("A")
public class AlbumV3 extends ItemV3 {
    ...
    public void accept(Visitor visitor) {
        visitor.accept(this);
    }
}

@Entity
@DiscriminatorValue("M")
public class MovieV3 extends ItemV3 {
    ...

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
~~~

직접 사용은 아래와 같이 한다.

~~~java
@Test
@Transactional
void inheritance5() {
    ...

    OrderItemV3 orderItem = orderItemRepositoryV3.findById(saveOrderItem.getId()).get();
    ItemV3 item = orderItem.getItem(); // lazy load
    item.accept(new VisitorImpl1());
}
~~~