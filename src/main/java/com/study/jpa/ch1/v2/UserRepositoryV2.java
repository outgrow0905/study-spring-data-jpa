package com.study.jpa.ch1.v2;

import com.study.jpa.ch1.v1.entity.UserV1;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepositoryV2 extends MyBaseRepositoryV2<UserV1, Long> {
    @Query("select u from UserV1 u where u.address.street = :street")
    List<UserV1> findByAddressCity(@Param("street") String street);
}
