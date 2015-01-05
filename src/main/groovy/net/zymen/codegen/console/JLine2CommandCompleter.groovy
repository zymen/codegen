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
        return classNameWithoutPackage
    }

    @Override
    int complete(String buffer, int cursor, List<CharSequence> candidates) {
        //if (buffer != null && buffer.length() < 2) {

        availableCommands.grep { it.startsWith(buffer) }
                         .each { candidates.add(it) }
        //}

        return 0
    }
}
