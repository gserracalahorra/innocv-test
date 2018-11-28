package com.innocv.crm.user.repository;

import com.innocv.crm.user.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UserRepository {

    private final String INDEX = "crm";

    private final String TYPE = "user";

    @Autowired
    private RestHighLevelClientProxy client;

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

}