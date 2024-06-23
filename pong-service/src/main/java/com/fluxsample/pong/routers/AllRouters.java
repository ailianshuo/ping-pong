package com.fluxsample.pong.routers;


import com.fluxsample.pong.handlers.PongHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Slf4j
@Configuration
public class AllRouters {
    // 定义路由
    RouterFunctions.Builder globalRouterBuilder = RouterFunctions.route();
    @Bean
    RouterFunction<ServerResponse> pongRouter(PongHandler pongHandler){
        // 动态定义路由(接口)信息
        return globalRouterBuilder
                .GET("/pong", pongHandler::handleSay)
                .build();
    }


}
