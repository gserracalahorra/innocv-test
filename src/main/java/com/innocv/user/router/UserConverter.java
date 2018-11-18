package com.innocv.user.router;

import com.innocv.user.router.model.User;
import org.springframework.stereotype.Component;

@Component("model-domain")
public class UserConverter {

    public User fromDomainToModel(com.innocv.user.handler.domain.User domain) {
        User model = new User();
        model.setName(model.getName());
        model.setBirthday(model.getBirthday());
        return model;
    }

    public com.innocv.user.handler.domain.User fromModelToDomain(User model) {
        com.innocv.user.handler.domain.User domain = new com.innocv.user.handler.domain.User();
        domain.setName(model.getName());
        domain.setBirthday(model.getBirthday());
        return domain;
    }

}