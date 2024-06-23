package com.fluxsample.pong

import com.fluxsample.pong.handlers.PongHandler
import org.mockito.Mockito
import org.springframework.web.reactive.function.server.ServerRequest
import spock.lang.Specification

class PongHandlerTest extends Specification {

    def "say content to Pong"() {
        given:
        def controller = new PongHandler()
        def request = Mock(ServerRequest.class)
        when:" say Hello"
        request.queryParam("say") >> Optional.of("Hello")
        def responseMono = controller.handleSay(request)
        def response = responseMono.block()

        then:
        response.statusCode().value() == 200

        cleanup:
        Mockito.reset(controller)
        Mockito.reset(request)
    }
}
