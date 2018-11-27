package com.innocv.crm.user.controller;

import com.innocv.crm.user.controller.model.UserModel;
import com.innocv.crm.user.controller.model.UserModelFactory;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.service.UserService;
import com.innocv.crm.user.controller.converter.UserConverter;
import com.innocv.crm.user.service.domain.User;
import com.innocv.crm.user.service.domain.UserDomainFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
    public void findCustomerByIdTest() {
        User userDomain = UserDomainFactory.buildValid();

        when(userService.findById(any())).thenReturn(userDomain);

        UserModel userModel = UserModelFactory.buildValid();

        when(userConverter.fromDomainToModel(any())).thenReturn(userModel);

        UserModel userResponse = userController.find("1234");

        assertNotNull(userResponse);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findCustomerById_NotFoundTest() {
        when(userService.findById(any())).thenThrow(new ResourceNotFoundException("1234"));
        userController.find("1234");
    }

}