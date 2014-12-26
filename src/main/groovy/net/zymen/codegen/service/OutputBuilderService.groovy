package net.zymen.codegen.service

import com.github.jknack.handlebars.Handlebars
import net.zymen.codegen.template.Template

class OutputBuilderService {
    def output(Template template, Map<String, Object> parameters) {
        output(template.content, parameters)
    }

    def output(String template, Map<String, Object> parameters) {
        Handlebars handlebars = new Handlebars()
        com.github.jknack.handlebars.Template templateObject = handlebars.compileInline(template)
        templateObject.apply(parameters)
    }
}
