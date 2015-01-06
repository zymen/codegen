package net.zymen.codegen.factory

import net.zymen.codegen.Configuration
import net.zymen.codegen.template.FileTemplate
import net.zymen.codegen.template.Template

class TemplateFactory {
    static Template fromFile(String filename) {
        new FileTemplate(Configuration.TEMPLATES_DIR + "/" + filename)
    }
}
