package com.innocv.crm.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innocv.crm.user.controller.model.UserModel;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.repository.UserRepository;
import com.innocv.crm.user.service.converter.UserConverter;
import com.innocv.crm.user.service.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("domain-repository")
    private UserConverter userConverter;

    @Autowired
    private ObjectMapper objectMapper;

    public User findById(String id) {
        log.debug("Retrieving user {}", id);
        GetResponse response = userRepository.findById(id);

        if (response.isExists()) {
            Map<String, Object> responseMap = response.getSourceAsMap();
            User user = userConverter.fromMapToDomain(responseMap);
            log.debug("Retrieved user {}", id);
            return user;
        }

        throw new ResourceNotFoundException(id);
    }

}