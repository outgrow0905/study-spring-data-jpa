package com.study.jpa.ch1.v5.entity;

import com.study.jpa.ch1.v1.entity.AddressV1;
import com.study.jpa.ch1.v1.entity.BaseEntityV1;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserV2 extends BaseEntityV1 implements Persistable<Long> {
    @Id
    @Column(name = "USER_ID")
    private Long id;
    private String name;
    private AddressV1 address;

    @CreatedDate
    private LocalDateTime createdTime;


    @Override
    public boolean isNew() {
        return createdTime == null;
    }
}
