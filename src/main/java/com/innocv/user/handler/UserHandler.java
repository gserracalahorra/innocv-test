package com.innocv.user.handler;

import com.innocv.user.handler.domain.User;
import com.innocv.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("domain-entity")
    private UserConverter userConverter;

    public void index(User user) {
        log.info("Persisting {}", user);
        userRepository.index(userConverter.fromDomainToEntity(user));
    }

}