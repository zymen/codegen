package net.zymen.codegen

import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.GenerateEntityCommand
import net.zymen.codegen.commands.InsertMethodCommand
import net.zymen.codegen.model.Entity
import net.zymen.codegen.model.Method
import net.zymen.codegen.model.MethodParameter
import net.zymen.codegen.service.DirFileService
import net.zymen.codegen.service.SystemDirFileService

class Application {
    private def configure() {
        Ioc.instance().register(DirFileService.class, new SystemDirFileService())
    }

    def run() {
        this.configure()
    }

    static def main(String[] args) {
        Application app = new Application()
        app.run()

        List<Command> commands = new LinkedList<Command>()

        /*
            create project top-package:net.zymen.codegen template:rest-service
            create entity name:Method
            create datasource type:jpa entity:Method
            create method name:delete entity:Method layer:service
            create method name:delete entity:Method layer:controller
         */

        commands.add(new GenerateEntityCommand(new Entity(name: "Method", pack: "net.zymen.codegen")))
        //commands.add(new InsertMethodCommand(new Method(name: "delete", params: new MethodParameter(type: Entity.class))))

        commands.each { it.execute() }
    }
}
