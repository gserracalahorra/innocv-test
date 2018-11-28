package com.innocv.crm.user.service.domain;

import java.time.LocalDate;

public class UserDomainFactory {

    public static User buildValid() {
        return new User("1234", "Guillem", LocalDate.of(1990, 2, 8));
    }

}