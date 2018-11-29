package com.innocv.crm.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This proxy is created in order to simplify the UserRepository class testing.
 * UserRepository class depends on RestHighLevelClient class which methods are declared
 * as final. Final methods cannot be stubbed by Mockito, so this class offers non final
 * methods.
 *
 */
@Slf4j
@Component
public class RestHighLevelClientProxy {

    @Autowired
    @Qualifier("crm-cluster-client")
    private RestHighLevelClient client;

    public GetResponse get(GetRequest getRequest) throws IOException {
        return client.get(getRequest);
    }

    public SearchResponse search(SearchRequest searchRequest) throws IOException {
        return client.search(searchRequest);
    }

    public IndexResponse index(IndexRequest indexRequest) throws IOException{
        return client.index(indexRequest);
    }

    public UpdateResponse update(UpdateRequest updateRequest) throws IOException {
        return client.update(updateRequest);
    }

}