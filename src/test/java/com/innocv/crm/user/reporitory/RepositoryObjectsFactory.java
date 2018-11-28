package com.innocv.crm.user.reporitory;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.get.GetResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryObjectsFactory {

    public static GetResponse buildExistingGetResponse() {
        Map<String, DocumentField> fields = new HashMap<>();
        GetResult getResult = new GetResult("crm", "user", "1234", 1L, true, null, fields);
        return new GetResponse(getResult);
    }

    public static GetResponse buildNonExistingGetResponse() {
        Map<String, DocumentField> fields = new HashMap<>();
        GetResult getResult = new GetResult("crm", "user", "1234", 1L, false, null, fields);
        return new GetResponse(getResult);
    }

}