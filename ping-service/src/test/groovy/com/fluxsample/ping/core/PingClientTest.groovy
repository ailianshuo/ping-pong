package com.fluxsample.ping.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification
@SpringBootTest
class PingClientTest extends Specification {
    /*
    def "SayHello"() {
        given:
        def webClient =Mock(WebClient)
        def  b = Mock(WebClient.Builder)
        def pingClient = new PingClient(builder: b,webClient:webClient)
        when:
        def response = pingClient.sayHello("Hello")
        then:
        response.block().entity().value == "Hello"
    }
    */

}
