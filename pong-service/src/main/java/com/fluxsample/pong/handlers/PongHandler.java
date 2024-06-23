package com.fluxsample.pong.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class PongHandler {

    public Mono<ServerResponse> handleSay(ServerRequest request) {
        return ServerResponse.ok().body(sayHello(request), String.class);
    }

    private Mono<String> sayHello(ServerRequest request) {
        Optional<String> paramOptional = request.queryParam("say");
        String sayContent = paramOptional.isPresent() ? paramOptional.get() : "";
        return Mono.just(  sayContent.equalsIgnoreCase("Hello")?"World" :"" );
    }

}
