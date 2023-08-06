package com.study.jpa.ch1.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MyBaseRepositoryV2<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findById(ID id);
}
