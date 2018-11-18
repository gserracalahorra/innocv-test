package com.innocv.user.handler;

import com.innocv.user.repository.entity.User;
import org.springframework.stereotype.Component;

@Component("domain-entity")
public class UserConverter {

    public User fromDomainToEntity(com.innocv.user.handler.domain.User domain) {
        User entity = new User();
        entity.setName(domain.getName());
        entity.setBirthday(domain.getBirthday());
        return entity;
    }

}