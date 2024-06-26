package com.fluxsample.pong.handlers;

import com.fluxsample.pong.limits.PongLimitControl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
public class PongHandler {

    private final PongLimitControl pongLimitControl;

    public PongHandler(PongLimitControl pongLimitControl) {
        this.pongLimitControl = pongLimitControl;
    }

    public Mono<ServerResponse> handleSay(ServerRequest request) {
        if (Boolean.TRUE.equals(pongLimitControl.isCanPong()) ) {
            return ServerResponse.ok().body(sayHello(request), String.class);
        } else {
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).bodyValue("Rate limit exceeded");
        }
    }

    private Mono<String> sayHello(ServerRequest request) {
        String sayContentl = request.queryParam("say").orElse("");
        return Mono.just(  sayContentl.equalsIgnoreCase("Hello")?"World" :"Please say 'Hello'." ) ;
    }

}
