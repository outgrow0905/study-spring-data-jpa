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

가장 쉽게 꺼낼 수 있지만 이방법의 주의할 점은 `assertNotSame(item, book);` 이 통과한다는 것이다.  
이 의미는 영속성 컨텍스트에 `item, book`이 둘 다 등록되어있는 상태를 의미하기도 한다.  
(실제로 `book`의 데이터를 변경하면 jpa의 `변경감지기능`이 동작한다.)  
같은 키값을 가지고 있는데 다른 객체이니 영속성컨텍스트에 불안정하게 둘 다 등록될 수 있는 것이다.  
영속성 컨텍스트 입장에서는 키값이 같은 (ex. `id=1`)의 서로다른 두 객체를 보관하고 있을 뿐이다.  
따라서 `Hibernate.unproxy()` 기능은 원본 엔티티가 꼭 필요한 곳에서만 잠깐 사용하도록 하는것이 좋다.


##### 별도 인터페이스 제공



##### visitor pattern