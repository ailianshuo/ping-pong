package com.fluxsample.pong

import spock.lang.Specification;

class PongServiceApplicationTest extends Specification {

    def "test application"() {
        given:

        String[] args = [];

        when:
        PongServiceApplication.main(args)

        then:
        noExceptionThrown()

    }

}
