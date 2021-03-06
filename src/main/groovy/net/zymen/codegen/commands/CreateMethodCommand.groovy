package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.service.CommandExecutionBuilder

class CreateMethodCommand implements Command {
    Context context

    String entity

    String name

    String layer

    @Override
    def execute() {
        println "Creating method for entity ${entity} with name ${name} for layer ${layer}"

        CommandExecutionBuilder.start()
            .inContext(context)
            .forTemplateGroup("create-method")
            .useFileTemplatesInOrder(
                "${layer}.method.template"
            )
            .registerTemplateCommandParameters(this)
            .registerTemplateContext()
            .insideTopPackageDirectory(layer)
            .appendOutputToFile(this.entity + layer[0].toUpperCase() + layer.substring(1) + ".groovy")
            .run()
    }
}
