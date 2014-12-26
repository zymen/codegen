package net.zymen.codegen.commands

import net.zymen.codegen.Ioc
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService

class GenerateEntityCommand implements Command{
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
        String destinationDirectory = "src/main/groovy/${entity.pack}/model"
        this.dirFileService.createDirectory(destinationDirectory)
    }
}
