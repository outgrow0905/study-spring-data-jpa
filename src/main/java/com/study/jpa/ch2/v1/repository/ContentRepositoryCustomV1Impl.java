package com.study.jpa.ch2.v1.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.ch2.v1.entity.ContentV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.jpa.ch2.v1.entity.QContentV1.contentV1;

@RequiredArgsConstructor
@Repository
public class ContentRepositoryCustomV1Impl implements ContentRepositoryCustomV1{

    private final JPAQueryFactory factory;

    @Override
    public List<ContentV1> findContentsByName(String name) {
        return factory.selectFrom(contentV1)
                .where(contentV1.name.eq(name))
                .fetch();
    }
}
