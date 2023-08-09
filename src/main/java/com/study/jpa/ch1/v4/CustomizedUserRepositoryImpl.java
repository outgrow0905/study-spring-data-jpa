package com.study.jpa.ch1.v4;

import com.study.jpa.ch1.v1.entity.UserV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository{

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void customMethod(UserV1 user) {
        // logic...
        log.info("user: {}", user.getName());
    }
}
