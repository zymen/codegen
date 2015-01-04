package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.service.CommandExecutionBuilder

class CreateEntityCommand implements Command {
    Context context

    String name

    String layer = "model"

    String entity

    @Override
    def execute() {
        CommandExecutionBuilder.start()
            .inContext(context)
            .forTemplateGroup("create-entity")
            .useFileTemplatesInOrder(
                "${layer}.class.template"
            )
            .registerTemplateContext()
            .registerTemplateCommandParameters(this)
            .insideTopPackageDirectory(layer)
            .writeIntoFileWithoutOverwriting(name + ".groovy")
            .run()
    }
}
