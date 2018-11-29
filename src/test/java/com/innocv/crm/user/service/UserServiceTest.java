package com.innocv.crm.user.service;

import com.innocv.crm.user.exception.ContentNotFoundException;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.repository.UserRepository;
import com.innocv.crm.user.service.converter.UserConverter;
import com.innocv.crm.user.service.domain.User;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService = new UserService();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @Captor
    private ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);

    @Before
    public void before() {
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        ReflectionTestUtils.setField(userService, "userConverter", userConverter);
    }

    @Test
    public void findByIdSuccessTest() {
        GetResponse getResponse = mock(GetResponse.class);

        when(getResponse.isExists()).thenReturn(true);

        when(userRepository.findById(any())).thenReturn(getResponse);

        User userDomain = mock(User.class);

        when(userConverter.fromMapToDomain(any())).thenReturn(userDomain);

        User userResponse = userService.findById("1234");

        assertNotNull(userResponse);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdNotFoundTest() {
        GetResponse getResponse = mock(GetResponse.class);

        when(getResponse.isExists()).thenReturn(false);

        when(userRepository.findById(any())).thenReturn(getResponse);

        userService.findById("1234");
    }

    @Test
    public void findAllSuccessTest() {
        SearchResponse searchResponse = mock(SearchResponse.class);

        SearchHits searchHits = PowerMockito.mock(SearchHits.class);

        SearchHit hit = PowerMockito.mock(SearchHit.class);

        SearchHit [] hits = {hit};

        when(searchResponse.getHits()).thenReturn(searchHits);

        when(searchHits.getTotalHits()).thenReturn(1L);

        when(searchHits.getHits()).thenReturn(hits);

        when(hit.getSourceAsMap()).thenReturn(UserMockFactory.createValidUserMap());

        when(hit.getId()).thenReturn("1");

        when(userRepository.findAll()).thenReturn(searchResponse);

        when(userConverter.fromMapToDomain(any())).thenReturn(mock(User.class));

        //CALL SERVICE METHOD
        List<User> result = userService.findAll();

        //CAPTURE CONVERSOR ARGUMENT
        Mockito.verify(userConverter).fromMapToDomain(captor.capture());

        Map<String, Object> interceptedUserMap = captor.getValue();

        assertNotNull(result);
        assertEquals("1", interceptedUserMap.get("id"));
        assertEquals("Guillem Serra Calahorra", interceptedUserMap.get("name"));
        assertEquals("1990-08-02", interceptedUserMap.get("birthday"));
    }

    @Test(expected = ContentNotFoundException.class)
    public void findAllNoContentTest() {
        SearchResponse searchResponse = mock(SearchResponse.class);

        SearchHits searchHits = SearchHits.empty();

        when(searchResponse.getHits()).thenReturn(searchHits);

        when(userRepository.findAll()).thenReturn(searchResponse);

        userService.findAll();
    }

}