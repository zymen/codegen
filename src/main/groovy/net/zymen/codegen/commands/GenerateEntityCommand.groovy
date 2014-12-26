package net.zymen.codegen.commands

import net.zymen.codegen.model.Entity

class GenerateEntityCommand implements Command{
    Entity entity

    public GenerateEntityCommand(Entity entity) {
        this.entity = entity
    }

    @Override
    def execute() {
        return null
    }
}
