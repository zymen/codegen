package net.zymen.codegen

import net.zymen.codegen.console.ConsoleSupport
import net.zymen.codegen.console.JLine2ConsoleSupport
import net.zymen.codegen.service.CommandExecutionService
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
        Ioc.instance().register(CommandExecutionService.class, new CommandExecutionService())
    }

    def executeCommands(Context context, List<String> commands) {
        CommandExecutionService service = Ioc.instance().get(CommandExecutionService.class)
        commands.each { service(context, it) }
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

