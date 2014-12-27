package net.zymen.codegen

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

    static def main(String[] args) {
        Application app = new Application()
        Context context = new Context(topPackage: 'net.zymen.example')

        List<Command> commands = new LinkedList<Command>()

        /*
            create project top-package:net.zymen.codegen template:rest-service
            create entity name:Method
            create datasource type:jpa entity:Method
            create method name:delete entity:Method layer:service
            create method name:delete entity:Method layer:controller
         */

        //commands.add(new CreateEntityCommand([context: context, name: "Method"]))
        //commands.add(new InsertMethodCommand(new Method(name: "delete", params: new MethodParameter(type: Entity.class))))

       // commands.each { it.execute() }

        List<String> cmd = new LinkedList<String>()

        cmd.add("create entity name:Method")
        //cmd.add("create property name:name type:text entity:Method")

        cmd.add("create entity name:MethodService layer:service")
        cmd.add("create method name:delete entity:Method layer:service")
        cmd.add("create method name:insert entity:Method layer:service")
        cmd.add("create method name:update entity:Method layer:service")

        cmd.add("create entity name:MethodController layer:controller")
        cmd.add("create method name:delete entity:Method layer:controller")
        cmd.add("create method name:find entity:Method layer:controller")

        /*
            usage of templates:
            layer/class.template

            groovy/service/class.template
            groovy/service/delete.method.template

            groovy/service/method.template
            groovy/delete.method.template
            groovy/method.template
         */

        /*
            set context.entity:Method context.layer:service
            create validation entity:Method property:name validation:not-null

         */

        app.run(context, cmd)
    }
}
