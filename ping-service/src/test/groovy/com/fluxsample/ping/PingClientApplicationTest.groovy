package com.fluxsample.ping;

import spock.lang.Specification;

class PingClientApplicationTest extends Specification {

    def "test application"() {
        given:

        String[] args = [];
        when:
        PingServiceApplication.main(args)

        then:
        noExceptionThrown()

    }

}
