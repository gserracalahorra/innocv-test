package com.innocv.crm.user.reporitory;

import com.innocv.crm.user.JsonStringFactory;
import com.innocv.crm.user.exception.InternalServerException;
import com.innocv.crm.user.repository.RestHighLevelClientProxy;
import com.innocv.crm.user.repository.UserRepository;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();

    @Mock
    private RestHighLevelClientProxy client;

    @Before
    public void before() {
        ReflectionTestUtils.setField(userRepository, "client", client);
    }

    @Test
    public void successFindByIdTest() throws IOException {
        GetResponse getResponse = mock(GetResponse.class);

        when(client.get(any())).thenReturn(getResponse);

        GetResponse response = userRepository.findById("1234");

        assertNotNull(response);
    }


    @Test(expected = InternalServerException.class)
    public void failFindByIdTest() throws IOException {
        when(client.get(any())).thenThrow(new IOException());

        userRepository.findById("1234");
    }

    @Test
    public void successFindAllTest() throws IOException {
        SearchResponse searchResponse = mock(SearchResponse.class);

        when(client.search(any())).thenReturn(searchResponse);

        SearchResponse response = userRepository.findAll();

        assertNotNull(response);
    }

    @Test(expected = InternalServerException.class)
    public void failFindAllTest() throws IOException {
        when(client.search(any())).thenThrow(new IOException());

        userRepository.findAll();
    }

    @Test
    public void successIndexTest() throws IOException {
        IndexResponse indexResponse = mock(IndexResponse.class);

        when(client.index(any())).thenReturn(indexResponse);

        IndexResponse response = userRepository.index(JsonStringFactory.getUserJson());

        assertNotNull(response);
    }

    @Test(expected = InternalServerException.class)
    public void failIndexAllTest() throws IOException {
        when(client.index(any())).thenThrow(new IOException());

        userRepository.index(JsonStringFactory.getUserJson());
    }

}