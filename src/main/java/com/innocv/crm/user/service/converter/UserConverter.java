package com.innocv.crm.user.service.converter;

import com.innocv.crm.user.service.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component("domain-repository")
public class UserConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public User fromMapToDomain(final Map<String, Object> fields) {
        String id = (String) fields.get("id");
        String name = (String) fields.get("name");
        String birthday = (String) fields.get("birthday");
        return new User(id, name, LocalDate.parse(birthday, formatter));
    }

}