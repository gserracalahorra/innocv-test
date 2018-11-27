package com.innocv.crm.user.service;

import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.reporitory.RepositoryObjectsFactory;
import com.innocv.crm.user.repository.UserRepository;
import com.innocv.crm.user.service.converter.UserConverter;
import com.innocv.crm.user.service.domain.User;
import com.innocv.crm.user.service.domain.UserDomainFactory;
import org.elasticsearch.action.get.GetResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService = new UserService();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @Before
    public void before() {
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        ReflectionTestUtils.setField(userService, "userConverter", userConverter);
    }

    @Test
    public void findByIdTest() {
        GetResponse getResponse = RepositoryObjectsFactory.buildExistingGetResponse();

        when(userRepository.findById(any())).thenReturn(getResponse);

        User userDomain = UserDomainFactory.buildValid();

        when(userConverter.fromMapToDomain(any())).thenReturn(userDomain);

        User userResponse = userService.findById("1234");

        assertNotNull(userResponse);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdNotFoundTest() {
        GetResponse getResponse = RepositoryObjectsFactory.buildNonExistingGetResponse();

        when(userRepository.findById(any())).thenReturn(getResponse);

        userService.findById("1234");
    }

}