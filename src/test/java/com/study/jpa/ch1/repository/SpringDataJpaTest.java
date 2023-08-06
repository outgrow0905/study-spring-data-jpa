package com.study.jpa.ch1.repository;

import com.study.jpa.ch1.v1.entity.UserV1;
import com.study.jpa.ch1.v1.repository.UserRepositoryV1;
import com.study.jpa.ch1.v2.UserRepositoryV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SpringDataJpaTest {
    @Autowired
    private UserRepositoryV1 userRepositoryV1;

    @Autowired
    private UserRepositoryV2 userRepositoryV2;

    @Test
    void queryMethod1() {
        List<UserV1> users = userRepositoryV1.findByNameAndAddressCity("name1", "city1", PageRequest.of(0, 10));
        long counts =  userRepositoryV1.countUserByName("name1");
        userRepositoryV1.deleteUserByName("name1");
    }

    @Test
    void queryMethod2() {
        Optional<UserV1> user = userRepositoryV2.findById(1l);
        user.ifPresent(userV1 -> {
            // logic..
        });
    }
}