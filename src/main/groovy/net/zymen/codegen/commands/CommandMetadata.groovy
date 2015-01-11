package net.zymen.codegen.commands

class CommandMetadata {
    String userFriendlyName
    String commandClassName
    List<CommandPropertyMetadata> commandProperties = new LinkedList<CommandPropertyMetadata>()
}
