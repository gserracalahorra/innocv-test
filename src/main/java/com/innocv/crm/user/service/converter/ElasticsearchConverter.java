package com.innocv.crm.user.service.converter;

import org.elasticsearch.action.index.IndexResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Use this class to convert elasticsearch entities to maps and viceversa (like Index, Update and Delete)
 *
 */
@Component
public class ElasticsearchConverter {

    public Map<String, Object> fromIndexResponseToMap(IndexResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", response.getId());
        map.put("index", response.getIndex());
        map.put("type", response.getType());
        map.put("version", response.getVersion());
        map.put("result", response.getResult().getLowercase());
        map.put("seqNo", response.getSeqNo());
        map.put("primaryTerm", response.getPrimaryTerm());
        return map;
    }

}