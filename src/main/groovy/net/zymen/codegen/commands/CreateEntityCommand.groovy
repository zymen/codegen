package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class CreateEntityCommand implements Command {
    DirFileService dirFileService

    Context context

    String name

    String layer = "model"

    String entity

    String otherLayerInjections

    public CreateEntityCommand() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)
    }

    @Override
    def execute() {
        Entity entity = new Entity(name: name)

        String entityPackageDirectory = context.topPackage.replace(".", "/")
        String destinationDirectory = "src/main/groovy/${entityPackageDirectory}/${layer}"
        this.dirFileService.createDirectory(destinationDirectory)

        String entityOutputFile = destinationDirectory + "/" + name + ".groovy"

        if (this.dirFileService.fileExists(entityOutputFile))
            return

        Map<String, String> properties = new HashMap<String, String>()
        properties.put('entityName', this.entity)

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(TemplateFactory.fromFile("${layer}.class.template"),
                ['entity': entity.properties, 'context': context.properties, 'class': properties])

        this.dirFileService.writeIntoFile(entityOutputFile, output)
    }
}
