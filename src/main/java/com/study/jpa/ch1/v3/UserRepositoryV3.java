package com.study.jpa.ch1.v3;

import com.study.jpa.ch1.v1.entity.UserV1;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepositoryV3 extends JpaRepository<UserV1, Long> {
    @Query(
        value = "select my_user from UserV1 my_user where my_user.name = :name",
        queryRewriter = MyQueryRewriter.class
    )
    List<UserV1> findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "update UserV1 u set u.name = :afterName where u.name = :beforeName and u.createdTime is null")
    int updateByName(String beforeName, String afterName);
}
