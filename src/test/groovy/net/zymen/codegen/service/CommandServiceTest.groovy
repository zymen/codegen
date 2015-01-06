package net.zymen.codegen.service

import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.CommandMetadata
import spock.lang.Specification

class CommandServiceTest extends Specification{
    class CreateWebProjectCommand implements Command {
        String url
        Integer port

        @Override
        def execute() {}
    }

    def "should return correct user friendly name for example class"() {
        given:
        CommandService commandService = new CommandService(this.class.package.name)

        when:
        CommandMetadata metadata = commandService.buildAllCommandsInfo()
                .find { it.commandClassName.equals(CreateWebProjectCommand.class.name) }

        then:
        assert metadata.userFriendlyName.equals("create web project")
    }

    def "should return all properties that are available inside class"() {
        given:
        CommandService commandService = new CommandService(this.class.package.name)

        when:
        CommandMetadata metadata = commandService.buildAllCommandsInfo()
                .find { it.commandClassName.equals(CreateWebProjectCommand.class.name) }

        then:
        assert metadata.commandProperties.size() == 2
        assert metadata.commandProperties.find { it.name.equals("port") } != null
        assert metadata.commandProperties.find { it.name.equals("url") } != null
    }
}
