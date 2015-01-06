package net.zymen.codegen.console

import jline.console.completer.Completer
import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.CommandMetadata
import net.zymen.codegen.service.CommandService
import org.reflections.Reflections

class JLine2CommandCompleter implements Completer {
    CommandService commandService = new CommandService()
    List<CommandMetadata> allCommandsMetadata;

    public JLine2CommandCompleter() {
        this.allCommandsMetadata = this.commandService.buildAllCommandsInfo()
    }

    @Override
    int complete(String buffer, int cursor, List<CharSequence> candidates) {

        CommandMetadata selectedCommand = this.allCommandsMetadata
                .find { buffer.startsWith(it.userFriendlyName) }

        if (selectedCommand == null) {
            this.allCommandsMetadata
                    .grep { it.userFriendlyName.startsWith(buffer) }
                    .each { candidates.add(it.userFriendlyName) }
        } else {

            candidates.add(buffer.trim() + " withData")
            candidates.add(buffer.trim() + " name")
        }

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
