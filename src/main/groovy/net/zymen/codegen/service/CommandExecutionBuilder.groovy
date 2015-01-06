package net.zymen.codegen.service

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.commands.Command
import net.zymen.codegen.factory.TemplateFactory

class CommandExecutionBuilder {
    private enum ActionType {
        APPEND,
        WRITE
    }

    Context context

    String templateGroupName

    List<String> fileTemplates

    Map<String, Object> templateParameters

    String outputFileName

    Boolean outputFileOverwrite = null

    String topPackageDirectory

    ActionType actionType

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

    CommandExecutionBuilder insideTopPackageDirectory(String directory = "") {
        String entityPackageDirectory = context.topPackage.replace(".", "/")

        this.topPackageDirectory = "src/main/groovy/${entityPackageDirectory}"

        if (directory != "") {
            this.topPackageDirectory += "/${directory}"
        }

        return this
    }

    CommandExecutionBuilder insideProjectDirectory(String directory = "") {
        this.topPackageDirectory = directory
        return this
    }

    CommandExecutionBuilder writeIntoFileWithoutOverwriting(String filename) {
        this.outputFileName = filename
        this.outputFileOverwrite = false
        this.actionType = ActionType.WRITE
        return this
    }

    CommandExecutionBuilder appendOutputToFile(String outputFilename) {
        this.outputFileName = outputFilename
        this.actionType = ActionType.APPEND
        return this
    }

    private String getOutputFile() {
        if (this.topPackageDirectory.trim().length() > 0) {
            this.topPackageDirectory + "/" + this.outputFileName
        } else {
            this.outputFileName
        }
    }

    def run() {
        this.dirFileService.createDirectory(this.topPackageDirectory)

        if (this.actionType == ActionType.WRITE && this.outputFileOverwrite == false && this.dirFileService.fileExists(outputFile))
            return

        OutputBuilderService outputBuilderService = new OutputBuilderService()
        String output = outputBuilderService.output(
                TemplateFactory.fromFile("${templateGroupName}/${fileTemplates[0]}"),
                this.templateParameters)

        if (this.actionType == ActionType.APPEND) {
            this.dirFileService.appendBeforeClassEnd(outputFile, output)
        } else if (this.actionType == ActionType.WRITE) {
            this.dirFileService.writeIntoFile(outputFile, output)
        }
    }
}
