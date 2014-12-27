package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class CreateGradleProjectCommand implements Command {
    DirFileService dirFileService

    Context context

    public CreateGradleProjectCommand() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)

    }

    @Override
    def execute() {
        String outputFile = "build.gradle"

        if (this.dirFileService.fileExists(outputFile))
            return

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(TemplateFactory.fromFile("build.gradle.template"),
                ['context': context.properties])

        this.dirFileService.writeIntoFile(outputFile, output)
    }
}
