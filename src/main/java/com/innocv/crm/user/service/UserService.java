package com.innocv.crm.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innocv.crm.user.exception.ContentNotFoundException;
import com.innocv.crm.user.exception.InternalServerException;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.repository.UserRepository;
import com.innocv.crm.user.service.converter.ElasticsearchConverter;
import com.innocv.crm.user.service.converter.UserConverter;
import com.innocv.crm.user.service.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("domain-repository")
    private UserConverter userConverter;

    @Autowired
    private ElasticsearchConverter elasticsearchConverter;

    @Autowired
    private ObjectMapper objectMapper;

    public User findById(String id) {
        log.debug("Retrieving user {}", id);
        GetResponse response = userRepository.findById(id);

        if (response.isExists()) {
            Map<String, Object> responseMap = response.getSourceAsMap();
            User user = userConverter.fromMapToDomain(responseMap);
            log.debug("User {} retrieved", id);
            return user;
        }

        throw new ResourceNotFoundException(id);
    }

    public List<User> findAll() {
        log.debug("Retrieving all users");
        SearchResponse response = userRepository.findAll();
        SearchHits hitsList = response.getHits();

        if (hitsList.getTotalHits() > 0) {
            List<User> result = Arrays.asList(hitsList.getHits()).stream()
                    .map(e -> {
                        final Map<String, Object> fields = e.getSourceAsMap();
                        fields.put("id", e.getId());
                        return fields;
                    })
                    .map(userConverter::fromMapToDomain)
                    .collect(Collectors.toList());
            log.debug("All users retrieved");
            return result;
        }

        throw new ContentNotFoundException();
    }

    public Map<String, Object> create(User user) {
        log.debug("Creating user {}", user);
        try {
            final String json = objectMapper.writeValueAsString(user);
            Map<String, Object> indexResponse = elasticsearchConverter.fromIndexResponseToMap(userRepository.index(json));
            log.debug("User {} created", user);
            return indexResponse;
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InternalServerException(e.getClass(), e.getMessage());
        }
    }

}