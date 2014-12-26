package net.zymen.codegen

import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.GenerateEntityCommand
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService

class Application {
    static def main(String[] args) {
        Ioc.instance().register(DirFileService.class, )

        Command command = new GenerateEntityCommand(new Entity(name: "Book"));
        command.execute()
    }
}
