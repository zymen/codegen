package net.zymen.codegen.service

import net.zymen.codegen.factory.TemplateFactory
import spock.lang.Specification

class OutputBuilderServiceTest extends Specification {
    OutputBuilderService outputBuilderService = new OutputBuilderService()

    def "should generate correct output for simple template and map"() {
        when:
            def result = outputBuilderService.output("how are you {{username}}?", ['username': 'Adam'])

        then:
            "how are you Adam?" == result
    }

    def "should be possible to use template file for generating output"() {
        when:
            def result = outputBuilderService.output(TemplateFactory.fromFile("template.example"), ['username': 'Adam'])

        then:
            "Simple template from file for Adam" == result
    }
}
