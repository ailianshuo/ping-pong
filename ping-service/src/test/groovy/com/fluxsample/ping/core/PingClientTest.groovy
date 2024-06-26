package com.fluxsample.ping.core


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Consumer
import java.util.function.Function

@SpringBootTest
class PingClientTest extends Specification {
    def "SayHello"() {
        given:
        def webClient = Mock(WebClient)
        def b = Mock(WebClient.Builder)
        def headerUriSpec = Mock(WebClient.RequestHeadersUriSpec)
        def uriSpec = Mock(WebClient.UriSpec)
        def responseSpec = Mock(WebClient.ResponseSpec)
        def mono = Mock(Mono)
        def ex = Mock(WebClientResponseException)
        when:
        b.baseUrl(_) >> b
        b.build() >> webClient
        def pingClient = new PingClient(b)

        webClient.get() >> headerUriSpec
        headerUriSpec.uri(_) >> headerUriSpec
        headerUriSpec.accept(_) >> headerUriSpec
        headerUriSpec.retrieve() >> responseSpec
        responseSpec.bodyToMono(_) >> mono
        mono.onErrorMap(_, _) >> mono
        Consumer<String> consumer = {}
        mono.subscribe(consumer) >> {args -> args[0].accept("Hello")}

        def response = pingClient.sayHello()
        then:
        response != null
    }

    @Unroll
    def "getErrorMap"() {
        given:
        def webClient = Mock(WebClient)
        def b = Mock(WebClient.Builder)
        def ex = Mock(WebClientResponseException)
        when:
        b.baseUrl(_) >> b
        b.build() >> webClient
        def pingClient = new PingClient(b)

        Function<WebClientResponseException, Throwable> function = pingClient.getErrorMap()
        ex.getStatusCode() >> statusCode
        Throwable result = function.apply(ex);
        then:
        expected == (result != null)
        where:
        statusCode                     || expected
        HttpStatus.TOO_MANY_REQUESTS   || true
        HttpStatus.SERVICE_UNAVAILABLE || true
        HttpStatus.NOT_FOUND           || true
    }
}
