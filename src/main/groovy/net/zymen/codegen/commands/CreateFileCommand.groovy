package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.factory.TemplateFactory
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.OutputBuilderService

class CreateFileCommand implements Command {
    DirFileService dirFileService

    Context context

    String template

    String output

    public CreateFileCommand() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)
    }

    @Override
    def execute() {
        println "Creating file ${output} from template ${template}"
        String entityPackageDirectory = context.topPackage.replace(".", "/")
        String destinationDirectory = "src/main/groovy/${entityPackageDirectory}"
        this.dirFileService.createDirectory(destinationDirectory)

        String outputFile = destinationDirectory + "/${output}"

        if (this.dirFileService.fileExists(outputFile))
            return

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(TemplateFactory.fromFile("${template}.template"),
                ['context': context.properties])

        this.dirFileService.writeIntoFile(outputFile, output)
    }
}
