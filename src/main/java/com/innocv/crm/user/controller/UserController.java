package com.innocv.crm.user.controller;

import com.innocv.crm.user.controller.converter.UserConverter;
import com.innocv.crm.user.controller.model.UserModel;
import com.innocv.crm.user.service.UserService;
import com.innocv.crm.user.service.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Api(value = "crm-user-api", description = "Users API", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("model-domain")
    private UserConverter userConverter;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by ID", notes = "Returns user by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Requested user exists"),
            @ApiResponse(code = 404, message = "Requested user does not exist")
    })
    public UserModel find(@PathVariable("id") String id) {
        User user = userService.findById(id);
        return userConverter.fromDomainToModel(user);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all users", notes = "Returns all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exits one user at least"),
            @ApiResponse(code = 204, message = "There are no user in the system")
    })
    public List<UserModel> findAll() {
        List<User> userList = userService.findAll();
        return userList.stream().map(userConverter::fromDomainToModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation(value = "Create a user", notes = "Creates a user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User successfully created")
    })
    public ResponseEntity<Map<String, Object>> create(@RequestBody UserModel user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userConverter.fromModelToDomain(user)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Get all users", notes = "Returns all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User has ben updated"),
            @ApiResponse(code = 404, message = "User requested for update does not exist")
    })
    public Map<String, Object> update(@PathVariable("id") String id, @RequestBody UserModel user) {
        user.setId(id);
        return userService.update(userConverter.fromModelToDomain(user));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Get all users", notes = "Returns all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User has ben deleted")
    })
    public Map<String, Object> delete(@PathVariable("id") String id) {
        return userService.delete(id);
    }

}