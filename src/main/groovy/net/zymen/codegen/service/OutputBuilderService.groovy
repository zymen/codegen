package net.zymen.codegen.service

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Helper
import com.github.jknack.handlebars.Options
import net.zymen.codegen.template.Template

class OutputBuilderService {

    def output(Template template, Map<String, Object> parameters) {
        output(template.content, parameters)
    }

    def output(String template, Map<String, Object> parameters) {
        Handlebars handlebars = new Handlebars()
        handlebars.registerHelper('toLowerCase', new Helper<String>() {
            public CharSequence apply(String input, Options options) {
                return input.toLowerCase()
            }
        })

        handlebars.registerHelper('toGroovyType', new Helper<String>() {
            public CharSequence apply(String input, Options options) {
                if (input.toLowerCase().equals("text")) {
                    return "String"
                }

                return "def"
            }
        })


        com.github.jknack.handlebars.Template templateObject = handlebars.compileInline(template)
        templateObject.apply(parameters)
    }
}
