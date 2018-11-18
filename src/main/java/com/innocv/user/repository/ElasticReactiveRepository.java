package com.innocv.user.repository;

import org.elasticsearch.action.ActionListener;
import reactor.core.publisher.MonoSink;

public abstract class ElasticReactiveRepository {

    protected <T> ActionListener<T> listenerToSink(MonoSink<T> sink) {
        return new ActionListener<T>() {
            @Override
            public void onResponse(T response) {
                sink.success(response);
            }

            @Override
            public void onFailure(Exception e) {
                sink.error(e);
            }
        };
    }

}