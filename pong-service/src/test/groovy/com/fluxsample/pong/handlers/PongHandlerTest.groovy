package com.fluxsample.pong.handlers

import com.fluxsample.pong.limits.PongLimitControl
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

class PongHandlerTest extends Specification {

    @Unroll
    def "say hello"() {
        given:
        def pongLimitControl = Mock(PongLimitControl.class)
        def handler = new PongHandler(pongLimitControl:pongLimitControl)
        def request = Mock(ServerRequest.class)
        when:
        pongLimitControl.isCanPong() >> Boolean.valueOf(canPong)
        request.queryParam(_) >> Optional.of(paraValue)
        Mono<ServerResponse> responseMono = handler.handleSay(request)
        ServerResponse serviceResponse = responseMono.block()
        then:
        serviceResponse.statusCode().value() == responseContent
        where:
        canPong |paraValue ||responseContent
        "true"  |"Hello"   ||HttpStatus.OK.value()
        "true"  |"hi"      ||HttpStatus.OK.value()
        "false" |"hello"   ||HttpStatus.TOO_MANY_REQUESTS.value()
        "true"  |""        ||HttpStatus.OK.value()

    }



}
