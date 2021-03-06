package com.innocv.crm.user.controller;

import com.innocv.crm.user.controller.model.UserModel;
import com.innocv.crm.user.service.UserService;
import com.innocv.crm.user.controller.converter.UserConverter;
import com.innocv.crm.user.service.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private UserController userController = new UserController();

    @Mock
    private UserService userService;

    @Mock
    private UserConverter userConverter;

    @Before
    public void before() {
        ReflectionTestUtils.setField(userController, "userService", userService);
        ReflectionTestUtils.setField(userController, "userConverter", userConverter);
    }

    @Test
    public void findUserByIdTest() {
        when(userService.findById(any())).thenReturn(mock(User.class));

        when(userConverter.fromDomainToModel(any())).thenReturn(mock(UserModel.class));

        UserModel userResponse = userController.find("1234");

        assertNotNull(userResponse);
    }

    @Test
    public void findAllByIdTest() {
        List<User> domainList = new ArrayList<>();
        domainList.add(mock(User.class));
        domainList.add(mock(User.class));

        when(userService.findAll()).thenReturn(domainList);

        UserModel userModel = mock(UserModel.class);

        when(userConverter.fromDomainToModel(any())).thenReturn(userModel);

        List<UserModel> response = userController.findAll();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
    }

    @Test
    public void createTest() {
        User user = mock(User.class);

        when(userConverter.fromModelToDomain(any())).thenReturn(user);

        when(userService.create(user)).thenReturn(mock(Map.class));

        ResponseEntity<Map<String, Object>> response = userController.create(mock(UserModel.class));

        assertNotNull(response);
    }

    @Test
    public void updateTest() {
        User user = mock(User.class);

        when(userConverter.fromModelToDomain(any())).thenReturn(user);

        when(userService.update(user)).thenReturn(mock(Map.class));

        Map<String, Object> response = userController.update("1", mock(UserModel.class));

        assertNotNull(response);
    }

    @Test
    public void deleteTest() {
        when(userService.delete(any())).thenReturn(mock(Map.class));

        Map<String, Object> response = userController.delete("1");

        assertNotNull(response);
    }

}