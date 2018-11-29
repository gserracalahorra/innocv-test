package com.innocv.crm.user.service.converter;

import com.innocv.crm.user.service.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *  Use this class to convert elasticsearch maps to user domain and viceversa
 */
@Component("domain-repository")
public class UserConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Map<String, Object> fromDomainToMap(final User domain) {
        final Map<String, Object> user = new HashMap<>();

        if (!Objects.isNull(domain.getId())) {
            user.put("id", domain.getId());
        }

        user.put("name", domain.getName());
        user.put("birthday", domain.getBirthday());
        return user;
    }

    public User fromMapToDomain(final Map<String, Object> fields) {
        String id = (String) fields.get("id");
        String name = (String) fields.get("name");
        String birthday = (String) fields.get("birthday");
        return new User(id, name, LocalDate.parse(birthday, formatter));
    }

}