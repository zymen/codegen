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

            String trimmedBuffer = buffer.trim()

            if (trimmedBuffer.equals(selectedCommand.userFriendlyName)) {
                selectedCommand.commandProperties
                        .each { candidates.add(it.name) }
            } else {

                int lastSpacePosition = trimmedBuffer.lastIndexOf(' ')
                String lastSegment = trimmedBuffer.substring(lastSpacePosition).trim()
                String prefix = trimmedBuffer.substring(0, lastSpacePosition + 1)

                selectedCommand.commandProperties
                        .grep { it.name.startsWith(lastSegment) }
                        .each { candidates.add(prefix + it.name) }
            }
        }

        //TODO: command name
        //TODO: properties names (only not used previously)
        //TODO: possible values for those fields (future?)

        return 0
    }
}
