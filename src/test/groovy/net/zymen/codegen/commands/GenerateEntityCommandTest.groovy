package net.zymen.codegen.commands

import net.zymen.codegen.model.Entity
import net.zymen.codegen.service.DirFileService
import spock.lang.Specification

class GenerateEntityCommandTest extends Specification {
    def "should create directory for destination file"() {
        given:
            def dirFileService = Mock(DirFileService)

        when:
            GenerateEntityCommand command = new GenerateEntityCommand(new Entity(name: 'test', pack: 'pack'), dirFileService)
            command.execute()

        then:
            1 * dirFileService.createDirectory(_)
    }
}
