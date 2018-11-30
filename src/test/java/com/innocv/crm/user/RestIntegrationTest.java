package com.innocv.crm.user;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestIntegrationTest {

    @LocalServerPort
    private int port;

    @MockBean
    private RestHighLevelClient client;

    @Test
    public void getAllUsersOk() throws Exception {
        SearchResponse searchResponse = mock(SearchResponse.class);
        SearchHits searchHits = mock(SearchHits.class);

        when(searchResponse.getHits()).thenReturn(searchHits);

        when(searchHits.getTotalHits()).thenReturn(2L);

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("name", "Guillem Serra");
        sourceMap.put("birthday", "1990-08-01");

        SearchHit hit1 = mock(SearchHit.class);
        when(hit1.getId()).thenReturn("1");
        when(hit1.getSourceAsMap()).thenReturn(sourceMap);

        SearchHit hit2 = mock(SearchHit.class);
        when(hit2.getId()).thenReturn("1");
        when(hit2.getSourceAsMap()).thenReturn(sourceMap);
        SearchHit [] hits = {hit1, hit2};

        when(searchHits.getHits()).thenReturn(hits);

        when(client.search(any())).thenReturn(searchResponse);

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void getAllUsersNotFound() throws Exception {
        SearchResponse searchResponse = mock(SearchResponse.class);
        SearchHits searchHits = mock(SearchHits.class);

        when(searchResponse.getHits()).thenReturn(searchHits);

        when(searchHits.getTotalHits()).thenReturn(0L);

        when(client.search(any())).thenReturn(searchResponse);

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(404));
    }

    @Test
    public void getAllUsersInternalServerError() throws Exception {
        IOException ioException = mock(IOException.class);

        when(ioException.getMessage()).thenReturn("Message of mock exception");

        when(client.search(any())).thenThrow(ioException);

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(500));
    }

}