package com.innocv.user.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innocv.user.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserRepository extends ElasticReactiveRepository {

    @Autowired
    @Qualifier("crm-cluster-client")
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public void index(User doc) {
        indexDoc(doc)
                .subscribe(indexResponse -> log.info("Response: {}", indexResponse));
    }

    private Mono<IndexResponse> indexDoc(User doc) {
        return Mono.create(sink -> {
            try {
                doIndex(doc, listenerToSink(sink));
            } catch (JsonProcessingException e) {
                sink.error(e);
            }
        });
    }

    private void doIndex(User doc, ActionListener<IndexResponse> listener) throws JsonProcessingException {
        final IndexRequest indexRequest = new IndexRequest("crm", "user");
        final String json = objectMapper.writeValueAsString(doc);
        indexRequest.source(json, XContentType.JSON);
        client.indexAsync(indexRequest, listener);
    }

}