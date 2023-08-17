package com.study.jpa.ch2.v1.repository;

import com.study.jpa.ch2.v1.entity.ContentV1;

import java.util.List;

public interface ContentRepositoryCustomV1 {
    List<ContentV1> findContentsByName(String name);
}
