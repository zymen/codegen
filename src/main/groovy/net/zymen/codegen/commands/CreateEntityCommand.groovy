package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.service.CommandExecutionBuilder
import net.zymen.codegen.service.DirFileService

class CreateEntityCommand implements Command {
    DirFileService dirFileService

    Context context

    String name

    String layer = "model"

    String entity

    public CreateEntityCommand() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)
    }

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
