package com.fluxsample.ping.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PingClient {

    private final WebClient webClient ;
    public PingClient(WebClient.Builder builder)
    {

        this.webClient = builder
                .baseUrl("http://127.0.0.1:8666")
                .build();
    }
    // 向pop服务发"Hello"
    public Mono<String> sayHello()
    {
        Mono<String> sFlux = webClient.get()
                .uri(uriBuilder -> uriBuilder
                                .path("/pong")
                                .queryParam("say", "Hello")
                                .build()  )
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(WebClientResponseException.class, exception -> {
                    if (exception.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        log.error("Request send & Pong throttled it");
                    } else if (exception.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                        log.error("Pong service is unavailable");
                    } else {
                        log.error(exception.getMessage());
                    }
                    return exception;
                }) ;
        sFlux.subscribe(result -> log.info("Request sent & Pong Respond.") ) ;
        return sFlux;

    }
}
