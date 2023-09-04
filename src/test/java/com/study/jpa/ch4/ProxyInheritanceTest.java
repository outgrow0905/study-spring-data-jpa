package com.study.jpa.ch4;

import com.study.jpa.ch1.v1.entity.BookV1;
import com.study.jpa.ch1.v1.entity.ItemV1;
import com.study.jpa.ch1.v1.entity.OrderItemV1;
import com.study.jpa.ch1.v1.repository.BookRepositoryV1;
import com.study.jpa.ch1.v1.repository.OrderItemRepositoryV1;
import com.study.jpa.ch4.v2.entity.BookV2;
import com.study.jpa.ch4.v2.entity.ItemV2;
import com.study.jpa.ch4.v2.entity.OrderItemV2;
import com.study.jpa.ch4.v2.repository.BookRepositoryV2;
import com.study.jpa.ch4.v2.repository.OrderItemRepositoryV2;
import com.study.jpa.ch4.v3.entity.BookV3;
import com.study.jpa.ch4.v3.entity.ItemV3;
import com.study.jpa.ch4.v3.entity.OrderItemV3;
import com.study.jpa.ch4.v3.repository.BookRepositoryV3;
import com.study.jpa.ch4.v3.repository.OrderItemRepositoryV3;
import com.study.jpa.ch4.v3.visitor.VisitorImpl1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProxyInheritanceTest {
    @Autowired
    private BookRepositoryV1 bookRepositoryV1;

    @Autowired
    private OrderItemRepositoryV1 orderItemRepositoryV1;

    @Autowired
    private BookRepositoryV2 bookRepositoryV2;

    @Autowired
    private OrderItemRepositoryV2 orderItemRepositoryV2;

    @Autowired
    private BookRepositoryV3 bookRepositoryV3;

    @Autowired
    private OrderItemRepositoryV3 orderItemRepositoryV3;

    @PersistenceContext
    private EntityManager entityManager;

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

        if (proxyItem instanceof BookV1) { // false
            BookV1 book = (BookV1) proxyItem;
            log.info("book: {}", book);
        }

        assertTrue(proxyItem instanceof ItemV1);
        assertFalse(proxyItem instanceof BookV1);
        assertFalse(proxyItem.getClass() == BookV1.class);
    }

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

    @Test
    @Transactional
    @Rollback(value = false)
    void inheritance3() {
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

        // unproxy
        BookV1 book = (BookV1) Hibernate.unproxy(item);
        log.info("book: {}", book.getClass());

        assertSame(book.getClass(), BookV1.class);
        assertTrue(book instanceof BookV1);
        assertTrue(book instanceof ItemV1);
        assertNotSame(item, book); // not same
    }

    @Test
    @Transactional
    void inheritance4() {
        // save
        BookV2 saveBook = new BookV2();
        saveBook.setName("book name1");
        saveBook.setAuthor("book author1");
        bookRepositoryV2.save(saveBook);

        OrderItemV2 saveOrderItem = new OrderItemV2();
        saveOrderItem.setItem(saveBook);
        orderItemRepositoryV2.save(saveOrderItem);

        // clear persistence context
        entityManager.clear();

        OrderItemV2 orderItem = orderItemRepositoryV2.findById(saveOrderItem.getId()).get();
        ItemV2 item = orderItem.getItem(); // lazy load
        String bookView = item.getView();
        log.info("book view: {}", bookView);
    }

    @Test
    @Transactional
    void inheritance5() {
        // save
        BookV3 saveBook = new BookV3();
        saveBook.setName("book name1");
        saveBook.setAuthor("book author1");
        bookRepositoryV3.save(saveBook);

        OrderItemV3 saveOrderItem = new OrderItemV3();
        saveOrderItem.setItem(saveBook);
        orderItemRepositoryV3.save(saveOrderItem);

        // clear persistence context
        entityManager.clear();

        OrderItemV3 orderItem = orderItemRepositoryV3.findById(saveOrderItem.getId()).get();
        ItemV3 item = orderItem.getItem(); // lazy load
        item.accept(new VisitorImpl1());
    }
}