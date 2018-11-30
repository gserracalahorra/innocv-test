package com.innocv.crm.user.repository;

import com.innocv.crm.user.exception.InternalServerException;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class UserRepository {

    private final String INDEX = "crm";

    private final String TYPE = "user";

    @Autowired
    @Qualifier("crm-cluster-client")
    private RestHighLevelClient client;

    public GetResponse findById(String id) {
        try {
            log.debug("Permorming GET request to /{}/{}/{}", INDEX, TYPE, id);
            GetResponse getResponse = client.get(new GetRequest(INDEX, TYPE, id));
            log.debug("Permormed GET request to /{}/{}/{}", INDEX, TYPE, id);
            return getResponse;
        }
        catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InternalServerException(e.getClass(), e.getMessage());
        }
    }

    public SearchResponse findAll() {
        try {
            log.debug("Permorming GET all users request to /{}/{}", INDEX, TYPE);
            SearchRequest searchRequest = new SearchRequest(INDEX);
            searchRequest.types(TYPE);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest);
            log.debug("Permormed GET all users request to /{}/{}", INDEX, TYPE);
            return searchResponse;
        }
        catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InternalServerException(e.getClass(), e.getMessage());
        }
    }

    public IndexResponse index(String json) {
        try {
            log.debug("Permorming POST request to /{}/{}", INDEX, TYPE);
            final IndexRequest indexRequest = new IndexRequest(INDEX, TYPE);
            indexRequest.source(json, XContentType.JSON);
            IndexResponse indexResponse = client.index(indexRequest);
            log.debug("Permormed POST request to /{}/{}", INDEX, TYPE);
            return indexResponse;
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InternalServerException(e.getClass(), e.getMessage());
        }
    }

    public UpdateResponse update(String id, Map<String, Object> user) {
        try {
            log.debug("Permorming PUT request to /{}/{}/{}", INDEX, TYPE, id);
            final UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id);
            updateRequest.doc(user);
            UpdateResponse updateResponse = client.update(updateRequest);
            log.debug("Permormed PUT request to /{}/{}/{}", INDEX, TYPE, id);
            return updateResponse;
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InternalServerException(e.getClass(), e.getMessage());
        } catch (ElasticsearchStatusException e) {
            if (e.status().equals(RestStatus.NOT_FOUND)) {
                throw new ResourceNotFoundException(id);
            }
            throw e;
        }
    }

    public DeleteResponse delete(String id) {
         try {
             log.debug("Permorming DELETE request to /{}/{}/{}", INDEX, TYPE, id);
             DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
             DeleteResponse deleteResponse = client.delete(deleteRequest);
             log.debug("Permormed DELETE request to /{}/{}/{}", INDEX, TYPE, id);
             return deleteResponse;
         } catch (IOException e) {
             log.error(ExceptionUtils.getStackTrace(e));
             throw new InternalServerException(e.getClass(), e.getMessage());
         }
    }

}