package net.zymen.codegen.console

import jline.console.ConsoleReader
import jline.console.completer.StringsCompleter


class Jline2ConsoleSupport implements ConsoleSupport{

    @Override
    void runInteraction() {
        ConsoleReader reader = new ConsoleReader();
        reader.setPrompt("prompt> ");

        reader.addCompleter(new StringsCompleter("alfa", "betta", "gamma", "delta", "xyz"))

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
                out.println("RECEIVED: " + line)
            }
        }
    }
}