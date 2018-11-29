package com.innocv.crm.user.service.converter;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ElasticsearchConverterTest {

    private ElasticsearchConverter elasticsearchConverter = new ElasticsearchConverter();

    @Test
    public void fromIndexResponseToMap() {
        IndexResponse indexResponse = mock(IndexResponse.class);

        when(indexResponse.getId()).thenReturn("1");
        when(indexResponse.getIndex()).thenReturn("crm");
        when(indexResponse.getType()).thenReturn("user");
        when(indexResponse.getVersion()).thenReturn(1L);
        when(indexResponse.getResult()).thenReturn(DocWriteResponse.Result.CREATED);
        when(indexResponse.getSeqNo()).thenReturn(1L);

        Map<String, Object> response = elasticsearchConverter.fromIndexResponseToMap(indexResponse);

        assertEquals("1", response.get("id"));
        assertEquals("crm", response.get("index"));
        assertEquals("user", response.get("type"));
        assertEquals(1L, response.get("version"));
        assertEquals("created", response.get("result"));
        assertEquals(1L, response.get("seqNo"));
    }

    @Test
    public void fromUpdateResponseToMap() {
        UpdateResponse updateResponse = mock(UpdateResponse.class);

        when(updateResponse.getId()).thenReturn("1");
        when(updateResponse.getIndex()).thenReturn("crm");
        when(updateResponse.getType()).thenReturn("user");
        when(updateResponse.getVersion()).thenReturn(1L);
        when(updateResponse.getResult()).thenReturn(DocWriteResponse.Result.CREATED);
        when(updateResponse.getSeqNo()).thenReturn(1L);

        Map<String, Object> response = elasticsearchConverter.fromUpdateResponseToMap(updateResponse);

        assertEquals("1", response.get("id"));
        assertEquals("crm", response.get("index"));
        assertEquals("user", response.get("type"));
        assertEquals(1L, response.get("version"));
        assertEquals("created", response.get("result"));
        assertEquals(1L, response.get("seqNo"));
    }

}