package net.zymen.codegen.commands

import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class GenerateEntityCommand implements Command {
    DirFileService dirFileService

    Entity entity

    public GenerateEntityCommand(Entity entity) {
        this(
                entity,
                Ioc.instance().get(DirFileService.class)
        )
    }

    public GenerateEntityCommand(Entity entity, DirFileService dirFileService) {
        this.entity = entity
        this.dirFileService = dirFileService
    }

    @Override
    def execute() {
        String entityPackageDirectory = this.entity.pack.replace(".", "/")
        String destinationDirectory = "src/main/groovy/${entityPackageDirectory}/model"
        this.dirFileService.createDirectory(destinationDirectory)

        String entityOutputFile = destinationDirectory + "/" + this.entity.name + ".java"

        if (this.dirFileService.fileExists(entityOutputFile))
            return

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(TemplateFactory.fromFile("entity.template"), this.entity.properties)

        this.dirFileService.writeIntoFile(entityOutputFile, output)
    }
}
