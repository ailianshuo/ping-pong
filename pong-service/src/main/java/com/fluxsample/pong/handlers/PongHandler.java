package com.fluxsample.pong.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import com.fluxsample.pong.limits.PongLimitControl;
@Component
public class PongHandler {

    @Autowired
    private  PongLimitControl pongLimitControl;
    public Mono<ServerResponse> handleSay(ServerRequest request) {
        if (Boolean.TRUE.equals(pongLimitControl.isCanPong()) ) {
            return ServerResponse.ok().body(sayHello(request), String.class);
        } else {
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).bodyValue("Rate limit exceeded");
        }
    }

    private Mono<String> sayHello(ServerRequest request) {
        Optional<String> paramOptional =  request.queryParam("say") ;
        String sayContent =  "" ;
        if ( Optional.ofNullable(paramOptional).isPresent() ) {
            sayContent = paramOptional.get() ;
        }
        return Mono.just(  sayContent.equalsIgnoreCase("Hello")?"World" :"Please say 'Hello'." ) ;
    }

}
