package net.zymen.codegen

import groovy.transform.CompileStatic
import net.zymen.codegen.commands.Command

@CompileStatic
public class App {

    public static void main(String[] args) {
        Application app = new Application()
        app.main(args)
    }
}
