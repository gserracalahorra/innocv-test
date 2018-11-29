package com.innocv.crm.user.controller;

import com.innocv.crm.user.controller.converter.UserConverter;
import com.innocv.crm.user.controller.model.UserModel;
import com.innocv.crm.user.service.UserService;
import com.innocv.crm.user.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/crm/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("model-domain")
    private UserConverter userConverter;

    @GetMapping("/{id}")
    public UserModel find(@PathVariable("id") String id) {
        User user = userService.findById(id);
        return userConverter.fromDomainToModel(user);
    }

    @GetMapping("/all")
    public List<UserModel> findAll() {
        List<User> userList = userService.findAll();
        return userList.stream().map(userConverter::fromDomainToModel)
                .collect(Collectors.toList());
    }

}