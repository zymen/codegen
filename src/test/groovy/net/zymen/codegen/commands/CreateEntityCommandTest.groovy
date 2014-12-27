package net.zymen.codegen.commands

import net.zymen.codegen.Context
import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import spock.lang.Specification

class CreateEntityCommandTest extends Specification {
    def "should create directory for destination file"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            CreateEntityCommand command = new CreateEntityCommand(name: 'test', context: new Context(topPackage: 'pack'))
            command.dirFileService = dirFileService

        when:
            command.execute()

        then:
            1 * dirFileService.createDirectory(_)
    }

    def "should create correct directory if package name has more than one level"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            CreateEntityCommand command = new CreateEntityCommand(name: 'test', context: new Context(topPackage: 'net.zymen.codegen'))
            command.dirFileService = dirFileService

        when:
            command.execute()

        then:
            1 * dirFileService.createDirectory("src/main/groovy/net/zymen/codegen/model")
    }

    def "should not override if destination file exists"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { true }
            CreateEntityCommand command = new CreateEntityCommand(name: 'test', context: new Context(topPackage: 'net.zymen.codegen'))
            command.dirFileService = dirFileService

        when:
            command.execute()

        then:
            0 * dirFileService.writeIntoFile(_, _)
    }
}
