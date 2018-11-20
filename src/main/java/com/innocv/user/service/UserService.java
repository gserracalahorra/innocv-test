package com.innocv.user.service;

import com.innocv.user.repository.UserRepository;
import com.innocv.user.service.domain.User;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    @Qualifier("domain-entity")
    private UserConversor userConversor;

    @Autowired
    private UserRepository userRepository;

    public Mono<Map<String, Object>> index(User user) {
        return userRepository
                .index(userConversor.fromDomainToEntity(user))
                .map(this::responseToMap);
    }

    private Map<String, Object> responseToMap(IndexResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", response.getId());
        map.put("index", response.getIndex());
        map.put("type", response.getType());
        map.put("version", response.getVersion());
        map.put("result", response.getResult().getLowercase());
        map.put("seqNo", response.getSeqNo());
        map.put("primaryTerm", response.getPrimaryTerm());
        return map;
    }
}