package com.innocv.crm.user.reporitory;

import com.innocv.crm.user.JsonStringFactory;
import com.innocv.crm.user.exception.InternalServerException;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.repository.UserRepository;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();

    private RestHighLevelClient client = PowerMockito.mock(RestHighLevelClient.class);

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
    public void failIndexTest() throws IOException {
        when(client.index(any())).thenThrow(new IOException());

        userRepository.index(JsonStringFactory.getUserJson());
    }

    @Test
    public void successUpdateTest() throws IOException {
        UpdateResponse updateResponse = mock(UpdateResponse.class);

        when(client.update(any())).thenReturn(updateResponse);

        UpdateResponse response = userRepository.update("1", mock(Map.class));

        assertNotNull(response);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void resourceNotFoundUpdateTest() throws IOException {
        ElasticsearchStatusException exception = mock(ElasticsearchStatusException.class);

        when(client.update(any())).thenThrow(exception);

        when(exception.status()).thenReturn(RestStatus.NOT_FOUND);

        userRepository.update("1", mock(Map.class));
    }

    @Test(expected = InternalServerException.class)
    public void failUpdateTest() throws IOException {
        when(client.update(any())).thenThrow(new IOException());

        userRepository.update("1", mock(Map.class));
    }

    @Test
    public void successDeleteTest() throws IOException {
        DeleteResponse deleteResponse = mock(DeleteResponse.class);

        when(client.delete(any())).thenReturn(deleteResponse);

        DeleteResponse response = userRepository.delete("1");

        assertNotNull(response);
    }

    @Test(expected = InternalServerException.class)
    public void failDeleteTest() throws IOException {
        when(client.delete(any())).thenThrow(new IOException());

        userRepository.delete("1");
    }

}