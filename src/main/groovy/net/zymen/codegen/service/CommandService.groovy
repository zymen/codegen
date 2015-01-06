package net.zymen.codegen.service

import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.CommandMetadata
import org.reflections.Reflections

class CommandService {
    String basePackage

    public CommandService() {
        this("net.zymen.codegen")
    }

    public CommandService(String basePackage) {
        this.basePackage = basePackage
    }

    List<CommandMetadata> buildAllCommandsInfo() {
        Set<Class<Command>> result = new Reflections(basePackage).getSubTypesOf(Command.class)

        return result.collect { createCommandMetadata(it) }
    }

    private CommandMetadata createCommandMetadata(Class<Command> command) {
        CommandMetadata metadata = new CommandMetadata()
        metadata.commandClassName = command.name
        metadata.userFriendlyName = extractUserFriendlyName(command)
        return metadata
    }

    private String extractUserFriendlyName(Class<Command> command) {
        String className = command.name

        if (!className.contains('.')) {
            return className
        }

        String classNameWithoutPackage;

        if (className.contains('$')) {
            classNameWithoutPackage = className.substring(className.indexOf('$') + 1)
        }else {
            classNameWithoutPackage = className.substring(className.lastIndexOf('.') + 1);
        }

        return classNameWithoutPackage.split("(?=\\p{Lu})")
                .takeWhile { it.toLowerCase().compareTo("command") }
                .join(" ")
                .toLowerCase()
    }
}
