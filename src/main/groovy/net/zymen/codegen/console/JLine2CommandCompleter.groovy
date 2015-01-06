package net.zymen.codegen.console

import jline.console.completer.Completer
import net.zymen.codegen.commands.Command
import net.zymen.codegen.service.CommandService
import org.reflections.Reflections

class JLine2CommandCompleter implements Completer {
    CommandService commandService = new CommandService()

    @Override
    int complete(String buffer, int cursor, List<CharSequence> candidates) {

        commandService.buildAllCommandsInfo()
                .grep { it.userFriendlyName.startsWith(buffer) }
                .each { candidates.add(it.userFriendlyName) }

        //TODO: command name
        //TODO: properties names (only not used previously)
        //TODO: possible values for those fields (future?)
//
//        availableCommands.grep { it.startsWith(buffer) }
//                         .each { candidates.add(it) }
        //}

        return 0
    }
}
