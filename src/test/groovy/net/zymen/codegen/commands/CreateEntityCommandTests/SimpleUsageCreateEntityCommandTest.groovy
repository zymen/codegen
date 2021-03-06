package net.zymen.codegen.commands.CreateEntityCommandTests

import net.zymen.codegen.Context
import net.zymen.codegen.Ioc
import net.zymen.codegen.commands.CreateEntityCommand
import net.zymen.codegen.service.DirFileService
import spock.lang.Specification

class SimpleUsageCreateEntityCommandTest extends Specification {
    def "should create directory for destination file"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            Ioc.instance().register(DirFileService.class, dirFileService)
            CreateEntityCommand command = new CreateEntityCommand(name: 'test', context: new Context(topPackage: 'pack'))

        when:
            command.execute()

        then:
            1 * dirFileService.createDirectory(_)
    }

    def "should create correct directory if package name has more than one level"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            Ioc.instance().register(DirFileService.class, dirFileService)
            CreateEntityCommand command = new CreateEntityCommand(name: 'test', context: new Context(topPackage: 'net.zymen.codegen'))

        when:
            command.execute()

        then:
            1 * dirFileService.createDirectory("src/main/groovy/net/zymen/codegen/model")
    }

    def "should not override if destination file exists"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { true }
            Ioc.instance().register(DirFileService.class, dirFileService)
            CreateEntityCommand command = new CreateEntityCommand(name: 'test', context: new Context(topPackage: 'net.zymen.codegen'))

        when:
            command.execute()

        then:
            0 * dirFileService.writeIntoFile(_, _)
    }
}
