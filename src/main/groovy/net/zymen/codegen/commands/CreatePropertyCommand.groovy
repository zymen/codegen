package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class CreatePropertyCommand implements Command {
    DirFileService dirFileService

    Context context

    String name

    String type

    String entity

    public CreatePropertyCommand() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)
    }

    @Override
    def execute() {
        String layer = "model"

        println "Adding property with name ${name} for entity ${entity}"

        String entityPackageDirectory = context.topPackage.replace(".", "/")
        String destinationDirectory = "src/main/groovy/${entityPackageDirectory}/${layer}"
        this.dirFileService.createDirectory(destinationDirectory)

        String className = this.entity + layer[0].toUpperCase() + layer.substring(1)
        String entityOutputFile = destinationDirectory + "/${className}.groovy"

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(TemplateFactory.fromFile("create-property/${type}.property.template"),
                ['parameters': this.properties, 'context': context.properties])

        this.dirFileService.appendBeforeClassEnd(entityOutputFile, output)
    }
}
