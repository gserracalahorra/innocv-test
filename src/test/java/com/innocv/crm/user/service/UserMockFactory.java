package com.innocv.crm.user.service;

import java.util.HashMap;
import java.util.Map;

public class UserMockFactory {

    public static Map<String, Object> createValidUserMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "Guillem Serra Calahorra");
        userMap.put("birthday", "1990-08-02");
        return userMap;
    }

}