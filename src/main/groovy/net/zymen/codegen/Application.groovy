package net.zymen.codegen

import groovy.transform.CompileStatic
import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.CreateEntityCommand
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.SystemDirFileService

class Application {
    private def configure() {
        Ioc.instance().register(DirFileService.class, new SystemDirFileService())
    }

    def executeCommand(Context context, String command) {
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

    def executeCommands(Context context, List<String> commands) {
        commands.each { executeCommand(context, it) }
    }

    def run(Context context, List<String> cmd) {
        this.configure()
        this.executeCommands(context, cmd)
    }

}

