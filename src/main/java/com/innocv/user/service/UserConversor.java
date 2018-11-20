package com.innocv.user.service;

import com.innocv.user.repository.entity.User;
import org.springframework.stereotype.Component;

@Component("domain-entity")
public class UserConversor {

    public com.innocv.user.service.domain.User fromEntityToDomain(User entity) {
        com.innocv.user.service.domain.User domain = new com.innocv.user.service.domain.User();
        domain.setName(entity.getName());
        domain.setBirthday(entity.getBirthday());
        return domain;
    }

    public User fromDomainToEntity(com.innocv.user.service.domain.User domain) {
        User entity = new User();
        entity.setName(domain.getName());
        entity.setBirthday(domain.getBirthday());
        return entity;
    }

}