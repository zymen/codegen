package net.zymen.codegen.service

import spock.lang.Specification

class OutputBuilderServiceTest extends Specification {
    OutputBuilderService outputBuilderService = new OutputBuilderService()

    def "should generate correct output for simple template and map"() {
        when:
            def result = outputBuilderService.output("how are you {{username}}?", ['username': 'Adam'])

        then:
            "how are you Adam?" == result
    }
}
