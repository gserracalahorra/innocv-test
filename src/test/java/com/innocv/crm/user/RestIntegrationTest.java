package com.innocv.crm.user;

import com.innocv.crm.user.controller.model.UserModel;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
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
    public void getAllUsersOkTest() throws Exception {
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
        SearchHit[] hits = {hit1, hit2};

        when(searchHits.getHits()).thenReturn(hits);

        when(client.search(any())).thenReturn(searchResponse);

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .body(containsString("Guillem Serra"))
                .statusCode(is(200));
    }

    @Test
    public void getAllUsersNotFoundTest() throws Exception {
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
    public void getAllUsersInternalServerErrorTest() throws Exception {
        IOException ioException = mock(IOException.class);

        when(ioException.getMessage()).thenReturn("Message of mock exception");

        when(client.search(any())).thenThrow(ioException);

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void getUserOkTest() throws Exception {
        GetResponse getResponse = mock(GetResponse.class);

        when(getResponse.isExists()).thenReturn(true);

        when(getResponse.getSourceAsMap()).thenReturn(buildUserMap());

        when(client.get(any())).thenReturn(getResponse);

        given().port(port).get("/v1/crm/user/12345678").then()
                .assertThat()
                .body(containsString("Guillem Serra"))
                .statusCode(is(200));
    }

    @Test
    public void getUserResourceNotFoundTest() throws Exception {
        GetResponse getResponse = mock(GetResponse.class);

        when(getResponse.isExists()).thenReturn(false);

        when(client.get(any())).thenReturn(getResponse);

        given().port(port).get("/v1/crm/user/12345678").then()
                .assertThat()
                .statusCode(is(404));
    }

    @Test
    public void getUserInternalServerErrorTest() throws Exception {

        when(client.get(any())).thenThrow(mock(IOException.class));

        given().port(port).get("/v1/crm/user/12345678").then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void indexSuccessTest() throws IOException {
        IndexResponse indexResponse = mock(IndexResponse.class);

        when(indexResponse.getId()).thenReturn("1");
        when(indexResponse.getIndex()).thenReturn("crm");
        when(indexResponse.getType()).thenReturn("user");
        when(indexResponse.getVersion()).thenReturn(1L);
        when(indexResponse.getResult()).thenReturn(DocWriteResponse.Result.CREATED);
        when(indexResponse.getSeqNo()).thenReturn(1L);

        when(client.index(any())).thenReturn(indexResponse);

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new UserModel(null, "Guillem Serra", LocalDate.now()))
                .post("/v1/crm/user")
                .then()
                .assertThat()
                .statusCode(is(201));
    }

    @Test
    public void indexInternalServerErrorTest() throws IOException {
        when(client.index(any())).thenThrow(mock(IOException.class));

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new UserModel(null, "Guillem Serra", LocalDate.now()))
                .post("/v1/crm/user")
                .then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void updateSuccessTest() throws IOException {
        UpdateResponse updateResponse = mock(UpdateResponse.class);

        when(updateResponse.getId()).thenReturn("1");
        when(updateResponse.getIndex()).thenReturn("crm");
        when(updateResponse.getType()).thenReturn("user");
        when(updateResponse.getVersion()).thenReturn(1L);
        when(updateResponse.getResult()).thenReturn(DocWriteResponse.Result.CREATED);
        when(updateResponse.getSeqNo()).thenReturn(1L);

        when(client.update(any())).thenReturn(updateResponse);

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new UserModel(null, "Guillem Serra", LocalDate.now()))
                .put("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void updateNotFoundTest() throws IOException {
        ElasticsearchStatusException e = mock(ElasticsearchStatusException.class);

        when(e.status()).thenReturn(RestStatus.NOT_FOUND);

        when(client.update(any())).thenThrow(e);

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new UserModel(null, "Guillem Serra", LocalDate.now()))
                .put("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(404));
    }

    @Test
    public void updateInternalServerErrorTest() throws IOException {
        when(client.update(any())).thenThrow(mock(IOException.class));

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new UserModel(null, "Guillem Serra", LocalDate.now()))
                .put("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void deleteSuccessTest() throws IOException {
        DeleteResponse deleteResponse = mock(DeleteResponse.class);

        when(deleteResponse.getId()).thenReturn("1");
        when(deleteResponse.getIndex()).thenReturn("crm");
        when(deleteResponse.getType()).thenReturn("user");
        when(deleteResponse.getVersion()).thenReturn(1L);
        when(deleteResponse.getResult()).thenReturn(DocWriteResponse.Result.CREATED);
        when(deleteResponse.getSeqNo()).thenReturn(1L);

        when(client.delete(any())).thenReturn(deleteResponse);

        given().port(port)
                .delete("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void deleteInternalServerErrorTest() throws IOException {
        when(client.update(any())).thenThrow(mock(IOException.class));

        given().port(port)
                .delete("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(500));
    }

    private Map<String, Object> buildUserMap(){
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("id", "12345678");
        sourceMap.put("name", "Guillem Serra");
        sourceMap.put("birthday", "1990-08-01");
        return sourceMap;
    }

}