package com.study.jpa.ch2.v1.repository;

import com.study.jpa.ch2.v1.entity.ContentV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class QueryDslTest {
    @Autowired
    private ContentRepositoryV1 contentRepositoryV1;

    @Test
    void querydsl1() {
        // save
        ContentV1 content = new ContentV1();
        content.setName("name1");
        contentRepositoryV1.save(content);

        // select
        List<ContentV1> contents = contentRepositoryV1.findContentsByName("name1");
        assertEquals(1, contents.size());

        // delete
        contentRepositoryV1.deleteAll();
    }
}