package com.fluxsample.ping.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.function.Function;

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
        final String url = UriComponentsBuilder.fromUriString("/pong")
                .queryParam("say", "Hello")
                .build()
                .toUriString();
        Mono<String> sFlux = webClient.get()
                .uri(  url)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(WebClientResponseException.class, getErrorMap()) ;
        sFlux.subscribe(result -> log.info("Request sent & Pong Respond.") ) ;
        return sFlux;

    }
    public Function<WebClientResponseException, Throwable> getErrorMap() {
        return exception -> {
            if (exception.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                log.warn("Request send & Pong throttled it");
            } else if (exception.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                log.warn("Pong service is unavailable");
            } else {
                log.warn(exception.getMessage());
            }
            return exception;
        };
    }
}
