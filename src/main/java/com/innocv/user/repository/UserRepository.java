package com.innocv.user.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innocv.user.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

@Component
@Slf4j
public class UserRepository extends ElasticAsincrhonousRepository {

    @Autowired
    @Qualifier("crm-cluster-client")
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    public Mono<IndexResponse> index(User doc) {
        return indexDoc(doc)
                .onErrorMap(ex -> new ElasticsearchException("Unable to connect to Elasticseacrh. May be it is down"));
    }

    private Mono<IndexResponse> indexDoc(User doc) {
        Mono<IndexResponse> indexResponseMono = Mono.create(sink -> {
            try {
                doIndex(doc, listenerToSink(sink));
            } catch (JsonProcessingException e) {
                sink.error(e);
            }
        });
        indexResponseMono.subscribe(e -> log.info("Saving document: /{}/{}/{}", e.getIndex(), e.getType(), e.getId()));
        return indexResponseMono;
    }

    private void doIndex(User doc, ActionListener<IndexResponse> listener) throws JsonProcessingException {
        final IndexRequest indexRequest = new IndexRequest("crm", "user");
        final String json = objectMapper.writeValueAsString(doc);
        indexRequest.source(json, XContentType.JSON);
        client.indexAsync(indexRequest, listener);
    }

}