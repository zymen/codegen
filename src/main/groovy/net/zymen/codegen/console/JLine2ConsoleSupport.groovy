package net.zymen.codegen.console

import jline.console.ConsoleReader
import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.service.CommandExecutionService

class JLine2ConsoleSupport implements ConsoleSupport{

    @Override
    void runInteraction() {
        CommandExecutionService commandExecutionService = Ioc.instance().get(CommandExecutionService.class)

        ConsoleReader reader = new ConsoleReader();
        reader.setPrompt("prompt> ");

        reader.addCompleter(new JLine2CommandCompleter())

        String line;
        PrintWriter out = new PrintWriter(reader.getOutput());
        while ((line = reader.readLine()) != null) {
            out.println("\u001B[33m======>\u001B[0m\"" + line + "\"");
            out.flush();

            if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                break;
            } else if (line.equalsIgnoreCase("cls")) {
                reader.clearScreen();
            } else {

                Context context = new Context(topPackage: 'net.zymen.test1')
                commandExecutionService.execute(context, line.trim())
                out.println("RECEIVED: " + line)
            }
        }
    }
}