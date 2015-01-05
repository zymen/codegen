package net.zymen.codegen

import net.zymen.codegen.console.ConsoleSupport
import net.zymen.codegen.console.JLine2ConsoleSupport
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option

import net.zymen.codegen.commands.Command
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.SystemDirFileService

class Application {
    @Option(name = "-f", usage = "input file")
    File inputFile

    @Option(name = "-p", usage = "top package")
    String topPackage

    private def configure() {
        Ioc.instance().register(DirFileService.class, new SystemDirFileService())
        Ioc.instance().register(ConsoleSupport.class, new JLine2ConsoleSupport())
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

    def main(String[] args) {
        this.configure()

        CmdLineParser parser = new CmdLineParser(this)

        try {
            parser.parseArgument(args)

            if ( inputFile ) {
                def content = inputFile.readLines()
                        .findAll { it.trim().length() > 1 }
                        //.removeAll { it.trim().startsWith('#') }

                Context context = new Context(topPackage: topPackage)

                processBatchCommands(context, content)
            } else {
                processInteractiveConsole()
            }

        }catch(Exception e) {
            println e.message
        }
    }

    def processInteractiveConsole() {
        Ioc.instance().get(ConsoleSupport.class).runInteraction()
    }

    def processBatchCommands(Context context, List<String> cmd) {
        this.executeCommands(context, cmd)
    }
}

