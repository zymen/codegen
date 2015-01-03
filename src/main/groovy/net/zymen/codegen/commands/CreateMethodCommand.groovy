package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class CreateMethodCommand implements Command {
    DirFileService dirFileService

    Context context

    String entity

    String name

    String layer

    public CreateMethodCommand() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)
    }

    @Override
    def execute() {
        println "Creating method for entity ${entity} with name ${name} for layer ${layer}"
        Entity entity = new Entity(name: name)

        String entityPackageDirectory = context.topPackage.replace(".", "/")
        String destinationDirectory = "src/main/groovy/${entityPackageDirectory}/${layer}"
        this.dirFileService.createDirectory(destinationDirectory)

        String className = this.entity + layer[0].toUpperCase() + layer.substring(1)
        String entityOutputFile = destinationDirectory + "/${className}.groovy"


        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(TemplateFactory.fromFile("${layer}.method.template"),
                ['parameters': entity.properties, 'context': context.properties])

        this.dirFileService.appendBeforeClassEnd(entityOutputFile, output)
    }
}
