package com.innocv.crm.user.reporitory;

import com.innocv.crm.user.exception.InternalServerException;
import com.innocv.crm.user.repository.RestHighLevelClientProxy;
import com.innocv.crm.user.repository.UserRepository;
import org.elasticsearch.action.get.GetResponse;
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
    public void successIndexTest() throws IOException {
        GetResponse getResponse = RepositoryObjectsFactory.buildExistingGetResponse();

        when(client.get(any())).thenReturn(getResponse);

        GetResponse indexResponse = userRepository.findById("1234");

        assertNotNull(indexResponse);
    }


    @Test(expected = InternalServerException.class)
    public void failIndexTest() throws IOException {
        when(client.get(any())).thenThrow(new IOException());

        userRepository.findById("1234");
    }

}