package net.zymen.codegen.commands

import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import spock.lang.Specification

class GenerateEntityCommandTest extends Specification {
    def "should create directory for destination file"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            GenerateEntityCommand command = new GenerateEntityCommand(new Entity(name: 'test', pack: 'pack'), dirFileService)

        when:
            command.execute()

        then:
            1 * dirFileService.createDirectory(_)
    }

    def "should create correct directory if package name has more than one level"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            GenerateEntityCommand command = new GenerateEntityCommand(new Entity(name: 'test', pack: 'net.zymen.codegen'), dirFileService)

        when:
            command.execute()

        then:
            1 * dirFileService.createDirectory("src/main/groovy/net/zymen/codegen/model")
    }

    def "should not override if destination file exists"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { true }
            GenerateEntityCommand command = new GenerateEntityCommand(new Entity(name: 'test', pack: 'net.zymen.codegen'), dirFileService)

        when:
            command.execute()

        then:
            0 * dirFileService.writeIntoFile(_, _)
    }
}
