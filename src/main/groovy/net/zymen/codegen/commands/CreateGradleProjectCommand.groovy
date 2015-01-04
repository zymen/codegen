package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.service.CommandExecutionBuilder

class CreateGradleProjectCommand implements Command {
    Context context

    @Override
    def execute() {
        CommandExecutionBuilder.start()
            .inContext(context)
            .forTemplateGroup("create-gradle-project")
            .useFileTemplatesInOrder(
                "build.gradle.template"
            )
            .registerTemplateCommandParameters(this)
            .insideProjectDirectory()
            .writeIntoFileWithoutOverwriting("build.gradle")
            .run()
    }
}
