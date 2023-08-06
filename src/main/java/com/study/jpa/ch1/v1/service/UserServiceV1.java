package com.study.jpa.ch1.v1.service;

import com.study.jpa.ch1.v1.repository.UserRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceV1 {
    private final UserRepositoryV1 userRepository;

    public void findAll() {
        userRepository.findAll();
    }
}
