package com.study.jpa.ch1.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;

@Slf4j
public class MyQueryRewriter implements QueryRewriter {
    @Override
    public String rewrite(String query, Sort sort) {
        return query.replaceAll("my_user", "my_new_user");
    }
}
