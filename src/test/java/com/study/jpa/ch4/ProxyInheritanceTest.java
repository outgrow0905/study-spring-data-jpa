package com.study.jpa.ch4;

import com.study.jpa.ch1.v1.entity.BookV1;
import com.study.jpa.ch1.v1.entity.ItemV1;
import com.study.jpa.ch1.v1.entity.OrderItemV1;
import com.study.jpa.ch1.v1.repository.BookRepositoryV1;
import com.study.jpa.ch1.v1.repository.OrderItemRepositoryV1;
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
}