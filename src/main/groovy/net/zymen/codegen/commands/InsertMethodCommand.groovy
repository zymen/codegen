package net.zymen.codegen.commands

import net.zymen.codegen.model.Method

class InsertMethodCommand implements Command {
    Method method

    public InsertMethodCommand(Method method) {
        this.method = method
    }

    @Override
    def execute() {
        return null
    }
}
