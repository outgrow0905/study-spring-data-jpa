package com.study.jpa.v1.service;

import com.study.jpa.v1.entity.UserV1;
import com.study.jpa.v1.repository.UserRepositoryV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceV1Test {
    @Autowired
    private UserServiceV1 userService;

    @Autowired
    private UserRepositoryV1 userRepository;

    @Test
    void findAll() {
        UserV1 user1 = new UserV1();
        user1.setName("name1");
        userRepository.saveAndFlush(user1);
    }
}