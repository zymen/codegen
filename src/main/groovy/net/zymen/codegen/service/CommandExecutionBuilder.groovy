package net.zymen.codegen.service

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.commands.Command
import net.zymen.codegen.factory.TemplateFactory

class CommandExecutionBuilder {
    Context context

    String templateGroupName

    List<String> fileTemplates

    Map<String, Object> templateParameters

    String outputFileName

    String topPackageDirectory

    DirFileService dirFileService

    private CommandExecutionBuilder() {
        this.dirFileService = Ioc.instance().get(DirFileService.class)
        this.templateParameters = new HashMap<String, Object>()
        this.fileTemplates = new LinkedList<String>()
    }

    public static CommandExecutionBuilder start() {
        new CommandExecutionBuilder()
    }

    CommandExecutionBuilder inContext(Context context) {
        this.context = context
        return this
    }

    CommandExecutionBuilder forTemplateGroup(String templateGroupName) {
        this.templateGroupName = templateGroupName
        return this
    }

    CommandExecutionBuilder useFileTemplatesInOrder(String...fileTemplates) {
        this.fileTemplates.addAll(fileTemplates)
        return this
    }

    CommandExecutionBuilder registerTemplateData(String key, Object data) {
        this.templateParameters.put(key, data)
        return this
    }

    CommandExecutionBuilder registerTemplateCommandParameters(Command command) {
        this.registerTemplateData("command", command.properties)
    }

    CommandExecutionBuilder registerTemplateContext() {
        this.registerTemplateData("context", this.context.properties)
    }

    CommandExecutionBuilder insideTopPackageDirectory(String directory) {
        String entityPackageDirectory = context.topPackage.replace(".", "/")
        this.topPackageDirectory = "src/main/groovy/${entityPackageDirectory}/${directory}"
        return this
    }

    CommandExecutionBuilder appendOutputToFile(String outputFilename) {
        this.outputFileName = outputFilename
        return this
    }

    def run() {
        this.dirFileService.createDirectory(this.topPackageDirectory)

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(
                TemplateFactory.fromFile("${templateGroupName}/${fileTemplates[0]}"),
                this.templateParameters)

        this.dirFileService.appendBeforeClassEnd(this.topPackageDirectory + "/" + this.outputFileName + ".groovy", output)
    }
}
