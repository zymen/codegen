package net.zymen.codegen.service

import net.zymen.codegen.Context
import net.zymen.codegen.commands.Command

class CommandExecutionService {
    def execute(Context context, String command) {
        println command

        String[] commandParts = command.split(' ')

        String commandName = ""
        Map<String, Object> parameters = new HashMap<String, Object>()

        commandParts.each {
            if (!it.contains(':')) {
                commandName += it[0].toUpperCase() + it.substring(1).toLowerCase()
            } else {
                parameters.put(it.split(':')[0], it.split(':')[1])
            }
        }

        commandName += "Command"

        println "Command name = '${commandName}', parameters = ${parameters}"

        parameters.put('context', context)

        Command cmd = (Command)Class.forName("net.zymen.codegen.commands." + commandName).newInstance(parameters)
        cmd.execute()
    }
}
