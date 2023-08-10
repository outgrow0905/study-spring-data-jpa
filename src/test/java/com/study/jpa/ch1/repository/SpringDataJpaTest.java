package com.study.jpa.ch1.repository;

import com.study.jpa.ch1.v1.entity.AddressV1;
import com.study.jpa.ch1.v1.entity.UserV1;
import com.study.jpa.ch1.v1.repository.UserRepositoryV1;
import com.study.jpa.ch1.v2.UserRepositoryV2;
import com.study.jpa.ch1.v3.UserRepositoryV3;
import com.study.jpa.ch1.v4.UserRepositoryV4;
import com.study.jpa.ch1.v5.entity.UserV2;
import com.study.jpa.ch1.v5.repository.UserRepositoryV5;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class SpringDataJpaTest {
    @Autowired
    private UserRepositoryV1 userRepositoryV1;

    @Autowired
    private UserRepositoryV2 userRepositoryV2;

    @Autowired
    private UserRepositoryV3 userRepositoryV3;

    @Autowired
    private UserRepositoryV4 userRepositoryV4;

    @Autowired
    private UserRepositoryV5 userRepositoryV5;

    @Test
    void queryMethod1() {
        List<UserV1> users = userRepositoryV1.findByNameAndAddressCity("name1", "city1", PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name")));
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

    @Test
    void queryAnnotation() {
        List<UserV1> users = userRepositoryV2.findByAddressCity("hello");
    }

    @Test
    void queryRewriter() {
        userRepositoryV3.findByName("name1");
    }

    @Test
    void modifyingAnnotation1() {
        UserV1 user1 = new UserV1();
        user1.setName("name1");
        userRepositoryV3.save(user1);

        userRepositoryV3.updateByName("name1", "name2");

        UserV1 findUser = userRepositoryV3.findById(user1.getId()).get();

        log.info("name: {}", findUser.getName());

        userRepositoryV3.deleteAll();
    }

    @Test
    void paging1() {
        UserV1 user1 = new UserV1();
        user1.setName("name1");
        user1.setAddress(new AddressV1(null, "street1", null));
        userRepositoryV3.save(user1);

        Page<UserV1> users = userRepositoryV1.findByNameAndAddressStreet("name1", "street1", PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "name")));
        log.info("count: {}", users.getTotalElements());
    }

    @Test
    void customRepository() {
        UserV1 user = new UserV1();
        user.setName("name1");
        userRepositoryV4.customMethod(user);
    }

    @Test
    void save1() {
        UserV1 user1 = new UserV1();
        user1.setName("name1");
        userRepositoryV4.save(user1);
    }

    @Test
    void save2() {
        UserV2 user1 = new UserV2();
        user1.setId(1L);
        user1.setName("name1");
        userRepositoryV5.save(user1);

        userRepositoryV5.deleteAll();
    }
}