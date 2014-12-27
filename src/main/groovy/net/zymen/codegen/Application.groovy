package net.zymen.codegen

import net.zymen.codegen.commands.Command
import net.zymen.codegen.commands.GenerateEntityCommand
import net.zymen.codegen.model.Entity
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

        Command command = new GenerateEntityCommand(new Entity(name: "Book", pack: "zymen"));
        command.execute()
    }
}
