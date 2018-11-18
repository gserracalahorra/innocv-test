package com.innocv.user.router;

import com.innocv.user.router.model.User;
import com.innocv.user.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.BodyExtractors.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Configuration
@Slf4j
public class RouterApi implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routeUsers(UserHandler userHandler, UserConverter userConverter) {
        return RouterFunctions
                .route(POST("/v1/crm/user").and(accept(MediaType.APPLICATION_JSON)), req -> req.body(toMono(User.class))
                        .map(user -> userConverter.fromModelToDomain(user))
                        .doOnNext(userHandler::index)
                        .onErrorMap(e -> postExceptionHandler(e))
                        .then(ok().build()));
    }

    private Throwable postExceptionHandler(Throwable e) {

        return e;
    }

}

