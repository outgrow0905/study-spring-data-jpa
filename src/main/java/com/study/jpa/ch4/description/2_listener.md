#### 리스너
엔티티의 생명주기에 따라 리스터를 설정할 수 있다.  
예를 들어 모든 엔티티 삭제 전에는 로그를 남기도록 하는 요구사항이 있을 때에 유용할 수 있다.  
생명주기부터 정리해보자.

~~~java
PostLoad: 엔티티를 영속성컨텍스트에 로드한 직후이다. 데이터베이스에서 조회한 데이터가 이미 2차캐시에 엔티티로 있었어도 호출된다.영속성컨텍스트를 refresh()해도 호출된다.
PrePersist: 엔티티를 영속성컨텍스트에 관리하기 직전에 호출된다. 만약 엔티티의 키값이 @GeneratedValue 이라면 키값이 없는 상태이다.
PostPersist: 엔티티를 데이터베이스에 실제로 저장한 직후에 호출된다. @GeneratedValue 이라도 식별자가 있는 상태이다.
PreUpdate: flush(), commit()를 호출하여 데이터베이스에 수정되기 직전에 호출된다. 주의할 부분은 실제 변경사항이 있어서 update sql이 수행될 때에만 호출되는 것이다.
PostUpdate: 엔티티의 수정사항을 데이터베이스에 수정하기 직후에 호출된다. 
PreRemove: 엔티티를 영속성 컨텍스트에서 삭제하기 직전에 호출된다. orphanRemoval같이 영속성 전이로 인해 삭제되는 경우에도 호출된다.
PostRemove: 엔티티를 데이터베이스에서 삭제한 직후에 호출된다.
~~~

#### 엔티티에 설정
리스너를 설정하는 첫번째 방식은 엔티티에 직접 설정하는 것이다.  
전부 `void` 리턴타입으로 해야하며 아래와 같이 어노테이션으로 설정하면 된다.

~~~java
@Entity
public class EComputerV1 {
    ...
    @PostLoad
    public void postLoad() {
        log.info("postLoad: {}", this);
    }

    @PrePersist
    public void prePersist() {
        log.info("prePersist: {}", this);
    }

    @PostPersist
    public void postPersist() {
        log.info("postPersist: {}", this);
    }

    @PreUpdate
    public void preUpdate() {
        log.info("preUpdate: {}", this);
    }

    @PostUpdate
    public void postUpdate() {
        log.info("postUpdate: {}", this);
    }

    @PreRemove
    public void preRemove() {
        log.info("preRemove: {}", this);
    }

    @PostRemove
    public void postRemove() {
        log.info("postRemove: {}", this);
    }
}
~~~


#### 별도 리스너 등록
별도로 리스너를 생성하여 엔티티에 붙일수도 있다.  
아래와 같이 설정하면 된다.
~~~java
public class ComputerV1Listener {
    
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(EComputerV1 computer) {
        if (computer.getId() == null) {
            log.info("[AUDIT] About to add a computer");
        } else {
            log.info("[AUDIT] About to update/delete computer: " + computer.getId());
        }
    }
    
    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(EComputerV1 computer) {
        log.info("[AUDIT] add/update/delete complete for computer: " + computer.getId());
    }
    
    @PostLoad
    private void afterLoad(EComputerV1 computer) {
        log.info("[AUDIT] computer loaded from database: " + computer.getId());
    }
}

@EntityListeners(ComputerV1Listener.class)
public class EComputerV1 {
    ...
}
~~~