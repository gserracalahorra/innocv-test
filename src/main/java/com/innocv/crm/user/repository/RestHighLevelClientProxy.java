package com.innocv.crm.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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

}