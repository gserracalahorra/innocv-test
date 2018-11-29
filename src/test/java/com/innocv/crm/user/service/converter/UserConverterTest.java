package com.innocv.crm.user.service.converter;

import com.innocv.crm.user.service.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserConverterTest {

    private UserConverter userConverter = new UserConverter();

    @Test
    public void fromMapToDomainTest() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", "1234");
        userMap.put("name", "Guillem Serra Calahorra");
        userMap.put("birthday", "1990-08-02");

        User userDomain = userConverter.fromMapToDomain(userMap);

        assertEquals("1234", userDomain.getId());
        assertEquals("Guillem Serra Calahorra", userDomain.getName());
        assertEquals(LocalDate.of(1990, 8, 2), userDomain.getBirthday());
    }

}