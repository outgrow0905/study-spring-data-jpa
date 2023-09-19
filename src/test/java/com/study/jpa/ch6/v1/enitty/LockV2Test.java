package com.study.jpa.ch6.v1.enitty;

import com.study.jpa.ch6.v1.repository.ABoardRepositoryV1;
import com.study.jpa.ch6.v1.repository.AReplyRepositoryV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Rollback(value = false)
class LockV2Test {
    @Autowired
    private ABoardRepositoryV1 aBoardRepositoryV1;

    @Autowired
    private AReplyRepositoryV1 aReplyRepositoryV1;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void lock1() throws Exception {
        // board
        ABoard board1 = aBoardRepositoryV1.findABoardById(1L);

        // reply
        AReply reply = new AReply();
        reply.setText("text3");
        reply.setBoard(board1);

        // add reply
        board1.getReplies().add(reply);
    }

}