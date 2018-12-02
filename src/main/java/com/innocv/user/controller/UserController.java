package com.innocv.user.controller;

import com.innocv.user.controller.model.User;
import com.innocv.user.exception.ContentNotFoundException;
import com.innocv.user.exception.ResourceNotFoundException;
import com.innocv.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/crm/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Flux<User> getAll() {
        return userRepository.findAll()
                .switchIfEmpty(Mono.error(new ContentNotFoundException()));
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    @PostMapping
    public Mono<ResponseEntity<User>> save(@RequestBody User user) {
        return userRepository.save(user)
                .map(e -> ResponseEntity.status(HttpStatus.CREATED).body(e));
    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") String id, @RequestBody User user) {
        user.setId(id);
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .doOnSuccess(e -> userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return userRepository.deleteById(id);
    }

}