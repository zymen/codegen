package net.zymen.codegen.service

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template

class OutputBuilderService {
    def output(String template, Map<String, Object> parameters) {
        Handlebars handlebars = new Handlebars()
        Template templateObject = handlebars.compileInline(template)
        templateObject.apply(parameters)
    }
}
