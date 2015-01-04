package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.service.CommandExecutionBuilder

class CreateFileCommand implements Command {
    Context context

    String template

    String output

    @Override
    def execute() {
        println "Creating file ${output} from template ${template}"

        CommandExecutionBuilder.start()
            .inContext(context)
            .forTemplateGroup("create-file")
            .useFileTemplatesInOrder(
                "${template}.template"
            )
            .registerTemplateContext()
            .insideTopPackageDirectory()
            .writeIntoFileWithoutOverwriting(output)
            .run()
    }
}
