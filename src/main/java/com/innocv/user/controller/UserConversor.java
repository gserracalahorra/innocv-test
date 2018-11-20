package com.innocv.user.controller;

import com.innocv.user.service.domain.User;
import org.springframework.stereotype.Component;

@Component("model-domain")
public class UserConversor {

    public User fromModelToDomain(com.innocv.user.controller.model.User model) {
        User domain = new User();
        domain.setName(model.getName());
        domain.setBirthday(model.getBirthday());
        return domain;
    }

    public com.innocv.user.controller.model.User fromDomainToModel(User domain) {
        com.innocv.user.controller.model.User model = new com.innocv.user.controller.model.User();
        model.setName(domain.getName());
        model.setBirthday(model.getBirthday());
        return model;
    }

}