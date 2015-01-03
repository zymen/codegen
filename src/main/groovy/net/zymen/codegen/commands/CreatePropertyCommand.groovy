package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.CommandExecutionBuilder
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class CreatePropertyCommand implements Command {
    Context context

    String name

    String dataType

    String entity

    public CreatePropertyCommand() {
    }

    @Override
    def execute() {
        println "Adding property with name ${name} for entity ${entity}"

        CommandExecutionBuilder.start()
            .inContext(context)
            .forTemplateGroup("create-property")
            .useFileTemplatesInOrder(
                "${dataType}.property.template",
                "default.property.template"
            )
            .registerTemplateCommandParameters(this)
            .registerTemplateContext()
            .insideTopPackageDirectory("model")
            .appendOutputToFile("${entity}")
            .run()
    }
}
