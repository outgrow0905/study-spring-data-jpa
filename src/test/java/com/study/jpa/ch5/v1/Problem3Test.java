package com.study.jpa.ch5.v1;

import com.study.jpa.ch5.v2.enitty.GMemberV1;
import com.study.jpa.ch5.v2.repository.GMemberRepositoryV1;
import com.study.jpa.ch5.v2.repository.GOrderRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class Problem3Test {
    @Autowired
    private GMemberRepositoryV1 gMemberRepositoryV1;

    @Autowired
    private GOrderRepositoryV1 gOrderRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
//    @Transactional
    @Transactional(readOnly = true)
    void problem1() {
        SharedSessionContractImplementor sharedSessionContractImplementor = entityManager.unwrap(SharedSessionContractImplementor.class);
        log.info("FlushMode: {}", sharedSessionContractImplementor.getHibernateFlushMode());

        org.hibernate.engine.spi.PersistenceContext persistenceContext = sharedSessionContractImplementor.getPersistenceContext();
        List<GMemberV1> members = gMemberRepositoryV1.findMembersByColumns();
        for(GMemberV1 member : members) {
            log.info("isLoaded: {}", entityManager.contains(member));
        }

        members.forEach(member -> {
            EntityEntry entity = persistenceContext.getEntry(member);

            // check snapshot 1
            Object[] states = entity.getLoadedState();
            if (states != null) {
                Arrays.stream(states).forEach(o -> log.info("Snapshot1: {}", o));
            } else {
                log.info("SnapShop1: {}", states);
            }

            // update
            member.setName("name1");
            member.setAddress("address1");

            // flush
            entityManager.flush();

            // check snapshot 2
            states = entity.getLoadedState();
            if (states != null) {
                Arrays.stream(states).forEach(o -> log.info("Snapshot2: {}", o));
            } else {
                log.info("SnapShop2: {}", states);
            }
        });
    }

    @Test
    @Transactional
    void problem2() {
        EntityManagerFactory factory = entityManager.getEntityManagerFactory();
        SessionFactory sf = factory.unwrap(SessionFactory.class);
        StatelessSession statelessSession = sf.openStatelessSession();
        Transaction transaction = statelessSession.getTransaction();
        transaction.begin();

        // logic
        List<GMemberV1> members = statelessSession.createQuery("select m from HMemberV1 m", GMemberV1.class).getResultList();

        for(GMemberV1 member : members) {
            log.info("isLoaded: {}", entityManager.contains(member)); // persistence context 에 엔티티 등록하지 않음
        }

        transaction.commit();
    }
}