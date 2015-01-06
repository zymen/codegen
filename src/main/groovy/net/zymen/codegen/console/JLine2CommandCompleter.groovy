package net.zymen.codegen.console

import jline.console.completer.Completer
import net.zymen.codegen.commands.Command
import org.reflections.Reflections

class JLine2CommandCompleter implements Completer {

    List<Class<Command>> availableCommands = new LinkedList<Class<Command>>()

    public JLine2CommandCompleter() {
        Set<Class<Command>> result = new Reflections("net.zymen.codegen").getSubTypesOf(Command.class)

        result.each { this.availableCommands.add(extractCommand(it)) }

        print availableCommands
    }

    private String extractCommand(Class<Command> cmd) {
        String className = cmd.name
        if (!className.contains('.')) {
            return className
        }

        String classNameWithoutPackage = className.substring(className.lastIndexOf('.') + 1)

        return classNameWithoutPackage.split("(?=\\p{Lu})")[0].toLowerCase()
    }

    @Override
    int complete(String buffer, int cursor, List<CharSequence> candidates) {

        //TODO: command name
        //TODO: properties names (only not used previously)
        //TODO: possible values for those fields (future?)

        availableCommands.grep { it.startsWith(buffer) }
                         .each { candidates.add(it) }
        //}

        return 0
    }
}
