package com.innocv.crm.user.controller.converter;

import com.innocv.crm.user.controller.model.UserModel;
import com.innocv.crm.user.service.domain.User;
import org.springframework.stereotype.Component;

@Component("model-domain")
public class UserConverter {

    public UserModel fromDomainToModel(User domain) {
        return new UserModel(domain.getId(), domain.getName(), domain.getBirthday());
    }

}