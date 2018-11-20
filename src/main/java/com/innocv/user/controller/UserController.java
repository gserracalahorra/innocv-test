package com.innocv.user.controller;

import com.innocv.user.controller.model.User;
import com.innocv.user.controller.model.UserExceptionResponse;
import com.innocv.user.service.UserService;
import org.apache.http.nio.protocol.ErrorResponseProducer;
import org.elasticsearch.ElasticsearchException;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/crm/user")
public class UserController {

    @Autowired
    @Qualifier("model-domain")
    private UserConversor userConversor;

    @Autowired
    private UserService userService;

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> indexUser(User user) {
        return userService.index(userConversor.fromModelToDomain(user))
                .map(ResponseEntity::ok);
    }

    @ExceptionHandler(ElasticsearchException.class)
    public Mono<ResponseEntity<UserExceptionResponse>> handleElasticSearchConnectionFail(ElasticsearchException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(UserExceptionResponse.create(ex.getClass(), ex.getDetailedMessage())));
    }

}