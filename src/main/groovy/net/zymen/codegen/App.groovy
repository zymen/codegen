package net.zymen.codegen

import groovy.transform.CompileStatic
import net.zymen.codegen.commands.Command

@CompileStatic
public class App {

    public static void main(String[] args) {
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

        List<String> cmd = new LinkedList<String>()

        cmd.add("create gradle project")

        cmd.add("create file template:main output:App.groovy")

        cmd.add("create entity name:Method")
        cmd.add("create property name:name dataType:text entity:Method")

        cmd.add("create entity name:MethodService layer:service")
        cmd.add("create method name:delete entity:Method layer:service")
        cmd.add("create method name:insert entity:Method layer:service")
        cmd.add("create method name:update entity:Method layer:service")

        cmd.add("create entity name:MethodController entity:Method layer:controller")
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
