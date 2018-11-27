package com.innocv.crm.user.controller.model;

import java.time.LocalDate;

public class UserModelFactory {

    public static UserModel buildValid() {
        return new UserModel("1234", "Guillem Serra Calahorra", LocalDate.of(1990, 2, 8));
    }

}