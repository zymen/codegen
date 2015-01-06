package net.zymen.codegen.service

import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.CommandMetadata
import spock.lang.Specification

class CommandServiceTest extends Specification{
    class CreateWebProjectCommand implements Command {

        @Override
        def execute() {}
    }

    def "should return correct user friendly name for example class"() {
        when:
        CommandService commandService = new CommandService(this.class.package.name)
        CommandMetadata metadata = commandService.buildAllCommandsInfo()
                .find { it.commandClassName.equals(CreateWebProjectCommand.class.name) }

        then:
        assert metadata.userFriendlyName.equals("create web project")
    }
}
